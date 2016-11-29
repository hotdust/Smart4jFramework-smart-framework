package org.smart4j.framework.helper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shijiapeng on 16/11/29.
 */
public class BeanHelperTest {

    @Test
    public void setBean() throws Exception {
        String firstObject = "firstObject";
        String secondObject = "secondObject";

        BeanHelper.setBean(String.class, firstObject);
        assertTrue(BeanHelper.getBean(String.class).equals(firstObject));

        BeanHelper.setBean(String.class, secondObject);
        assertTrue(BeanHelper.getBean(String.class).equals(secondObject));
    }

}