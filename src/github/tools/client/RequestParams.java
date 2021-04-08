package github.tools.client;

import com.google.gson.Gson;

import java.util.LinkedHashMap;

public class RequestParams {
    private LinkedHashMap<String,Object> params;

    public RequestParams() {
        params = new LinkedHashMap<>();
    }

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public Object getParam(String key) {
        if (params.containsKey(key)) {
            return params.get(key);
        }
        return null;
    }

    public boolean doesParamExist(String key) {
        return params.containsKey(key);
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(params);
    }
}
