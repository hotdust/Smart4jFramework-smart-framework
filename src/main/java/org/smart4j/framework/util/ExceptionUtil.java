package org.smart4j.framework.util;

/**
 * Created by shijiapeng on 16/12/16.
 */
public class ExceptionUtil {

    public static void throwRumtimeException(Exception e) {
        throw new RuntimeException(e);
    }
}
