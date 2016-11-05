package org.smart4j.framework;

import org.junit.Assert;
import org.junit.Test;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.helper.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @Author shijiapeng
 * @Date 2016/11/5 16:28
 * Created by shijiapeng on 2016/11/5.
 */
public class DispatcherServletTest {

    DispatcherServlet dispatcherServlet = new DispatcherServlet();

    @Test
    public void testInit() throws ServletException {
        // TODO: 2016/11/5
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        ServletConfig configMock = mock(ServletConfig.class);
        ServletContext contextMock = mock(ServletContext.class);
        when(configMock.getServletContext()).thenReturn(contextMock);
        // 创建名字为“jsp”和“Default”的Servlet
        ServletRegistration jspRegistrationMock = mock(ServletRegistration.class);
        ServletRegistration defaultRegistrationMock = mock(ServletRegistration.class);
        when(contextMock.getServletRegistration("jsp")).thenReturn(jspRegistrationMock);
        when(contextMock.getServletRegistration("default")).thenReturn(defaultRegistrationMock);

//        HttpServlet jspServletMock = mock(HttpServlet.class);
//        HttpServlet defaultServletMock = mock(HttpServlet.class);
//        contextMock.addServlet("jsp", jspServletMock);
//        contextMock.addServlet("default", defaultServletMock);

        dispatcherServlet.init(configMock);

        // comfirm
        assertTrue(ClassHelper.getClassSet().size() > 0);
        assertTrue(BeanHelper.getBeanMap().size() > 0);
//        assertTrue(IoCHelper.);
        Handler handler = ControllerHelper.getHandler("get", "/customer");
        assertTrue(handler.getActionMethod().getName().equals("getMethoder"));
        assertTrue(handler.getControllerClass().getSimpleName().equals("ControllerClass"));
//        ClassHelper.class,
//                BeanHelper.class,
//                IoCHelper.class,
//                ControllerHelper.class

//        verify(jspRegistrationMock.getMappings()).forEach(str -> {
//            str.equals(ConfigHelper.getAppJspPath() + "*");
//        });
//
//        verify(defaultRegistrationMock.getMappings()).forEach(str -> {
//            str.equals(ConfigHelper.getAppAssetPath() + "*");
//        });




//        HelperLoader.init();
//        ServletContext servletContext = servletConfig.getServletContext();
//        // 取得Tomcat默认的处理Jsp的Servlet，配置这个Servlet的Mapping
//        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
//        // 取得Tomcat默认的Servlet，配置这个Servlet的Mapping，来处理静态资源
//        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
//        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }
    
    public void testService() {
        // TODO: 2016/11/5
    }
}

