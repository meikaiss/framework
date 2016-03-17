package com.android.framework.demo.activity.viewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.framework.demo.R;
import com.android.framework.libui.view.ImageOuterTextView;

/**
 * Created by meikai on 16/3/17.
 */
public class ImageOuterTextViewActivity extends AppCompatActivity {

    ImageOuterTextView imageOuterTextView1;
    ImageOuterTextView imageOuterTextView2;
    ImageOuterTextView imageOuterTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_outer_textview);

        imageOuterTextView1 = (ImageOuterTextView) findViewById(R.id.imageOuterTextView1);
        imageOuterTextView2 = (ImageOuterTextView) findViewById(R.id.imageOuterTextView2);
        imageOuterTextView3 = (ImageOuterTextView) findViewById(R.id.imageOuterTextView3);

        ((Button) findViewById(R.id.btn_add_score)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOuterTextView1.setInnerText(
                        (Integer.parseInt(imageOuterTextView1.getInnerText().split("分")[0]) + 1) + "分"
                );
                imageOuterTextView2.setInnerText(
                        (Integer.parseInt(imageOuterTextView2.getInnerText().split("分")[0]) + 1) + "分"
                );
                imageOuterTextView3.setInnerText(
                        (Integer.parseInt(imageOuterTextView3.getInnerText().split("分")[0]) + 1) + "分"
                );
            }
        });

        ((Button) findViewById(R.id.btn_modify_text_color)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageOuterTextView1.getInnerTextColor() == Color.parseColor("#45c018"))
                    imageOuterTextView1.setInnerTextColor(Color.parseColor("#f25e5e"));
                else
                    imageOuterTextView1.setInnerTextColor(Color.parseColor("#45c018"));
            }
        });

        ((Button) findViewById(R.id.btn_modify_drawable)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageOuterTextView1.getOuterDrawableId() == R.drawable.image_out_text_view_green)
                    imageOuterTextView1.setOuterDrawableId(R.drawable.image_out_text_view_red);
                else
                    imageOuterTextView1.setOuterDrawableId(R.drawable.image_out_text_view_green);
            }
        });

    }
}
