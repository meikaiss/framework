package com.android.framework.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by meikai on 15/12/26.
 */
public class SaveStateView extends View {

    private String drawString = "test";

    public SaveStateView(Context context) {
        super(context);
    }

    public SaveStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SaveStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SaveStateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public String getDrawString() {
        return drawString;
    }

    public void setDrawString(String drawString) {
        this.drawString = drawString;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint();
        paint.setColor(Color.RED);
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24, getContext().getResources().getDisplayMetrics());
        paint.setTextSize(textSize);
        canvas.drawText(drawString, 0 , 50, paint);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.e("SaveStateView", "SaveStateView__onSaveInstanceState" + drawString);
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.e("SaveStateView", "SaveStateView__onRestoreInstanceState"+drawString);
        super.onRestoreInstanceState(state);
    }
}

