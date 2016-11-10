package org.smart4j.framework.proxy;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;

/**
 * 处理客户管理相关请求
 */
@Controller
public class CustomerController {


    /**
     * 进入 客户列表 界面
     */
    @Action("get:/customer")
    public View index(Param param) {
        return new View("customer.jsp");
    }

}