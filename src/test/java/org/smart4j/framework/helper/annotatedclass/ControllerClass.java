package org.smart4j.framework.helper.annotatedclass;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 11:08
 * Created by shijiapeng on 2016/11/4.
 */
@Controller
public class ControllerClass {

    @Inject
    private ServiceClass serviceClass;

    @Action("get:/customer")
    public void getMethoder() {

    }
}
