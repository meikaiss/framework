package com.android.framework.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.framework.customview.R;


/**
 * Created by meikai on 2018/01/29.
 */
public class RangeSeekBar extends View {

    private Bitmap bitmapBgOriginal; // 原始的未经压缩的bitmap，当背景图过大时需要压缩背景图
    private Bitmap bitmapBg;
    private Bitmap bitmapThumb;

    private Rect bgSrcRect;
    private Rect bgDstRect;
    private Rect thumbSrcRect;
    private Rect thumbDstRect;
    private Rect leftWhiteRect;
    private Rect leftWhiteDownRect;//手指按下时，左侧白色滑块的位置；仅用于记录，并非绘制时的依据
    private Rect rightWhiteRect;
    private Rect rightWhiteDownRect;//手指按下时右侧白色滑块的位置；仅用于记录，并非绘制时的依据

    private Paint paint;
    private Paint paintWhite;

    private int thumbMarginTop;//滑块与背景图之间的上下间距
    private boolean isDrag = false;
    private float downEventX;
    private Rect thumbDownRect; // 手指按下时的 按钮位置

    private long videoDuration;// videoDuration << audioDuration
    private long audioDuration;

    private OnDragChangeListener onDragChangeListener;

    // 从系统主页点击 应用图标 回到Activity时，会执行此控件的onMeasure方法，导致内部Rect重置，因此加这个标记
    private boolean hasMeasured = false;

    public void setOnDragChangeListener(OnDragChangeListener onDragChangeListener) {
        this.onDragChangeListener = onDragChangeListener;
    }

    public RangeSeekBar(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public RangeSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RangeSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar, defStyleAttr, defStyleRes);
        @DrawableRes int bgDrawableResId = arr.getResourceId(R.styleable.RangeSeekBar_bgDrawableRes, 0);
        @DrawableRes int thumbDrawableId = arr.getResourceId(R.styleable.RangeSeekBar_thumbDrawableRes, 0);
        thumbMarginTop = arr.getDimensionPixelSize(R.styleable.RangeSeekBar_dpGap, 0);
        arr.recycle();

        bitmapBgOriginal = BitmapFactory.decodeResource(getContext().getResources(), bgDrawableResId);
        bitmapBg = Bitmap.createBitmap(bitmapBgOriginal);
        bitmapThumb = BitmapFactory.decodeResource(getContext().getResources(), thumbDrawableId);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        paintWhite = new Paint();
        paintWhite.setAntiAlias(true);
        paintWhite.setDither(true);
        paintWhite.setColor(Color.WHITE);
        paintWhite.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int paddingHorizontal = 0;//用于:左右居中显示
        int measuredWidth = widthSize;
        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
            if (measuredWidth > bitmapBg.getWidth()) {
                paddingHorizontal = (measuredWidth - bitmapBg.getWidth()) / 2;
            }
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measuredWidth = bitmapBg.getWidth();
        }

        int measuredHeight = heightSize;
        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measuredHeight = bitmapBg.getHeight() + bitmapThumb.getHeight() + thumbMarginTop;
        }

        setMeasuredDimension(measuredWidth, measuredHeight);

        if (measuredWidth == 0 || measuredHeight == 0 || hasMeasured) {
            return;
        }

        if (measuredHeight != 0 && bitmapBgOriginal.getWidth() != 0 && measuredWidth < bitmapBgOriginal.getWidth()) {
            // 如果 控件宽度 小于 背景图片宽度，则需要将背景图片宽度压缩
            int newWidth = measuredWidth - bitmapThumb.getWidth();//左右预留一半滑块的宽度
            int newHeight = (int) ((float) bitmapBgOriginal.getHeight() * newWidth / bitmapBgOriginal.getWidth()
                    + 0.5f);
            if (newWidth > 0 && newHeight > 0) {
                bitmapBg = Bitmap.createScaledBitmap(bitmapBgOriginal, newWidth, newHeight, false);
            }

            //背景图的 两个 Rect
            bgSrcRect = new Rect(0, 0, bitmapBg.getWidth(), bitmapBg.getHeight());
            bgDstRect = new Rect(bitmapThumb.getWidth() / 2, 0, bitmapThumb.getWidth() / 2 + bitmapBg.getWidth(),
                    bitmapBg.getHeight());
        } else {
            // 如果 控件宽度 大于 背景图片宽度
            bitmapBg = Bitmap.createBitmap(bitmapBgOriginal);

            if (measuredWidth - bitmapBgOriginal.getWidth() >= bitmapThumb.getWidth()) {
                //控件宽度两侧 足以 容纳半个滑块宽度

                //背景图的 两个 Rect
                bgSrcRect = new Rect(0, 0, bitmapBg.getWidth(), bitmapBg.getHeight());
                if (paddingHorizontal > 0) {
                    bgDstRect = new Rect(paddingHorizontal, 0, paddingHorizontal + bitmapBg.getWidth(),
                            bitmapBg.getHeight());
                } else {
                    bgDstRect = bgSrcRect;
                }
            } else {
                //控件宽度两侧 不足以 容纳半个滑块宽度，则仍然需要将背景图压缩，使两侧足以容纳半个滑块的宽度
                int newWidth = measuredWidth - bitmapThumb.getWidth();//左右预留一半滑块的宽度
                int newHeight = (int) ((float) bitmapBgOriginal.getHeight() * newWidth / bitmapBgOriginal.getWidth()
                        + 0.5f);
                if (newWidth > 0 && newHeight > 0) {
                    bitmapBg = Bitmap.createScaledBitmap(bitmapBgOriginal, newWidth, newHeight, false);
                }

                //背景图的 两个 Rect
                bgSrcRect = new Rect(0, 0, bitmapBg.getWidth(), bitmapBg.getHeight());
                bgDstRect = new Rect(bitmapThumb.getWidth() / 2, 0, bitmapThumb.getWidth() / 2 + bitmapBg.getWidth(),
                        bitmapBg.getHeight());
            }
        }

        //滑块的 两个 Rect
        thumbSrcRect = new Rect(0, 0, bitmapThumb.getWidth(), bitmapThumb.getHeight());
        int thumbTop = bitmapBg.getHeight() + thumbMarginTop;
        int thumbLeft = bgDstRect.left - bitmapThumb.getWidth() / 2;//默认滑块指针指向左侧起点位置
        if (audioDuration > 0 && videoDuration > 0) {
            int videoWidth = (int) ((float) videoDuration / audioDuration * bgDstRect.width());
            thumbLeft += videoWidth / 2;
        }
        thumbDstRect = new Rect(thumbLeft, thumbTop, thumbLeft + bitmapThumb.getWidth(),
                thumbTop + bitmapThumb.getHeight());

        //左侧 白色矩形 Rect
        leftWhiteRect = new Rect(bgDstRect);
        leftWhiteRect.right = leftWhiteRect.left; //默认从起点开始

        //右侧 白色矩形 Rect
        rightWhiteRect = new Rect(bgDstRect);
        if (audioDuration != 0) {
            rightWhiteRect.left = leftWhiteRect.right + (int) ((float) videoDuration / audioDuration * bgDstRect
                    .width());
        }

        hasMeasured = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmapBg, bgSrcRect, bgDstRect, paint);
        canvas.drawBitmap(bitmapThumb, thumbSrcRect, thumbDstRect, paint);

        if (videoDuration != 0 && audioDuration != 0) {
            canvas.drawRect(leftWhiteRect, paintWhite);
            canvas.drawRect(rightWhiteRect, paintWhite);
        }
    }

    /**
     * 判断 此位置 的 触摸事件 能否 触发 拖拽
     */
    private boolean isValidDragPosition(MotionEvent event) {
        if (event.getActionMasked() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        boolean canDragInThumbMarginTop = true;//标识:滑块与背景图片之间的marginTop 间隙 是否可以 触发拖拽
        if (event.getX() >= thumbDstRect.left && event.getX() <= thumbDstRect.right
                && event.getY() >= thumbDstRect.top - (canDragInThumbMarginTop ? thumbMarginTop : 0)
                && event.getY() <= thumbDstRect.bottom) {
            // 点到了 滑块上(包括滑块与背景图片之间的marginTop)， 可以触发滑动
            return true;
        }
        boolean canDragVideoArea = true;//标识:视频颜色区域是否可以触发拖拽
        if (canDragVideoArea && event.getX() >= leftWhiteRect.right && event.getX() <= rightWhiteRect.left
                && event.getY() >= bgDstRect.top && event.getY() <= bgDstRect.bottom) {
            // 点到了 视频颜色区域，也可以触发滑动
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (isValidDragPosition(event)) {
                    isDrag = true;
                    downEventX = event.getX();
                    thumbDownRect = new Rect(thumbDstRect);
                    leftWhiteDownRect = new Rect(leftWhiteRect);
                    rightWhiteDownRect = new Rect(rightWhiteRect);
                    log("开始拖拽, downEventX=" + downEventX);
                    return true;// down事件若被消费，必须返回true
                } else {
                    log("没有点中");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDrag) {
                    int deltaX = (int) (event.getX() - downEventX + 0.5f);

                    log("正在正常拖拽,更新拖拽界面, deltaX=" + deltaX + ",event=" + event.getX());

                    leftWhiteRect.right = leftWhiteDownRect.right + deltaX;
                    rightWhiteRect.left = rightWhiteDownRect.left + deltaX;

                    thumbDstRect.left = thumbDownRect.left + deltaX;
                    thumbDstRect.right = thumbDownRect.right + deltaX;

                    //视频颜色的宽度（单位:像素）
                    int videoWidth = (int) ((float) videoDuration / audioDuration * bgDstRect.width());

                    // 到达最左侧或最右侧，需要纠正滑动过头的偏差
                    if (leftWhiteRect.right < leftWhiteRect.left || rightWhiteRect.left > rightWhiteRect.right) {
                        if (leftWhiteRect.right < leftWhiteRect.left) {
                            // 滑到了 最左侧
                            leftWhiteRect.right = leftWhiteRect.left;//防止左侧白块溢出
                            rightWhiteRect.left = leftWhiteRect.right + videoWidth;//防止右侧白块过度往左侧延伸

                            // 将 滑块 重置 为左侧 对齐（滑块中点对齐到视频宽的中点，并且音频处于起点位置）
                            int thumbLeft = bgDstRect.left - bitmapThumb.getWidth() / 2;//默认滑块指针指向左侧起点位置
                            if (audioDuration > 0 && videoDuration > 0) {
                                thumbLeft += videoWidth / 2;
                            }
                            int thumbTop = bitmapBg.getHeight() + thumbMarginTop;
                            thumbDstRect = new Rect(thumbLeft, thumbTop, thumbLeft + bitmapThumb.getWidth(),
                                    thumbTop + bitmapThumb.getHeight());
                        } else if (rightWhiteRect.left > rightWhiteRect.right) {
                            // 滑到了 最右侧
                            rightWhiteRect.left = rightWhiteRect.right;//防止右侧白块溢出
                            leftWhiteRect.right = rightWhiteRect.left - videoWidth;//防止左侧白块过度往右侧延伸

                            // 将 滑块 重置 为右侧 对齐（滑块中点对齐到视频宽的中点，并且音频处于终点位置）
                            int thumbLeft = bgDstRect.right - bitmapThumb.getWidth() / 2;//默认滑块指针指向右侧终点位置
                            if (audioDuration > 0 && videoDuration > 0) {
                                thumbLeft -= videoWidth / 2;
                            }
                            int thumbTop = bitmapBg.getHeight() + thumbMarginTop;
                            thumbDstRect = new Rect(thumbLeft, thumbTop, thumbLeft + bitmapThumb.getWidth(),
                                    thumbTop + bitmapThumb.getHeight());
                        }
                    }

                    if (onDragChangeListener != null) {
                        float startPosition = (float) leftWhiteRect.width() / bgDstRect.width() * audioDuration;
                        float endPosition = startPosition + videoDuration;
                        onDragChangeListener.onDragChange((long) (startPosition + 0.5f), (long) (endPosition + 0.5f));
                    }

                    invalidate();
                } else {
                    log("没有拖拽,不更新拖拽界面");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isDrag) {
                    isDrag = false;
                    downEventX = 0f;

                    if (onDragChangeListener != null) {
                        float startPosition = (float) leftWhiteRect.width() / bgDstRect.width() * audioDuration;
                        float endPosition = startPosition + videoDuration;
                        onDragChangeListener.onDragConfirm((long) (startPosition + 0.5f), (long) (endPosition + 0.5f));
                    }

                    log("释放拖拽界面");
                } else {
                    log("没有拖拽，因此不需要释放");
                }

                break;
        }

        return super.onTouchEvent(event);
    }

    public void setDuration(long videoDuration, long audioDuration) {
        this.videoDuration = videoDuration;
        this.audioDuration = audioDuration;

        if (bgDstRect != null) {
            leftWhiteRect = new Rect(bgDstRect);
            leftWhiteRect.right = leftWhiteRect.left;

            rightWhiteRect = new Rect(bgDstRect);
            rightWhiteRect.left = leftWhiteRect.right + (int) (((float) videoDuration / audioDuration) * bgDstRect
                    .width());

            //重置滑块的 两个 Rect
            thumbSrcRect = new Rect(0, 0, bitmapThumb.getWidth(), bitmapThumb.getHeight());
            int thumbTop = bitmapBg.getHeight() + thumbMarginTop;
            int thumbLeft = bgDstRect.left - bitmapThumb.getWidth() / 2;//默认滑块指针指向左侧起点位置
            if (audioDuration > 0 && videoDuration > 0) {
                int videoWidth = (int) ((float) videoDuration / audioDuration * bgDstRect.width());
                thumbLeft += videoWidth / 2;
            }
            thumbDstRect = new Rect(thumbLeft, thumbTop, thumbLeft + bitmapThumb.getWidth(),
                    thumbTop + bitmapThumb.getHeight());

            invalidate();
        }
    }

    private void log(String msg) {
    }


    public interface OnDragChangeListener {
        void onDragChange(long startPosition, long endPosition);

        void onDragConfirm(long startPosition, long endPosition);
    }
}
