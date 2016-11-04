package org.smart4j.framework;

import org.smart4j.framework.helper.ConfigHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 17:09
 * Created by shijiapeng on 2016/11/4.
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //super.init(servletConfig);
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        // 获取请求方法和路径
        // 获取Action处理器

        // 创建请求对象
        // 把请求参数请到Param里
        // 把Body里的参数也放到Param里

        // 调用Action方法（用Param做参数）

        // 看返回对象
        // 如果是View，把返回参数放到Request里，再跳到JSP画面
        // 如果是Data，直接写到流里
    }
}
