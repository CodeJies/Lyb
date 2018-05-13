package com.codejies.lyb.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LybLoadingProgress extends View {
    private int radiu;
    private int arc;
    private int color;
    private Paint mPaint;
    private RectF oval;
    private int startArc = 0;
    private int endArc = 0;

    private int width, heigth;

    public LybLoadingProgress(Context context) {
        super(context);
        init();
    }

    public LybLoadingProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LybLoadingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setColor(color == 0 ? Color.BLUE : color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("View", "getWidth:" + getWidth() + "\n" + "getHeight" + getHeight() + "\n" + "MeasureWidth" + getMeasuredWidth() + "\n" + "MeasureHeight" + getMeasuredHeight());
        float x = (getWidth() - getHeight() / 2) / 2;
        float y = getHeight() / 4;
        oval = new RectF(x, y,
                getWidth() - x, getHeight() - y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (endArc < 180) {
            endArc++;
            startArc = 0;
        } else if (endArc >= 180 && endArc < 360 && startArc < 360) {
            startArc++;
            endArc++;
        } else if (endArc >= 360 && startArc < 360) {
            endArc = 360;
            startArc++;
        } else if (startArc >= 360) {
            startArc = 0;
            endArc = 0;
        }
        canvas.drawArc(oval, startArc, endArc - startArc, false, mPaint);
        invalidate();
    }
}