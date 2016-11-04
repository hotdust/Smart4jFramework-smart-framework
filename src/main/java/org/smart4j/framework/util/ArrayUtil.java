package org.smart4j.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 13:44
 * Created by shijiapeng on 2016/11/4.
 */
public class ArrayUtil {

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

    public static boolean isNotEmpty(Object[] array) {
        return ArrayUtils.isNotEmpty(array);
    }
}
