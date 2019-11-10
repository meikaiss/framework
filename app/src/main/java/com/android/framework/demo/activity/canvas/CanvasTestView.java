package com.android.framework.demo.activity.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * 参考： https://blog.csdn.net/u013394527/article/details/102966946
 * Created by meikai on 2019/11/08.
 */
public class CanvasTestView extends View {

    private Paint paint;

    public CanvasTestView(Context context) {
        super(context);
        init();
    }

    public CanvasTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CanvasTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画红色背景
        canvas.drawColor(Color.RED);

        //以初始默认视角 画黄色横线
        paint.setColor(Color.YELLOW);
        canvas.drawLine(0, 100, 400, 100, paint);

        canvas.save();
        // 将观察此canvas的肉身的视角  顺时针 旋转90度
        canvas.rotate(90, getMeasuredWidth()/2, getMeasuredHeight() / 2);
        // 以新的视角 来 绘横线
        paint.setColor(Color.WHITE);
        canvas.drawLine(100, 100, 300, 100, paint);
        // 恢复初始视角
        canvas.restore();

        // 以初始默认视角 绘线
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, 200, 400, 200, paint);

    }


}
