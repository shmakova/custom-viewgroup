package ru.yandex.yamblz.ui.views;

/**
 * Created by shmakova on 20.07.16.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomLayout extends ViewGroup {

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);

        int width = getPaddingLeft() + getPaddingRight();
        int height = getPaddingTop() + getPaddingBottom();

        final int count = getChildCount();
        View matchParentChild = null;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() != GONE) {
                final LayoutParams lp = child.getLayoutParams();
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                if (lp.width == LayoutParams.MATCH_PARENT) {
                    matchParentChild = child;
                } else {
                    width += child.getWidth();
                }

                height = Math.max(height, child.getHeight());
            }
        }

        if (matchParentChild != null) {
            int childWidthMeasureSpec =
                    MeasureSpec.makeMeasureSpec(Math.max(maxWidth - width, 0),
                            MeasureSpec.EXACTLY);
            int childHeightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(matchParentChild.getMeasuredHeight(),
                            MeasureSpec.EXACTLY);
            matchParentChild.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int leftPos = getPaddingLeft();
        final int parentTop = getPaddingTop();
        int left, right, top, bottom;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() != GONE) {
                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                left = leftPos;
                right = leftPos + width;
                leftPos = right;

                top = parentTop;
                bottom = height;

                child.layout(left, top, right, bottom);
            }
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

}