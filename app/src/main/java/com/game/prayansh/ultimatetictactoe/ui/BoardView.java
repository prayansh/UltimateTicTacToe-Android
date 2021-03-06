/*
 * Copyright 2016 Prayansh Srivastava
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.game.prayansh.ultimatetictactoe.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.models.CellVal;

import java.util.List;

/**
 * Created by Prayansh on 16-06-26.
 */
public class BoardView extends ViewGroup {

    private static final String TAG = "BoardViewLog";

    private static final int DEFAULT_COL_COUNT = 3;
    private static final int MARGIN = 20;
    private static final int IMAGE_PADDING = 30;

    private Paint mGridPaint;
    private int type; // 0 for CellView children, 1 for BoardView Children
    private int childSize;
    private int mColumnCount;
    private int mMaxChildren;
    private int winner;
    private LayoutParams layoutParamsChild;

    private boolean highlight;

    public BoardView(Context context) {
        this(context, null);
    }

    public BoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoardView, 0, defStyleAttr);

        int strokeWidth = a.getDimensionPixelSize(R.styleable.BoardView_separatorWidth, 1);
        int strokeColor = a.getColor(R.styleable.BoardView_separatorColor, Color.TRANSPARENT);
        mColumnCount = a.getInteger(R.styleable.BoardView_numColumns, DEFAULT_COL_COUNT);
        mMaxChildren = mColumnCount * mColumnCount;
        type = a.getInteger(R.styleable.BoardView_type, 0);
        layoutParamsChild = null;
        childSize = 0;
        a.recycle();
        winner = 0;
        highlight = false;

        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(strokeColor);
        mGridPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize, heightSize;

        //Get the width based on the measure specs
        widthSize = getDefaultSize(0, widthMeasureSpec);

        //Get the height based on measure specs
        heightSize = getDefaultSize(0, heightMeasureSpec);

        int majorDimension = Math.min(widthSize, heightSize);
        //Measure all child views
        childSize = majorDimension / mColumnCount;
        int blockSpec = MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.EXACTLY);
        measureChildren(blockSpec - MARGIN, blockSpec - MARGIN);

        //MUST call this to save our own dimensions
        setMeasuredDimension(majorDimension, majorDimension);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row, col, left, top;
        if (layoutParamsChild == null || changed) {
            layoutParamsChild = new LayoutParams(childSize, childSize);
            Log.d(TAG, "Setting LayoutParams : " + childSize);
        }
        for (int i = 0; i < getChildCount(); i++) {
            row = i / mColumnCount;
            col = i % mColumnCount;
            View child = getChildAt(i);
            child.setLayoutParams(layoutParamsChild);

            left = (col * child.getMeasuredWidth()) + MARGIN / 2;
            top = (row * child.getMeasuredHeight()) + MARGIN / 2;

            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Drawable d = null;
        if (winner == 1) {
            d = getResources().getDrawable(ThemeManager.getCross());
        } else if (winner == -1) {
            d = getResources().getDrawable(ThemeManager.getCircle());
        } else if (type == 0) {
            for (int i = 0; i < getMaxChildren(); i++) {
                ((CellView) getChildAt(i)).highlight(highlight);
            }
        }
        if (d != null) { //Draw Winner
            d.setBounds(IMAGE_PADDING, IMAGE_PADDING, getWidth() - IMAGE_PADDING, getHeight() - IMAGE_PADDING);
            d.draw(canvas);
        }

        if (winner == 0) {
            super.dispatchDraw(canvas);
            canvas.drawLine((childSize), (0), (childSize), (3 * childSize), mGridPaint);
            canvas.drawLine((2 * childSize), (0), (2 * childSize), (3 * childSize), mGridPaint);
            canvas.drawLine((0), (childSize), (3 * childSize), (childSize), mGridPaint);
            canvas.drawLine((0), (2 * childSize), (3 * childSize), (2 * childSize), mGridPaint);
        }
    }

    public void addAllViews(List<View> children) {
        for (View v : children) {
            addView(v);
        }
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than " + mMaxChildren + " direct children");
        }

        super.addView(child, width, height);
    }

    /**
     * Set Boarder Paint using #Color.rgb(rr,gg,bb)
     *
     * @param paint
     */
    public void setBorderPaint(int paint) {
        mGridPaint.setColor(paint);
    }

    public void setBorderSize(float size) {
        mGridPaint.setStrokeWidth(size);
    }

    public int getChildSize() {
        return childSize;
    }

    public int getType() {
        return type;
    }

    public int getMaxChildren() {
        return mMaxChildren;
    }

    public void setWinner(CellVal player) {
        winner = player.getVal();
        invalidate();
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void setType(int type) {
        this.type = type;
    }
}
