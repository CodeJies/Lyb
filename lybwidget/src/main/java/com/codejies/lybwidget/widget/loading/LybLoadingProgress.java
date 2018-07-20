package com.codejies.lybwidget.widget.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class LybLoadingProgress extends View {
    private int radiu;
    private int arc;
    private int color;
    private Paint mPaint;
    private RectF oval;
    private int startArc = 0;
    private int endArc = 0;
    private int[] recyclerColor;
    private int nowColor=0;
    public LybLoadingProgress(Context context) {
        this(context,null);
    }

    public LybLoadingProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LybLoadingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        recyclerColor=new int[4];
        recyclerColor[1]=Color.BLUE;
        recyclerColor[2]=Color.GREEN;
        recyclerColor[0]=Color.YELLOW;
        recyclerColor[3]=Color.CYAN;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("View", "getWidth:" + getWidth() + "\n" + "getHeight" + getHeight() + "\n" + "MeasureWidth" + getMeasuredWidth() + "\n" + "MeasureHeight" + getMeasuredHeight());
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        Log.e("YView", "---minimumWidth = " + minimumWidth + "");
        Log.e("YView", "---minimumHeight = " + minimumHeight + "");
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
        float x = (width - height / 2) / 2;
        float y = height / 4;
        oval = new RectF(x, y,
                width - x, height - y);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("YViewWidth", "---speSize = " + specSize + "");


        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = getMeasuredWidth();
                Log.e("YViewWidth", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("YViewWidth", "---speMode = EXACTLY");
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e("YViewWidth", "---speMode = UNSPECIFIED");
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("YViewHeight", "---speSize = " + specSize + "");

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) 200 + getPaddingTop() + getPaddingBottom();
                Log.e("YViewHeight", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                Log.e("YViewHeight", "---speSize = EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                Log.e("YViewHeight", "---speSize = UNSPECIFIED");
//        1.基准点是baseline
//        2.ascent：是baseline之上至字符最高处的距离
//        3.descent：是baseline之下至字符最低处的距离
//        4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
//        5.top：是指的是最高字符到baseline的值,即ascent的最大值
//        6.bottom：是指最低字符到baseline的值,即descent的最大值

                break;
        }
        return defaultHeight;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (endArc < 180) {
            endArc+=10;
            startArc = 0;
        } else if (endArc >= 180 && endArc < 360 && startArc < 360) {
            startArc+=8;
            endArc+=8;
        } else if (endArc >= 360 && startArc < 360) {
            endArc = 360;
            startArc+=5;
        } else if (startArc >= 360) {
            startArc = 0;
            endArc = 0;
            nowColor++;
            if(nowColor>3){
                nowColor=0;
            }
        }
        mPaint.setColor(recyclerColor[nowColor]);
        canvas.drawArc(oval, startArc, endArc - startArc, false, mPaint);
        invalidate();
    }
}
