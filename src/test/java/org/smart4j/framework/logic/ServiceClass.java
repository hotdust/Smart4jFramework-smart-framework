package org.smart4j.framework.logic;

import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 11:08
 * Created by shijiapeng on 2016/11/4.
 */
@Service
public class ServiceClass {

    @Transaction
    public void transactionMethod() {

    }
}
