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
import android.util.AttributeSet;
import android.widget.ImageView;

import com.game.prayansh.ultimatetictactoe.models.CellVal;

/**
 * Created by Prayansh on 16-06-26.
 */
public class CellView extends ImageView {

    private int mResource;
    private int block, cell;
    private int blankResource, highlightResource;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.FIT_CENTER);
        cell = block = -1;
        int padding = 10;
        setPadding(padding, padding, padding, padding);
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getCell() {
        return cell;
    }

    public int getBlock() {
        return block;
    }

    private void setCross() {
        mResource = ThemeManager.getCross();
    }

    private void setCircle() {
        mResource = ThemeManager.getCircle();
    }

    private void setEmpty() {
        mResource = blankResource;
    }

    public void highlight(boolean highlight) {
        if (mResource == highlightResource || mResource == blankResource) {
            mResource = (highlight) ? highlightResource : blankResource;
            redraw();
        }
    }

    public void mark(CellVal player) {
        switch (player) {
            case X:
                setCross();
                break;
            case O:
                setCircle();
                break;
            case B:
                setEmpty();
                break;
        }
        redraw();
    }

    private void redraw() {
        setImageResource(mResource);
    }

    public void setBlankResource(int blankResource) {
        this.blankResource = blankResource;
    }

    public void setHighlightResource(int highlightResource) {
        this.highlightResource = highlightResource;
    }
}
