package com.android.framework.customview.viewgroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.framework.customview.R;

/**
 * Created by meikai on 16/10/31.
 */
public class FloatDragFrameLayout extends FrameLayout {

    private ImageView floatImageView;
    private
    @DrawableRes
    int floatImageResourceId;
    private int floatWidth;
    private int floatHeight;

    public FloatDragFrameLayout(Context context) {
        this(context, null);
    }

    public FloatDragFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatDragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FloatDragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatDragFrameLayout);

        try {
            floatImageResourceId = a.getResourceId(R.styleable.FloatDragFrameLayout_float_image_res, 0);
        } catch (Exception e) {

        } finally {
            a.recycle();
        }

        floatWidth = floatHeight = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, Resources.getSystem()
                        .getDisplayMetrics());

        dp25 = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, Resources.getSystem()
                        .getDisplayMetrics());
    }

    int dp25;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        floatImageView = new ImageView(getContext());

        if (floatImageResourceId > 0) {
            floatImageView.setImageResource(floatImageResourceId);
        }

        FrameLayout.LayoutParams lp = new LayoutParams(floatWidth, floatHeight);

        addView(floatImageView, lp);

        floatImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String action = "";
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        action = "ACTION_DOWN";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        action = "ACTION_MOVE";

                        floatImageView.setTranslationX(event.getRawX() - floatImageView.getMeasuredWidth() / 2);

                        floatImageView.setTranslationY(event.getRawY() - dp25 - floatImageView.getMeasuredHeight() / 2);

                        break;
                    case MotionEvent.ACTION_UP:
                        action = "ACTION_UP  ";
                        break;
                    default:
                        break;
                }

                Log.e("FloatDragFrameLayout",
                        "action=" + action + ",rawX=" + event.getRawX() + ", rawY=" + event.getRawY());

                return true;
            }
        });

    }
}
