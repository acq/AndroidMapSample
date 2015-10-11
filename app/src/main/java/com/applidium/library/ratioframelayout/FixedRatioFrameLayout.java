package com.applidium.library.ratioframelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.applidium.paris.R;

/*
 * Copy this file and the attrs.xml file to your project. In your xml layouts,
 * declare the xmlns:applidium="http://schemas.android.com/apk/res-auto"
 * namespace You can now use the applidium:ratio float attribute to set the
 * ratio of your FixedRatioFrameLayout
 */
public class FixedRatioFrameLayout extends FrameLayout {

    private static final double MIN_RATIO = 0.0001;
    private static final double DEF_RATIO = 1.0;

    private double              mRatio    = DEF_RATIO;

    public FixedRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FixedRatioFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.FixedRatioFrameLayout);
        setRatio(attrArray.getFloat(R.styleable.FixedRatioFrameLayout_ratio, (float) DEF_RATIO));
        attrArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMeasureSpec != 0 && heightMeasureSpec != 0) {
            width = (int) Math.round(Math.min(widthSize, heightSize * mRatio));
            height = (int) Math.round(Math.min(heightSize, widthSize / mRatio));
        } else if (heightMeasureSpec == 0) {
            // height: wrap_content
            width = widthSize;
            height = (int) Math.round(widthSize / mRatio);
        } else {
            // width: wrap_content
            height = heightSize;
            width = (int) Math.round(heightSize * mRatio);
        }
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    public double getRatio() {
        return mRatio;
    }

    public void setRatio(double ratio) {
        mRatio = Math.max(ratio, MIN_RATIO);
        this.requestLayout();
        this.invalidate();
    }

}
