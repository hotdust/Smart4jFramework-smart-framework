package org.smart4j.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.ServletHelper;
import org.smart4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 17:09
 * Created by shijiapeng on 2016/11/4.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //super.init(servletConfig);
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        // 取得Tomcat默认的处理Jsp的Servlet，配置这个Servlet的Mapping
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 取得Tomcat默认的Servlet，配置这个Servlet的Mapping，来处理静态资源
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        // 获取请求方法和路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // 获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        // TODO: 16/12/16 如果用户输入的URI不存在，给用户跳到error画面的选择。在properties文件中可以设置画面和路径
        if (handler == null) {
            return;
        }


        // 把request和response放到线程里
        ServletHelper.init(req, resp);
        try {
            // 取得Controller和实例Bean
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            // 从request的parameter里取得参数
            HashMap<String, Object> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            // 从request的Body里取得参数（post请求时，参数放到body中）
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                // 把参数（key=value&testKey=testValue）分开
                String[] params = StringUtil.splitString(body, "&");
                for (String param : params) {
                    String[] array = StringUtil.splitString(param, "=");
                    if (!(ArrayUtil.isNotEmpty(array) && array.length == 2)) {
                        continue;
                    }

                    String paramName = array[0];
                    String paramValue = array[1];
                    paramMap.put(paramName, paramValue);
                }
            }


            // 把请求参数请到Param里
            Param param = new Param(paramMap);

            // 调用Action方法（用Param做参数）
            Object result;
            Method actionMethod = handler.getActionMethod();
            // 在做无参数方法调用时，和书上写的不一样。书上是判断Map为不为空，来选择调用哪个方法。
            if (actionMethod.getParameterCount() == 0) {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }

            // 看返回对象
            // 如果是View，把返回参数放到Request里，再跳到JSP画面\
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();

                if (path.startsWith("/")) {
                    resp.sendRedirect(req.getContextPath() + path);
                } else {
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                }
            } else if (result instanceof Data) {// 如果是Data，直接写到流里
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        } catch (IOException e) {
            LOGGER.error("DispatchServlet Service error", e);
            ExceptionUtil.throwRumtimeException(e);
        } catch (ServletException e) {
            LOGGER.error("DispatchServlet Service error", e);
            ExceptionUtil.throwRumtimeException(e);
        } finally {
            // 去除线程中的request和response
            ServletHelper.destroy();
        }

    }
}
