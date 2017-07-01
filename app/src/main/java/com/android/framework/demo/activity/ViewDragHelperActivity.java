package com.android.framework.demo.activity;

import android.content.Intent;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.viewgroup.VDHLayout;
import com.android.framework.demo.R;

/**
 * Created by meikai on 17/6/6.
 */
public class ViewDragHelperActivity extends BaseCompactActivity {

    private VDHLayout vdhLayout;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_view_drag_helper;
    }

    @Override
    public void findViews() {

        vdhLayout = (VDHLayout) findViewById(R.id.vdh_layout);

        vdhLayout.setOnDragFinishListener(new VDHLayout.OnDragFinishListener() {
            @Override
            public void onDragFinish() {
                Toast.makeText(ViewDragHelperActivity.this, "可以移除了", Toast.LENGTH_SHORT).show();
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