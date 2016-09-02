package com.android.framework.demo.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/9/2.
 */
public class WrapTypeJsonActivity extends BaseCompactActivity {

    EditText editText;
    Button button;
    TextView tvResult;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_wrap_type_json;
    }

    @Override
    public void findViews() {
        editText = (EditText) findViewById(R.id.edt_json_string);
        button = (Button) findViewById(R.id.btn_convert);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    public void setListeners() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterView();
            }
        });

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {
        String jsonString = "{\"int1\":null,\"int2\":null,\"int3\":\"null\",\"int4\":\"null\",\"name\":\"meikai\"}";

        if (editText.getText().toString().length() > 0) {
            jsonString = editText.getText().toString();
        }
        JSONObject jsonObject = JSON.parseObject(jsonString);

        UserInfo userInfo = JSON.toJavaObject(jsonObject, UserInfo.class);

        Log.e("afterView", userInfo.toString());

        Toast.makeText(this, userInfo.toString(), Toast.LENGTH_SHORT).show();

        tvResult.setText(userInfo.toString());

    }

    public static class UserInfo {

        private int int1;
        private Integer int2;
        private int int3;
        private Integer int4;

        private float float1;
        private Float float2;
        private float float3;
        private Float float4;

        private String name;

        @Override
        public String toString() {
            return "UserInfo{" +
                    "int1=" + int1 +
                    ", int2=" + int2 +
                    ", int3=" + int3 +
                    ", int4=" + int4 +
                    ", float1=" + float1 +
                    ", float2=" + float2 +
                    ", float3=" + float3 +
                    ", float4=" + float4 +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
