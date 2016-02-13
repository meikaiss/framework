package com.android.framework.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.android.framework.demo.R;
import com.android.framework.libui.view.FuseImageView;

/**
 * Created by meikai on 16/2/3.
 */
public class FuseImageActivity extends AppCompatActivity {

    FuseImageView fuseImageView1;
    FuseImageView fuseImageView2;
    FuseImageView fuseImageView3;
    FuseImageView fuseImageView4;
    AppCompatSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuse_image);

        fuseImageView1 = (FuseImageView) findViewById(R.id.fuse_image_view1);
        fuseImageView2 = (FuseImageView) findViewById(R.id.fuse_image_view2);
        fuseImageView3 = (FuseImageView) findViewById(R.id.fuse_image_view3);
        fuseImageView4 = (FuseImageView) findViewById(R.id.fuse_image_view4);

        seekBar = (AppCompatSeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fuseImageView1.setFusedAlpha(progress);
                fuseImageView2.setFusedAlpha(progress);
                fuseImageView3.setFusedAlpha(progress);
                fuseImageView4.setFusedAlpha(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
