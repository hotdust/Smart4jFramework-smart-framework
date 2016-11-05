package org.smart4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 17:04
 * Created by shijiapeng on 2016/11/4.
 */
public class View {

    private String path;

    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
