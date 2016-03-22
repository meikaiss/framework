package com.android.framework.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by meikai on 16/1/5.
 */
public class ApiResponse {

    private boolean success;

    private int responseCode;

    private String message;

    private JSONObject jsonObject;

    public ApiResponse(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.success = jsonObject.getBoolean("success");
        this.responseCode = jsonObject.getIntValue("responseCode");
        this.message = jsonObject.getString("message");
    }

    public boolean isSuccess() {
        return success;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public <T> T getData(Class<T> cls) {
        return getData("data", cls);
    }

    private  <T> T getData(String path, Class<T> cls) {
        String[] ss = path.split("\\.");
        JSONObject json = jsonObject;
        for (String s : ss) {
            json = json.getJSONObject(s);
        }
        return JSON.toJavaObject(json, cls);
    }
}
