package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 15:15
 * Created by shijiapeng on 2016/11/4.
 */
public class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    declaredMethod.isAnnotationPresent(Action.class);
                    Action action = declaredMethod.getAnnotation(Action.class);
                    String mapping = action.value();

                    boolean matchesResult = mapping.matches("\\w+:/\\w*");
                    if (!matchesResult) {
                        String errorMessage = "can not resolve action mapping : mapping is not 'Method:path' format";
                        LOGGER.error(errorMessage);
                        throw new RuntimeException(errorMessage);
                    }

                    String[] mappingArray = mapping.split(":");
                    if (ArrayUtil.isEmpty(mappingArray) || mappingArray.length != 2) {
                        String errorMessage = "can not resolve action mapping : mapping is not 'Method:path' format";
                        LOGGER.error(errorMessage);
                        throw new RuntimeException(errorMessage);
                    }

                    String requestMethod = mappingArray[0];
                    String requestPath = mappingArray[1];
                    Request request = new Request(requestMethod, requestPath);

                    Handler handler = new Handler(controllerClass, declaredMethod);
                    ACTION_MAP.put(request, handler);
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
