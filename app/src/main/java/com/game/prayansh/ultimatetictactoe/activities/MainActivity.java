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

package com.game.prayansh.ultimatetictactoe.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;
import com.game.prayansh.ultimatetictactoe.ui.Theme;
import com.game.prayansh.ultimatetictactoe.ui.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Prayansh on 16-07-02.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bg)
    View background;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCross)
    ImageView ivCross;
    @BindView(R.id.ivCircle)
    ImageView ivCircle;
    @BindView(R.id.bPlay)
    Button newGame;
    @BindView(R.id.bSettings)
    Button settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setupTheme();
        //TODO Add Recurring Animation for spinning Cross Icon
    }

    private void setupTheme() {
        Theme mTheme = ThemeManager.getPrimary(getApplicationContext());

        // Setting up views
        background.setBackgroundResource(mTheme.getBackground());
        tvTitle.setTextColor(mTheme.getTextColor());
        ivCross.setImageResource(mTheme.getCross());
        ivCross.setColorFilter(mTheme.getColorCross());
        ivCircle.setImageResource(mTheme.getCircle());
        ivCircle.setColorFilter(mTheme.getColorCircle());
        newGame.setTextColor(mTheme.getColor());
        settings.setTextColor(mTheme.getColor());
    }

    @OnClick(R.id.bPlay)
    public void newGame() {
        GameUI.getInstance().newGame();
        //TODO Create Intent for GameActivity
    }

    @OnClick(R.id.bSettings)
    public void openSettings() {
        //TODO Create intent for SettingsActivity
    }
}