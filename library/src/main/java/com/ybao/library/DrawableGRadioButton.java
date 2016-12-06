package com.ybao.library;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

/**
 * Created by Ybao on 2015/10/27 0027.
 */
public class DrawableGRadioButton extends RadioButton {

    public DrawableGRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        leftp = getPaddingLeft();
        topp = getPaddingTop();
        rightp = getPaddingRight();
        bottomp = getPaddingBottom();
        super.setGravity(Gravity.LEFT);
        super.setPadding(0, 0, 0, 0);
    }

    public DrawableGRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableGRadioButton(Context context) {
        this(context, null);
    }

    @Deprecated
    @Override
    public void setGravity(int gravity) {

    }

    int bodyWidth = 0;
    int bodyHeight = 0;

    int leftp;
    int topp;
    int rightp;
    int bottomp;

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        leftp = left;
        topp = top;
        rightp = right;
        bottomp = bottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setPadding(0, 0, 0, 0);
        bodyWidth = 0;
        bodyHeight = 0;
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            float len = getPaint().measureText(getText().toString());
            bodyWidth = (int) len;
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                bodyWidth += drawableLeft.getBounds().width() + getCompoundDrawablePadding();
            }
            Drawable drawableRight = drawables[2];
            if (drawableRight != null) {
                bodyWidth += drawableRight.getBounds().width() + getCompoundDrawablePadding();
            }

            Paint.FontMetrics fm = getPaint().getFontMetrics();
            bodyHeight = (int) Math.ceil(fm.descent - fm.top) + 2;
            Drawable drawableTop = drawables[1];
            if (drawableTop != null) {
                bodyHeight += drawableTop.getBounds().height() + getCompoundDrawablePadding();
            }
            Drawable drawableBottom = drawables[3];
            if (drawableBottom != null) {
                bodyHeight += drawableBottom.getBounds().height() + getCompoundDrawablePadding();
            }
        }

        hmode = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        if (hmode != MeasureSpec.EXACTLY) {
            height = bodyHeight + topp + bottomp;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }

        wmode = MeasureSpec.getMode(widthMeasureSpec);
        int width = 0;
        if (wmode != MeasureSpec.EXACTLY) {
            width = bodyWidth + leftp + rightp;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.setPadding(0, 0, width - bodyWidth, height - bodyHeight);
    }

    int hmode;
    int wmode;

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        canvas.save();
        int x = 0;
        if (wmode != MeasureSpec.EXACTLY) {
            x = leftp;
        } else {
            x = (width - bodyWidth) / 2;
        }
        int y = 0;
        if (hmode != MeasureSpec.EXACTLY) {
            y = topp;
        } else {
            y = (height - bodyHeight) / 2;
        }
        canvas.translate(x, y);
        super.onDraw(canvas);
        canvas.restore();
    }
}
