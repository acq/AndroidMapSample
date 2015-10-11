package com.applidium.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.applidium.paris.R;

public class AutoResizingTextView extends TextView {

    private final static String LOG_TAG            = AutoResizingTextView.class.getSimpleName();

    private final static float  NO_SHRINKING_VALUE = Float.NEGATIVE_INFINITY;
    private final static float  DELTA_SIZE_SP      = 0.01f;
    private float               mMinTextSize;
    private int                 mMinLines, mMaxLines;

    private TextPaint           mPaint;

    public AutoResizingTextView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoResizingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoResizingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int heightSize = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        if (widthSize < 0 || heightSize < 0) {
            autoShrink(getText().toString(), getTextSize(), widthSize, heightSize);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(Context ctx, AttributeSet attrs) {
        mPaint = new TextPaint();
        mPaint.set(getPaint());
        if (attrs != null) {
            TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.AutoResizingTextView);
            setMinTextSize(attrArray.getDimension(R.styleable.AutoResizingTextView_minTextSize, NO_SHRINKING_VALUE));
            setCustomFont(ctx, attrArray.getString(R.styleable.AutoResizingTextView_customFont));
            setMinLines(attrArray.getInt(R.styleable.AutoResizingTextView_android_minLines, 0));
            setMaxLines(attrArray.getInt(R.styleable.AutoResizingTextView_android_maxLines, 0));
            attrArray.recycle();
        }
    }

    private void autoShrink(String text, float originalSize, int desiredWidth, int desiredHeight) {

        if (mMinTextSize == NO_SHRINKING_VALUE) {
            return;
        }

        float size = originalSize;
        if (originalSize <= mMinTextSize) {
            Log.w(LOG_TAG, "Text is too small. Android guidelines suggest a minimal size of 12sp");
        }

        StaticLayout layout = getLayout(originalSize, text, desiredWidth);
        if (layout.getHeight() < desiredHeight) {
            return;
        }

        float upperSize = originalSize;
        float lowerSize = mMinTextSize;
        float deltaSize = spToPx(DELTA_SIZE_SP);
        int lowerHeight = desiredHeight - getLineHeight(size, text);
        boolean biggerOrSmaller = layout.getHeight() > desiredHeight || layout.getHeight() < lowerHeight;

        while (biggerOrSmaller) {
            size = (upperSize + lowerSize) / 2;
            layout = getLayout(size, text, desiredWidth);
            lowerHeight = desiredHeight - getLineHeight(size, text);

            if (upperSize - size < deltaSize || size - lowerSize < deltaSize) {
                break;
            }

            if (layout.getHeight() > desiredHeight) {
                upperSize = size;
            } else if (layout.getHeight() < lowerHeight) {
                lowerSize = size;
            } else {
                break;
            }

            biggerOrSmaller = layout.getHeight() > desiredHeight || layout.getHeight() < lowerHeight;
        }

        if (size != originalSize) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    private StaticLayout getLayout(float size, String text, int maxWidth) {
        mPaint.setTextSize(size);
        return new StaticLayout(text, mPaint, maxWidth, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }

    private int getLineHeight(float size, String text) {
        mPaint.setTextSize(size);
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        if (asset == null) {
            return true;
        }
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }
        setTypeface(tf);
        return true;
    }

    public double getMinTextSize() {
        return mMinTextSize;
    }

    public void setMinTextSize(float minTextSize) {
        mMinTextSize = minTextSize < 0 ? NO_SHRINKING_VALUE : minTextSize;
    }

    public int getMinLines() {
        return mMinLines;
    }

    public int getMaxLines() {
        return mMaxLines;
    }

    public void setMinLines(int minLines) {
        mMinLines = minLines;
    }

    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
    }

    public float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }
}
