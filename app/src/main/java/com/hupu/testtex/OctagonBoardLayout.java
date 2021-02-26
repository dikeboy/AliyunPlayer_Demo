package com.hupu.testtex;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class OctagonBoardLayout extends View {
    int dividePace = 10;
    public int backColor;
    public int divideColor = Color.BLACK;
    public int divideWidth = 4;

    public OctagonBoardLayout(Context context) {
        this(context, null);
    }

    public OctagonBoardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OctagonBoardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, Paint.FILTER_BITMAP_FLAG));

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Paint leftPaint = new Paint();
        leftPaint.setStyle(Paint.Style.STROKE);
        leftPaint.setStrokeWidth(divideWidth);
        leftPaint.setAntiAlias(true);
        leftPaint.setColor(divideColor);

        Path lPath = new Path();
        lPath.moveTo(divideWidth, dividePace);
        lPath.lineTo(dividePace+divideWidth, divideWidth);
        lPath.lineTo(width-dividePace-divideWidth,divideWidth);
        lPath.lineTo(width-divideWidth,dividePace);
        lPath.lineTo(width-divideWidth,height-dividePace);
        lPath.lineTo(width-dividePace,height-divideWidth);
        lPath.lineTo(dividePace,height-divideWidth);
        lPath.lineTo(divideWidth,height-dividePace);
        lPath.close();

        canvas.drawPath(lPath, leftPaint);
    }
}
