package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.framework.customview.viewgroup.MarqueeView;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/7/7.
 */
public class MarqueeViewActivity extends AppCompatActivity {

    private MarqueeView marqueeView0;
    private MarqueeView marqueeView1;
    private MarqueeView marqueeView2;
    private MarqueeView marqueeView3;
    private MarqueeView marqueeView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_view);

        marqueeView0 = (MarqueeView) findViewById(R.id.marqueeView0);
        marqueeView1 = (MarqueeView) findViewById(R.id.marqueeView1);
        marqueeView2 = (MarqueeView) findViewById(R.id.marqueeView2);
        marqueeView3 = (MarqueeView) findViewById(R.id.marqueeView3);
        marqueeView4 = (MarqueeView) findViewById(R.id.marqueeView4);

        List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是梅凯");
        info.add("2. 一个集美貌与才华于一身的程序猿！");
        info.add("3. 欢迎大家关注我哦！");
        info.add("4. 把你横向挣满了你会自动换行吗！把你横向挣满了你会自动换行吗！把你横向挣满了你会自动换行吗！");
        marqueeView0.startWithList(info);

        marqueeView1.startWithText(getString(R.string.marquee_texts));
        marqueeView2.startWithText(getString(R.string.marquee_texts));
        marqueeView3.startWithText(getString(R.string.marquee_texts));
        marqueeView4.startWithText(getString(R.string.marquee_text));

        marqueeView0.setOnItemClickListener((position, textView) ->
                Toast.makeText(getApplicationContext(), textView.getText() + "", Toast.LENGTH_SHORT).show());

        marqueeView1.setOnItemClickListener((position, textView) ->
                Toast.makeText(getApplicationContext(), String.valueOf(marqueeView1.getPosition()) + ". " + textView.getText(), Toast.LENGTH_SHORT).show());
    }


}
