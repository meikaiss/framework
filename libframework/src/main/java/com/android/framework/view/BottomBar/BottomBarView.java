package com.android.framework.view.BottomBar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.android.framework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 15/11/29.
 */
public class BottomBarView extends LinearLayout{

    //上下文
    private Context context;
    //选中后字的颜色
    private Integer mSelectColor;
    //未选中字的颜色
    private Integer mUnSelectColor;
    //选项文字
    private List<String> mTextList = new ArrayList<>();
    //选项图片
    private List<Bitmap> mSelectImageList = new ArrayList<>();
    private List<Bitmap> mSelectImageList2 = new ArrayList<>();
    //摁扭集合
    private List<BottomButton> mBtnList = new ArrayList<>();
    //防止频繁点击
    private Long mClickTimeMillis = System.currentTimeMillis();
    //默认选中下标
    private Integer mDefaultSelectIndex = 0;
    //点击事件暴露监听器对象
    private OnBtnClickListener mBtnClickListener = null;

    public BottomBarView(Context context) {
        super(context);
        this.context = context;
        initBar();
    }

    public BottomBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initBar();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarView);
        this.mSelectColor = ta.getColor(R.styleable.BottomBarView_select_color, BottomButton.DEFAULT_SELECT_COLOR);
        this.mUnSelectColor = ta.getColor(R.styleable.BottomBarView_unselect_color, BottomButton.DEFAULT_UNSELECT_COLOR);
        ta.recycle();
    }

    public BottomBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initBar();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarView);
        this.mSelectColor = ta.getColor(R.styleable.BottomBarView_select_color, BottomButton.DEFAULT_SELECT_COLOR);
        this.mUnSelectColor = ta.getColor(R.styleable.BottomBarView_unselect_color, BottomButton.DEFAULT_UNSELECT_COLOR);
        ta.recycle();
    }

    public BottomBarView addBtn(int index, int drawable1, int drawable2, String text) {
        Resources res = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, drawable1);
        if(drawable2>0){
            Bitmap bmp2 = BitmapFactory.decodeResource(res, drawable2);
            this.mSelectImageList2.add(index, bmp2);
        }else {
            this.mSelectImageList2.add(index, null);
        }
        this.mSelectImageList.add(index, bmp);
        this.mTextList.add(index, text);
        return this;
    }

    public BottomBarView removeAllBtn() {
        this.mSelectImageList.clear();
        this.mTextList.clear();
        return this;
    }

    public BottomBarView removeBtn(int index) {
        this.mSelectImageList.remove(index);
        this.mTextList.remove(index);
        return this;
    }

    public int getBtnCount() {
        return this.mTextList.size();
    }

    private void btnChange() {
        this.removeAllViews();
        for (int i = 0; i < getBtnCount(); i++) {
            BottomButton btn = new BottomButton(this.context,mSelectImageList.get(i),
                    mSelectImageList2.get(i), mTextList.get(i));
            btn.setIndex(i);
            btn.setImgThreshold(30);
            btn.setTextMarginImg(8);
            btn.setSelectColor(mSelectColor);
            btn.setUnSelectColor(mUnSelectColor);
            this.addView(btn);
            mBtnList.add(btn);
        }
        setBtnListenter();

        if(mBtnList.size() > 0 && mBtnList.size()-1-this.mDefaultSelectIndex >= 0){
            //由于设置了点击时间间隙,要进行处理,否则点击不了
            mClickTimeMillis = 0L;
            //默认点击
            mBtnList.get(this.mDefaultSelectIndex).performClick();
        }
    }

    private void setBtnListenter() {
        for (int i = 0; i < mBtnList.size(); i++) {
            final BottomButton b = mBtnList.get(i);
            b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (System.currentTimeMillis() - mClickTimeMillis < BottomButton.CLICK_INTERVAL) {
                        return;
                    }
                    if (!((BottomButton) v).isCheck()) {
                        changeLightBtn((BottomButton)v,true);
                        mClickTimeMillis = System.currentTimeMillis();
                    }
                }
            });
        }
    }

    private void initBar() {
        this.setOrientation(LinearLayout.HORIZONTAL);
    }

    public void notifyBtnChanged() {
        btnChange();
    }

    public void setDefaultSelectIndex(Integer mDefaultSelectIndex) {
        this.mDefaultSelectIndex = mDefaultSelectIndex;
    }

    public interface OnBtnClickListener {
        void onClick(BottomButton arg0);
    }

    public void setOnBtnClickListener(OnBtnClickListener l) {
        this.mBtnClickListener = l;
    }

    private void onBtnClick(BottomButton btn) {
        if (this.mBtnClickListener != null) {
            this.mBtnClickListener.onClick(btn);
        }
    }

    public List<BottomButton> getBtnList() {
        return mBtnList;
    }

    public void changeLightBtn(final BottomButton b,boolean isCallBtnClick){
        for (int j = 0; j < mBtnList.size(); j++) {
            if(b.getIndex() != j){
                mBtnList.get(j).setCheck(false);
            }
        }
        b.setCheck(true);
        if(isCallBtnClick){
            onBtnClick(b);
        }
    }

    public void changeLightBtn(int index, boolean isCallBtnClick){
        if(index < 0 || mBtnList.size()-1 < index){
            return;
        }
        final BottomButton b = mBtnList.get(index);
        changeLightBtn(b, isCallBtnClick);
    }

    public void changeLightBtnWithoutAlpha(final BottomButton b){
        for (int j = 0; j < mBtnList.size(); j++) {
            if(b.getIndex() != j){
                mBtnList.get(j).setCheck(false);
            }
        }
        b.setCheckWithoutAlpha(true);
    }

    public void changeLightBtnWithoutAlpha(int index){
        if(index < 0 || mBtnList.size()-1 < index){
            return;
        }
        final BottomButton b = mBtnList.get(index);
        changeLightBtnWithoutAlpha(b);
    }
}
