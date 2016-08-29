package com.android.framework.demo.activity.viewdemo;

import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.viewgroup.xflowlayout.XFlowLayout;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/8/25.
 */
public class XFlowLayoutActivity extends BaseCompactActivity {

    XFlowLayout xFlowLayout;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_x_flow_layout;
    }

    @Override
    public void findViews() {
        xFlowLayout = f(R.id.x_flow_layout);

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

        for (int i = 0; i < 16; i++) {
            TextView textView = new TextView(this);

            int random = (int) (Math.random() * 10) + 1;
            String number = "";
            for (int j = 0; j < random; j++) {
                number += "" + i;
            }

            textView.setText(number + "号儿子");
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(30, 10, 30, 10);
            textView.setBackgroundResource(R.drawable.selector_flex_box_item);

            int valueOf6dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, Resources.getSystem().getDisplayMetrics());
            int pxOf32dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, Resources.getSystem().getDisplayMetrics());
            XFlowLayout.LayoutParams lp = new XFlowLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, pxOf32dp);
            lp.setMargins(valueOf6dp, valueOf6dp, valueOf6dp, valueOf6dp);

            xFlowLayout.addView(textView, lp);
        }

    }

}
