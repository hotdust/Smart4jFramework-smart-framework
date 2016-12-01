package org.smart4j.framework.helper;


import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.util.ClassUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

/**
 * ControllerHelper Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 4, 2016</pre>
 */
public class ControllerHelperTest {

    @BeforeMethod
    public void before() throws Exception {
    }

    @AfterMethod
    public void after() throws Exception {
    }

    /**
     * Method: getHandler(String requestMethod, String requestPath)
     */
    @Test
    public void testGetHandler() throws Exception {
        ClassUtil.loadClass(ControllerHelper.class.getName());
        Handler handler = ControllerHelper.getHandler("get", "/customer");
        assertTrue(handler.getActionMethod().getName().equals("getMethoder"));
        assertTrue(handler.getControllerClass().getSimpleName().equals("ControllerClass"));
    }



} 
