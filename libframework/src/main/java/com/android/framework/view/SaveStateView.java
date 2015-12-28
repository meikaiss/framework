package com.android.framework.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by meikai on 15/12/26.
 */
public class SaveStateView extends View {

    public String drawString = "test";

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
        canvas.drawText(drawString, 0, 50, paint);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.e("SaveStateView", "SaveStateView__onSaveInstanceState,drawString=" + drawString);

        Parcelable parcelable = super.onSaveInstanceState();
        return new MySaveState(parcelable, drawString);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.e("SaveStateView", "SaveStateView__onRestoreInstanceState,drawString=" + drawString);
        if( !(state instanceof MySaveState)){
            super.onRestoreInstanceState(state);
        }else{
            MySaveState ss = (MySaveState) state;
            super.onRestoreInstanceState(ss.getSuperState());

            drawString = ((MySaveState)state).drawString;
        }
    }


    static class MySaveState extends BaseSavedState {

        String drawString;

        public MySaveState(Parcelable superState, String drawString) {
            super(superState);
            this.drawString = drawString;
        }

        public MySaveState(Parcel source) {
            super(source);
            Log.e("SaveStateView", "MySaveState__Parcel,读取前drawString=" + drawString);
            this.drawString = source.readString();
            Log.e("SaveStateView", "MySaveState__Parcel,读取后drawString=" + drawString);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(drawString);
            Log.e("SaveStateView", "MySaveState__writeToParcel,drawString=" + drawString);
        }

        public static final Parcelable.Creator<MySaveState> CREATOR =
                new Parcelable.Creator<MySaveState>() {
                    public MySaveState createFromParcel(Parcel in) {
                        return new MySaveState(in);
                    }

                    public MySaveState[] newArray(int size) {
                        return new MySaveState[size];
                    }
                };

    }
}

