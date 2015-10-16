package com.applidium.paris.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DirectionView extends View {
    private Double heading;

    public DirectionView(Context context) {
        super(context);
    }

    public DirectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DirectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHeading(Double angle) {
        if (angle != null) {
            this.heading = angle - Math.PI / 2;
        } else {
            this.heading = null;
        }
    }

    private final Paint mPaint;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (heading == null) {
            return;
        }
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int arrowLength = Math.min(getWidth(), getHeight()) / 2;
        int arrowX = (int) (centerX + arrowLength * Math.cos(heading));
        int arrowY = (int) (centerY + arrowLength * Math.sin(heading));
        int rightEdgeX = (int) (centerX + arrowLength * Math.cos(heading + 2 * Math.PI / 3) / 2);
        int rightEdgeY = (int) (centerX + arrowLength * Math.sin(heading + 2 * Math.PI / 3) / 2);
        int leftEdgeX = (int) (centerX + arrowLength * Math.cos(heading - 2 * Math.PI / 3) / 2);
        int leftEdgeY = (int) (centerX + arrowLength * Math.sin(heading - 2 * Math.PI / 3) / 2);
        canvas.drawLine(centerX, centerY, rightEdgeX, rightEdgeY, mPaint);
        canvas.drawLine(rightEdgeX, rightEdgeY, arrowX, arrowY, mPaint);
        canvas.drawLine(arrowX, arrowY, leftEdgeX, leftEdgeY, mPaint);
        canvas.drawLine(leftEdgeX, leftEdgeY, centerX, centerY, mPaint);
    }
}
