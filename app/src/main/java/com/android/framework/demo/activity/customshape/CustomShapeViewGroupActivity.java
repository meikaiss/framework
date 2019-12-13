package com.android.framework.demo.activity.customshape;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.framework.customview.viewgroup.MaskImageView;
import com.android.framework.demo.R;

/**
 * Created by meikai on 2019/09/17.
 */
public class CustomShapeViewGroupActivity extends AppCompatActivity {

    MaskImageView maskImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_shape_viewgroup);

        maskImageView = findViewById(R.id.mask_image_view);
        maskImageView.setImageResource(R.drawable.avatar);

    }

}
