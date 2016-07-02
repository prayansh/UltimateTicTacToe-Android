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
    public static Theme getPrimary(Context context) {
        int white = Color.rgb(255, 255, 255);
        return new Theme(white,
                R.drawable.grad_blue,
                R.drawable.cross, white,
                R.drawable.circle, white,
                ContextCompat.getColor(context, R.color.belize_hole),
                0,
                white);
    }

    public static Theme getMinimal() {
        return null;
    }

    public static Theme getClassic() {
        return null;
    }
}