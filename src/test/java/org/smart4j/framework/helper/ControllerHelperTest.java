package org.smart4j.framework.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ClassUtil;

/**
 * ControllerHelper Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 4, 2016</pre>
 */
public class ControllerHelperTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getHandler(String requestMethod, String requestPath)
     */
    @Test
    public void testGetHandler() throws Exception {
        ClassUtil.loadClass(ControllerHelper.class.getName());
        Handler handler = ControllerHelper.getHandler("get", "/customer");
        Assert.assertTrue(handler.getActionMethod().getName().equals("getMethoder"));
        Assert.assertTrue(handler.getControllerClass().getSimpleName().equals("ControllerClass"));
    }



} 
