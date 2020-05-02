package com.android.framework.demo.activity.span;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 2020/02/24.
 */
public class SpanActivity extends BaseCompactActivity {

    TextView tvSpan;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_span;
    }

    @Override
    public void findViews() {
        tvSpan = findViewById(R.id.tv_span);

        tvSpan.setHighlightColor(getResources().getColor(android.R.color.transparent));

        String string = "我是和常常大声点发大水发送到发送到发";
        String url = "https://www.2345.com";

        SpannableString spannableString = new SpannableString(string);
        ClickableSpan clickableSpan = new ClickableSpan() {
            String forUrl = url;

            @Override
            public void onClick(View widget) {
                Toast.makeText(SpanActivity.this, "点击了" + forUrl, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xff00ff00);
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        tvSpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SpanActivity.this, "点击了整个TextView", Toast.LENGTH_SHORT).show();
            }
        });
        spannableString.setSpan(clickableSpan, 3, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvSpan.setText(spannableString);
        tvSpan.setMovementMethod(LinkMovementMethod.getInstance());


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
