package com.android.framework.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by meikai on 16/5/6.
 */
public class DrawableCenterTextView extends TextView {

    public DrawableCenterTextView(Context context) {
        super(context);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            Drawable drawableBottom = drawables[3];
            if (drawableLeft != null) {

                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
            if (drawableRight != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableRight.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }

            if (drawableBottom != null) {
                float textWidth = getPaint().getTextSize() + 10;
                int drawablePadding = getCompoundDrawablePadding();
                int drawableHeight = 0;
                drawableHeight = drawableBottom.getIntrinsicHeight();
                float bodyWidth = textWidth + drawableHeight + drawablePadding;
                setPadding(0, 0, 0, (int) (getHeight() - bodyWidth));
                canvas.translate(0, (getHeight() - bodyWidth) / 2);
            }
        }
        super.onDraw(canvas);
    }
}
