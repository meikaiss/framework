package com.android.framework.customview.viewgroup;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.framework.customview.R;
import com.android.framework.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 俗名：垂直跑马灯
 * 学名：垂直翻页公告
 * <p>
 * Created by meikai on 16/7/7.
 */
public class MarqueeView extends ViewFlipper {

    private static final int TEXT_GRAVITY_LEFT = 0, TEXT_GRAVITY_CENTER = 1, TEXT_GRAVITY_RIGHT = 2;

    private static final int DEFAULT_TEXT_SIZE_SP = 14;
    private static final int DEFAULT_ANIM_DURATION = 500;
    private static final int DEFAULT_INTERVAL = 1000;

    private List<String> notices = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    private int interval; // 跑马灯向上滚动的周期, 单位:ms
    private long animDuration;  // 上下移动动画的 执行时间
    private int textSize = 0;  // 跑马灯内 显示的文字的大小 单位:px
    private int textColor = 0xffffffff;  // 跑马灯内 显示的文字的大小
    private boolean singleLine = false;
    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, DEFAULT_INTERVAL);
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, DEFAULT_ANIM_DURATION);
        singleLine = typedArray.getBoolean(R.styleable.MarqueeViewStyle_mvSingleLine, false);
        int defaultTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP, Resources.getSystem().getDisplayMetrics());
        textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, defaultTextSize);
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor);
        int gravityType = typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity, TEXT_GRAVITY_LEFT);
        switch (gravityType) {
            case TEXT_GRAVITY_CENTER:
                gravity = Gravity.CENTER;
                break;
            case TEXT_GRAVITY_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        typedArray.recycle();

        setFlipInterval(interval);

        Animation animIn = AnimationUtils.loadAnimation(getContext(), R.anim.anim_marquee_in);
        animIn.setDuration(animDuration);
        setInAnimation(animIn);

        Animation animOut = AnimationUtils.loadAnimation(getContext(), R.anim.anim_marquee_out);
        animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    /**
     * 根据公告字符串启动轮播
     *
     * @param notice 公告字符串
     */
    public void startWithText(final String notice) {
        if (TextUtils.isEmpty(notice)) return;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                startWithFixedWidth(notice, getWidth());
            }
        });
    }

    /**
     * 根据公告字符串列表启动轮播
     *
     * @param notices 公告字符串列表
     */
    public void startWithList(final List<String> notices) {

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                setNotices(notices);
                start();
            }
        });
    }

    /**
     * 根据宽度和公告字符串启动轮播
     *
     * @param notice
     * @param width  控件本身的测量后的宽度
     */
    private void startWithFixedWidth(String notice, int width) {
        int noticeLength = notice.length();
        int dpW = DensityUtil.px2dp(getContext(), width);
        int limit = dpW / textSize;
        if (dpW == 0) {
            throw new RuntimeException("Please set MarqueeView width !");
        }

        if (noticeLength <= limit) {
            notices.add(notice);
        } else {
            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
                notices.add(notice.substring(startIndex, endIndex));
            }
        }
        start();
    }

    /**
     * 启动轮播
     */
    public boolean start() {
        if (notices == null || notices.size() == 0) return false;
        removeAllViews();

        for (int i = 0; i < notices.size(); i++) {
            final TextView textView = createTextView(notices.get(i), i);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(finalI, textView);
                    }
                }
            });
            addView(textView);
        }

        if (notices.size() > 1) {
            startFlipping();
        }
        return true;
    }

    /**
     * 创建ViewFlipper下的TextView
     *
     * @param text
     * @param position
     * @return
     */
    private TextView createTextView(String text, int position) {
        TextView tv = new TextView(getContext());
        tv.setGravity(gravity);
        tv.setText(text);
        tv.setTextColor(textColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv.setSingleLine(singleLine);
        tv.setTag(position);
        return tv;
    }

    public int getPosition() {
        return (int) getCurrentView().getTag();
    }

    public List<String> getNotices() {
        return notices;
    }

    public void setNotices(List<String> notices) {
        for (int i = 0; i < notices.size(); i++) {
            String notice = notices.get(i);
            int noticeLength = notice.length();
            int dpW = DensityUtil.px2dp(getContext(), getWidth());
            int limit = dpW / textSize;
            if (dpW == 0) {
                throw new RuntimeException("Please set MarqueeView width !");
            }

            if (noticeLength <= limit) {
                this.notices.add(notice);
            } else {
                int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
                for (int j = 0; j < size; j++) {
                    int startIndex = j * limit;
                    int endIndex = ((j + 1) * limit >= noticeLength ? noticeLength : (j + 1) * limit);
                    this.notices.add(notice.substring(startIndex, endIndex));
                }
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, TextView textView);
    }

}
