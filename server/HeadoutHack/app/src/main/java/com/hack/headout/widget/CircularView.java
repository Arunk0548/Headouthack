package com.hack.headout.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.hack.headout.R;

/**
 * Created by Arun Kumar on 2/6/2016.
 */
public class CircularView extends View {

    private static final String COLOR_HEX = "#E74300";
    private final Paint drawPaint;
    private float size;

    public CircularView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        final Resources res = getResources();

        drawPaint = new Paint();
        int defaultSelectedColor = res.getColor(R.color.warning_ellipse_color);
        //Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinePageIndicator);
        drawPaint.setColor(a.getColor(R.styleable.LinePageIndicator_selectedColor, defaultSelectedColor));
        drawPaint.setAntiAlias(true);
        setOnMeasureCallback();
        a.recycle();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(size, size, size, drawPaint);
    }

    private void setOnMeasureCallback() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(this);
                size = getMeasuredWidth() / 2;
            }
        });
    }

    // sets the color of the bar (#FF00FF00 - Green by default)
    public void setCircularColor(int color) {
        this.drawPaint.setColor(color);
        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            //noinspection deprecation
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }
}
