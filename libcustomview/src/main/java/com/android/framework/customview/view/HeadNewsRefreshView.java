package com.android.framework.customview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by meikai on 16/5/19.
 */
public class HeadNewsRefreshView extends View {

    private final static int
            DRAW = 1,
            POSITION_1 = 2,
            POSITION_2 = 3,
            POSITION_3 = 4,
            POSITION_4 = 5;

    private float
            line_1_start_x = 80,
            line_1_start_y = 36,
            line_2_start_x = 80,
            line_2_start_y = 51,
            line_3_start_x = 80,
            line_3_start_y = 66,
            line_4_start_x = 35,
            line_4_start_y = line_3_start_y + 2 + 13,
            line_5_start_x = 35,
            line_5_start_y = line_4_start_y + 2 + 13,
            line_6_start_x = 35,
            line_6_start_y = line_5_start_y + 2 + 13,
            rectFLeft = 35,
            rectFTop = 35,
            rectFRight = rectFLeft + 35,
            rectFBottom = rectFTop + 32,
            line_1_2_3_stop_x = 125,
            line_4_5_6_stop_x = 125;

    private float diffY;
    private int state = 1;

    private Paint paint, rectFPaintStroke, rectFPaint, rectFArcPaint, linePaint;
    private RectF topLeftRectF, topRightRectF, bottomLeftRectF, bottomRightRectF, rectFArcRectF;

    public HeadNewsRefreshView(Context context) {
        super(context);
        init();
    }

    public HeadNewsRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeadNewsRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeadNewsRefreshView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffE0E0E0);

        rectFPaintStroke = new Paint();
        rectFPaintStroke.setAntiAlias(true);
        rectFPaintStroke.setStrokeWidth(1);
        rectFPaintStroke.setStyle(Paint.Style.STROKE);
        rectFPaintStroke.setColor(0xffa0a0a0);

        rectFPaint = new Paint();
        rectFPaint.setAntiAlias(true);
        rectFPaint.setStyle(Paint.Style.FILL);
        rectFPaint.setColor(0xffe0e0e0);

        rectFArcPaint = new Paint();
        rectFArcPaint.setColor(0xffffffff);
        rectFArcPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(2);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(0xffa0a0a0);

        topRightRectF = new RectF(110, 20, 140, 50);
        topLeftRectF = new RectF(20, 20, 50, 50);
        bottomLeftRectF = new RectF(20, 110, 50, 140);
        bottomRightRectF = new RectF(110, 110, 140, 140);
        rectFArcRectF = new RectF(20, -11, 116, 85);
    }

    public void update(float diffY) {
        this.diffY = diffY;
        if (diffY <= 550) {
            state = DRAW;
            rectFLeft = 35;
            rectFTop = 35;
            rectFRight = rectFLeft + 35;
            rectFBottom = rectFTop + 32;

            line_1_start_x = 80;
            line_1_start_y = 36;
            line_2_start_x = 80;
            line_2_start_y = 51;
            line_3_start_x = 80;
            line_3_start_y = 66;
            line_4_start_x = 35;
            line_4_start_y = line_3_start_y + 2 + 13;
            line_5_start_x = 35;
            line_5_start_y = line_4_start_y + 2 + 13;
            line_6_start_x = 35;
            line_6_start_y = line_5_start_y + 2 + 13;
            line_1_2_3_stop_x = 125;
            line_4_5_6_stop_x = 125;
            invalidate();
        }
    }

    public void updatePosition() {
        state = (state + 1) % 6;
        if (state == 0 || state == 1) {
            state = 2;
        }
        invalidate();
    }

    public void reset(){
        this.diffY = 0;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("onDraw", diffY+"");
        if (diffY > 0) {
            if (state == DRAW) {
                if (diffY <= 135) {
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);
                    canvas.drawArc(rectFArcRectF, -45, 360 - (diffY * 360 / 135), true, rectFArcPaint);
                } else {
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);
                    if (diffY <= 180) {
                        canvas.drawLine(line_1_start_x, line_1_start_y, line_1_start_x + diffY - 135, line_1_start_y, linePaint);
                    } else if (diffY <= 225) {
                        canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                        canvas.drawLine(line_2_start_x, line_2_start_y, line_2_start_x + diffY - 180, line_2_start_y, linePaint);
                    } else if (diffY <= 270) {
                        canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                        canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                        canvas.drawLine(line_3_start_x, line_3_start_y, line_3_start_x + diffY - 225, line_3_start_y, linePaint);
                    } else {
                        canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                        canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                        canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);
                        if (diffY <= 360) {
                            canvas.drawLine(line_4_start_x, line_4_start_y, line_4_start_x + diffY - 270, line_4_start_y, linePaint);
                        } else if (diffY <= 450) {
                            canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                            canvas.drawLine(line_5_start_x, line_5_start_y, line_5_start_x + diffY - 360, line_5_start_y, linePaint);
                        } else if (diffY <= 540) {
                            canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                            canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                            canvas.drawLine(line_6_start_x, line_6_start_y, line_6_start_x + diffY - 450, line_6_start_y, linePaint);
                        } else {
                            canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                            canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                            canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                        }
                    }
                }
            } else if (state == POSITION_1) {
                if (rectFLeft < 80) {
                    rectFLeft += 3;
                    rectFRight += 3;
                    rectFTop = 35;
                    rectFBottom = rectFTop + 32;

                    line_4_start_x = 35;
                    line_4_start_y -= 3;
                    line_5_start_x = 35;
                    line_5_start_y -= 3;
                    line_6_start_x = 35;
                    line_6_start_y -= 3;

                    line_4_5_6_stop_x -= (90 - 35) * 3 / (80 - 35);

                    line_1_start_x -= 3;
                    line_1_start_y += 3;
                    line_2_start_x -= 3;
                    line_2_start_y += 3;
                    line_3_start_x -= 3;
                    line_3_start_y += 3;
                    line_1_2_3_stop_x = 125;

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                    invalidate();
                } else {
                    rectFLeft = 80;
                    rectFRight = rectFLeft + 35;
                    rectFTop = 35;
                    rectFBottom = rectFTop + 32;

                    line_4_start_x = 35;
                    line_4_start_y = 36;
                    line_5_start_x = 35;
                    line_5_start_y = 51;
                    line_6_start_x = 35;
                    line_6_start_y = 66;

                    line_4_5_6_stop_x = 70;

                    line_1_start_x = 35;
                    line_1_start_y = 66 + 2 + 13;
                    line_2_start_x = 35;
                    line_2_start_y = line_1_start_y + 2 + 13;
                    line_3_start_x = 35;
                    line_3_start_y = line_2_start_y + 2 + 13;
                    line_1_2_3_stop_x = 125;

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                }
            } else if (state == POSITION_2) {
                if (rectFTop < 80) {
                    rectFLeft = 80;
                    rectFRight = rectFLeft + 35;
                    rectFTop += 3;
                    rectFBottom += 3;

                    line_4_5_6_stop_x += (90 - 35) * 3 / (80 - 35);

                    line_1_2_3_stop_x -= (90 - 35) * 3 / (80 - 35);

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                    invalidate();
                } else {
                    rectFLeft = 80;
                    rectFRight = rectFLeft + 35;
                    rectFTop = 80;
                    rectFBottom = rectFTop + 32;

                    line_4_5_6_stop_x = 125;

                    line_1_2_3_stop_x = 70;

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                }
            } else if (state == POSITION_3) {
                if (rectFLeft > 35) {
                    rectFLeft -= 3;
                    rectFRight -= 3;

                    line_1_start_y -= 3;
                    line_2_start_y -= 3;
                    line_3_start_y -= 3;
                    line_1_2_3_stop_x += (90 - 35) * 3 / (80 - 35);

                    line_4_start_x += 3;
                    line_4_start_y += 3;
                    line_5_start_x += 3;
                    line_5_start_y += 3;
                    line_6_start_x += 3;
                    line_6_start_y += 3;

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                    invalidate();
                } else {
                    rectFLeft = 35;
                    rectFRight = rectFLeft + 35;

                    line_1_start_y = 36;
                    line_2_start_y = 51;
                    line_3_start_y = 66;
                    line_1_2_3_stop_x = 125;

                    line_4_start_x = 80;
                    line_4_start_y = 66 + 2 + 13;
                    line_5_start_x = 80;
                    line_5_start_y = line_4_start_y + 2 + 13;
                    line_6_start_x = 80;
                    line_6_start_y = line_5_start_y + 2 + 13;

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                }
            } else if (state == POSITION_4) {
                if (rectFTop > 35) {
                    rectFTop -= 3;
                    rectFBottom -= 3;

                    line_1_start_x += 3;
                    line_2_start_x += 3;
                    line_3_start_x += 3;

                    line_4_start_x -= 3;
                    line_5_start_x -= 3;
                    line_6_start_x -= 3;

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                    invalidate();
                } else {
                    rectFTop = 35;
                    rectFBottom = rectFTop + 32;

                    line_1_start_x = 80;
                    line_2_start_x = 80;
                    line_3_start_x = 80;

                    line_4_start_x = 35;
                    line_5_start_x = 35;
                    line_6_start_x = 35;

                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaint);
                    canvas.drawRect(rectFLeft, rectFTop, rectFRight, rectFBottom, rectFPaintStroke);

                    canvas.drawLine(line_1_start_x, line_1_start_y, line_1_2_3_stop_x, line_1_start_y, linePaint);
                    canvas.drawLine(line_2_start_x, line_2_start_y, line_1_2_3_stop_x, line_2_start_y, linePaint);
                    canvas.drawLine(line_3_start_x, line_3_start_y, line_1_2_3_stop_x, line_3_start_y, linePaint);

                    canvas.drawLine(line_4_start_x, line_4_start_y, line_4_5_6_stop_x, line_4_start_y, linePaint);
                    canvas.drawLine(line_5_start_x, line_5_start_y, line_4_5_6_stop_x, line_5_start_y, linePaint);
                    canvas.drawLine(line_6_start_x, line_6_start_y, line_4_5_6_stop_x, line_6_start_y, linePaint);
                }
            }


            if (diffY <= 45) {
                canvas.drawArc(topRightRectF, -diffY * 2, Math.abs(-diffY * 2), false, paint);
            } else if (diffY <= 135) {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 125 - (diffY - 45), 20, paint);
            } else if (diffY <= 180) {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 35, 20, paint);
                canvas.drawArc(topLeftRectF, -(diffY - 135) * 2 - 90, Math.abs(-(diffY - 135) * 2), false, paint);
            } else if (diffY <= 270) {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 35, 20, paint);
                canvas.drawArc(topLeftRectF, 180, 90, false, paint);
                canvas.drawLine(20, 35, 20, 35 + (diffY - 180), paint);
            } else if (diffY <= 315) {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 35, 20, paint);
                canvas.drawArc(topLeftRectF, 180, 90, false, paint);
                canvas.drawLine(20, 35, 20, 125, paint);
                canvas.drawArc(bottomLeftRectF, 180 - ((diffY - 270) * 2), (diffY - 270) * 2, false, paint);
            } else if (diffY <= 405) {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 35, 20, paint);
                canvas.drawArc(topLeftRectF, 180, 90, false, paint);
                canvas.drawLine(20, 35, 20, 125, paint);
                canvas.drawArc(bottomLeftRectF, 90, 90, false, paint);
                canvas.drawLine(35, 140, 35 + (diffY - 315), 140, paint);
            } else if (diffY <= 450) {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 35, 20, paint);
                canvas.drawArc(topLeftRectF, 180, 90, false, paint);
                canvas.drawLine(20, 35, 20, 125, paint);
                canvas.drawArc(bottomLeftRectF, 90, 90, false, paint);
                canvas.drawLine(35, 140, 125, 140, paint);
                canvas.drawArc(bottomRightRectF, 90 - ((diffY - 405) * 2), (diffY - 405) * 2, false, paint);
            } else if (diffY <= 540) {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 35, 20, paint);
                canvas.drawArc(topLeftRectF, 180, 90, false, paint);
                canvas.drawLine(20, 35, 20, 125, paint);
                canvas.drawArc(bottomLeftRectF, 90, 90, false, paint);
                canvas.drawLine(35, 140, 125, 140, paint);
                canvas.drawArc(bottomRightRectF, 0, 90, false, paint);
                canvas.drawLine(140, 125, 140, 125 - (diffY - 450), paint);
            } else {
                canvas.drawArc(topRightRectF, -90, 90, false, paint);
                canvas.drawLine(125, 20, 35, 20, paint);
                canvas.drawArc(topLeftRectF, 180, 90, false, paint);
                canvas.drawLine(20, 35, 20, 125, paint);
                canvas.drawArc(bottomLeftRectF, 90, 90, false, paint);
                canvas.drawLine(35, 140, 125, 140, paint);
                canvas.drawArc(bottomRightRectF, 0, 90, false, paint);
                canvas.drawLine(140, 125, 140, 35, paint);
            }
        }
    }

}
