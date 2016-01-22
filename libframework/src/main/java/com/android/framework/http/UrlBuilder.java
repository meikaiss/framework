package com.android.framework.http;


import com.android.framework.util.ObjectUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by meikai on 15/10/13.
 */
public class UrlBuilder {

    private String mProtocol;
    private String mHost;
    private String mUri;
    private Map<String, String> mParameter;

    public UrlBuilder(String mHost, String mUri) {
        this("http", mHost, mUri);
    }

    public UrlBuilder(String mProtocol, String mUri, String mHost) {
        this.mProtocol = mProtocol.trim();
        this.mUri = mUri.trim();
        this.mHost = mHost.trim();
        this.mParameter = new HashMap<>();
    }

    public UrlBuilder addParam(String key, String value){
        mParameter.put(key, value);
        return this;
    }

    public UrlBuilder removeParam(String key){
        mParameter.remove(key);
        return this;
    }

    public UrlBuilder clearParam(){
        mParameter.clear();
        return this;
    }

    public String getParam(String key){
        return mParameter.get(key);
    }

    private String buildParameters(){
        if(ObjectUtil.isMapEmpty(mParameter))
            return null;

        StringBuilder s = new StringBuilder();

        Iterator<String> iterator = mParameter.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            s.append("&").append(key).append("=").append(mParameter.get(key));
        }

        return s.toString().substring(1);

    }

    public String build(){
        StringBuilder stringBuilder = new StringBuilder(mProtocol);
        stringBuilder.append("://").append(mHost).append(mUri).append(buildParameters());
        return stringBuilder.toString();
    }



}
