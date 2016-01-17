package com.android.framework.viewgroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by meikai on 16/1/14.
 */
public class PictureSelectorGridView extends GridView {

    private int spaceWidth = 2;

    private float itemWidth = 0;

    public float getItemWidth() {
        return itemWidth;
    }

    public void setSpaceWidth(int spaceWidth) {
        this.spaceWidth = spaceWidth;
    }

    public PictureSelectorGridView(Context context) {
        super(context);
    }

    public PictureSelectorGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSelectorGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PictureSelectorGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getAdapter()!= null && getAdapter().getCount() > 0) {
            int numColumns = getNumColumns();
            itemWidth = (getMeasuredWidth() - spaceWidth * (numColumns - 1)) / (float)numColumns;

            int rowCount = getAdapter().getCount() / numColumns;
            if (getAdapter().getCount() % numColumns != 0)
                rowCount++;

            float height = itemWidth * rowCount + spaceWidth * (rowCount - 1);

            setMeasuredDimension(getMeasuredWidth(), (int) height);
        }
    }

}
