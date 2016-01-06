package com.android.framework.demo.activity.shader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.framework.demo.R;

/**
 * Created by meikai on 16/1/6.
 */
public class PatternedTextView extends TextView {
    public PatternedTextView(Context context) {
        this(context, null);
    }

    public PatternedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PatternedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PatternedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.cheetah_tile);
        Shader shader = new BitmapShader(bitmap,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        getPaint().setShader(shader);
    }




}
