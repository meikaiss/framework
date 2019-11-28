package com.android.framework.demo.activity.viewdemo.measureExtend;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * 结合爷ViewGroup的clipChildren="false"属性，可以让 MeasureExtendLayout 超出父ViewGroup的外围
 * 正常情况下超出部分的无法响应点击事件，此时应结合 parent.onTouchListener 中触发 此范围扩展view 的 dispatchTouchEvent
 * Created by meikai on 2019/11/28.
 */
public class MeasureExtendActivity extends BaseCompactActivity {


    View parent;
    View extend;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.measure_extend_activity;
    }

    @Override
    public void findViews() {
        parent = findViewById(R.id.parent_layout);
        extend = findViewById(R.id.extend_layout);

        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return extend.dispatchTouchEvent(event);
            }
        });

        extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击响应", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了按钮1", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了按钮2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }
}
