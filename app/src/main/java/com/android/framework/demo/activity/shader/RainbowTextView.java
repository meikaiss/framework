package com.android.framework.demo.activity.shader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by meikai on 16/1/6.
 */
public class RainbowTextView extends TextView {

    public RainbowTextView(Context context) {
        this(context, null);
    }

    public RainbowTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainbowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RainbowTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int[] rainbow = getRainbowColors();
        Shader shader = new LinearGradient(0, 0, 0, right- left, rainbow,
                null, Shader.TileMode.MIRROR);

        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        shader.setLocalMatrix(matrix);

        getPaint().setShader(shader);
    }

    private int[] getRainbowColors() {
        return new int[]{
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE,
                Color.GRAY
        };
    }
}
