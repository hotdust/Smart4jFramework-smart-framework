package org.smart4j.framework;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IoCHelper;
import org.smart4j.framework.util.ClassUtil;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 16:51
 * Created by shijiapeng on 2016/11/4.
 */
public class HelperLoader {
    public static void main(String[] args) {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IoCHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }

    }
}
