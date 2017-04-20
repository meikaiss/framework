package com.android.framework.demo.requesttest;

import android.content.Intent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/12/6.
 */
public class RequestTestActivity extends BaseCompactActivity {


    MyView myView3;
    MyViewGroup myViewGroup2;
    HorizontalScrollView scrollView;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_request_layout_test;
    }

    @Override
    public void findViews() {

        myView3 = (MyView) findViewById(R.id.my_view_3);
        myViewGroup2 = (MyViewGroup) findViewById(R.id.my_view_group_2);
        scrollView = f(R.id.horizontal_scroll_view);
        f(R.id.btn_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(100, 0);
            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) myView3.getLayoutParams();

                lp.height = lp.height + 100;

                myView3.setLayoutParams(lp);
            }
        });

        findViewById(R.id.btn_test_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) myViewGroup2.getLayoutParams();

                lp.height = lp.height + 100;

                myViewGroup2.setLayoutParams(lp);
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
