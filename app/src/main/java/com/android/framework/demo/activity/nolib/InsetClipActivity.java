package com.android.framework.demo.activity.nolib;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.android.framework.demo.R;

/**
 * Created by meikai on 16/2/14.
 */
public class InsetClipActivity extends AppCompatActivity {

    AppCompatSeekBar seekBar;
    ImageView clipImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inset_clip);

        seekBar = (AppCompatSeekBar) findViewById(R.id.clip_seek_bar);


        clipImageView = (ImageView) findViewById(R.id.image_clip);
        ClipDrawable drawable = (ClipDrawable) clipImageView.getBackground();
        drawable.setLevel(5000);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.e("onProgressChanged", "progress * 100=" + progress * 100);
                ClipDrawable drawable = (ClipDrawable) clipImageView.getBackground();
                drawable.setLevel(progress * 100);

                if (progress == 0)
                    drawable.setLevel(0);
                clipImageView.invalidate();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipDrawable drawable = (ClipDrawable) clipImageView.getBackground();
                drawable.setLevel(0);
            }
        });
    }



}
