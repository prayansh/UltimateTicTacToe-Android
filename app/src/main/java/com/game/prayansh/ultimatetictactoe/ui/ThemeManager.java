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
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.game.prayansh.ultimatetictactoe.R;

/**
 * Created by Prayansh on 16-07-02.
 */
public class ThemeManager {
    private static Theme theme;

    public static void setMarvel(Context context) {
        int seed = (int) (Math.random() * 10);
        theme = new Theme(0,
                R.drawable.minimal_background,
                (seed > 5) ? R.drawable.cap : R.drawable.iron_man,
                (seed <= 5) ? R.drawable.cap : R.drawable.iron_man,
                ContextCompat.getColor(context, R.color.mt_black),
                R.drawable.blank_white,
                R.drawable.blank_white_highlight,
                Color.rgb(255, 255, 255),
                R.drawable.btn_light);
    }

    public static void setMinimal(Context context) {
        theme = new Theme(0,
                R.drawable.minimal_background,
                R.drawable.cross_minimal,
                R.drawable.circle_minimal,
                ContextCompat.getColor(context, R.color.mt_black),
                R.drawable.blank_white,
                R.drawable.blank_white_highlight,
                Color.rgb(255, 255, 255),
                R.drawable.btn_light);
    }

    public static void setDC(Context context) {
        int seed = (int) (Math.random() * 10);
        theme = new Theme(0,
                R.drawable.black_background,
                (seed > 5) ? R.drawable.batman : R.drawable.superman,
                (seed <= 5) ? R.drawable.batman : R.drawable.superman,
                ContextCompat.getColor(context, R.color.white),
                R.drawable.blank_black,
                R.drawable.blank_black_highlight,
                Color.rgb(255, 255, 255),
                R.drawable.btn_dark);
    }

    public static Theme getTheme() {
        return theme;
    }

    public static int getCross() {
        return theme.getCross();
    }


    public static int getCircle() {
        return theme.getCircle();
    }
}
