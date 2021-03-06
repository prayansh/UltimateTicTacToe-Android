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

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.game.prayansh.ultimatetictactoe.R;
import com.game.prayansh.ultimatetictactoe.models.Board;
import com.game.prayansh.ultimatetictactoe.models.CellVal;
import com.game.prayansh.ultimatetictactoe.models.Game;
import com.game.prayansh.ultimatetictactoe.models.Move;
import com.game.prayansh.ultimatetictactoe.models.TTTStack;
import com.game.prayansh.ultimatetictactoe.ui.BoardView;
import com.game.prayansh.ultimatetictactoe.ui.CellView;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;
import com.game.prayansh.ultimatetictactoe.ui.Theme;
import com.game.prayansh.ultimatetictactoe.ui.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Prayansh on 16-07-12.
 */
public abstract class GameActivity extends AppCompatActivity {

    private static final String STATE_STACK = "stack_state";
    @BindView(R.id.bg)
    View background;
    @BindView(R.id.board)
    BoardView gameBoard;
    @BindView(R.id.ivInfo)
    ImageView currentPlayer;
    @BindView(R.id.bRestart)
    Button restart;
    @BindView(R.id.bUndo)
    Button undo;

    protected View.OnClickListener cellTouchListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_activity);
        ButterKnife.bind(this);
        cellTouchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickView(v);
            }
        };
        setupThemeAndViews();
    }

    protected void setupThemeAndViews() {
        Theme theme = ThemeManager.getTheme();
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf");
        // Setup views
        background.setBackgroundResource(theme.getBackground());
        restart.setTextColor(theme.getColor());
        restart.setTypeface(typeFace);
        undo.setTextColor(theme.getColor());
        undo.setTypeface(typeFace);
        restart.setBackgroundResource(theme.getBtn());
        undo.setBackgroundResource(theme.getBtn());

        // Setup Board
        gameBoard.setType(1);
        gameBoard.setBorderPaint(theme.getGridColor());
        gameBoard.setBorderSize(10);
        for (int i = 0; i < gameBoard.getMaxChildren(); i++) {
            BoardView bv = new BoardView(getApplicationContext());
            bv.setBorderPaint(theme.getGridColor());
            bv.setType(0);
            gameBoard.addView(buildEmptyBoard(bv, i, theme));
        }
        updatePlayerInfo();
    }

    private BoardView buildEmptyBoard(BoardView bv, int block, Theme theme) {
        for (int j = 0; j < gameBoard.getMaxChildren(); j++) {
            CellView cv = new CellView(getApplicationContext());
            cv.setBlock(block);
            cv.setCell(j);
            //Set up view resources
            cv.setBlankResource(theme.getBlank());
            cv.setHighlightResource(theme.getHighlight());
            cv.mark(CellVal.B);
            cv.setOnClickListener(cellTouchListener);
            bv.addView(cv);
        }
        return bv;
    }

    protected void highlightContextBoards() {
        Board[] boards = GameUI.getInstance().getGame().getBoards();
        int index = GameUI.getInstance().getGame().getContextBoardIndex();
        for (int i = 0; i < boards.length; i++) {
            if (index == i)
                ((BoardView) gameBoard.getChildAt(i)).setHighlight(true);
            else
                ((BoardView) gameBoard.getChildAt(i)).setHighlight(false);
        }
    }

    protected void checkWins() {
        Game game = GameUI.getInstance().getGame();
        int i = 0;
        for (Board b : game.getBoards()) {
            if (b.solved()) {
                ((BoardView) gameBoard.getChildAt(i)).setWinner(b.winner());
            } else {
                ((BoardView) gameBoard.getChildAt(i)).setWinner(CellVal.B);
            }
            i++;
        }
    }

    protected void updatePlayerInfo() {
        switch (GameUI.getInstance().getGame().getPlayer()) {
            case X:
                currentPlayer.setImageResource(ThemeManager.getCross());
                break;
            case O:
                currentPlayer.setImageResource(ThemeManager.getCircle());
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putParcelable(STATE_STACK, GameUI.getInstance().getGame().getMoves());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TTTStack moveStack = savedInstanceState.getParcelable(STATE_STACK);
        restoreGame(moveStack);
    }

    private void restoreGame(TTTStack moveStack) {
        GameUI.recreate(moveStack);
        int i = 0;
        for (Move m : moveStack) {
            int block = m.getBoardNo();
            int cell = m.getCellNo();
            CellVal player = (i % 2 == 0) ? CellVal.X : CellVal.O;
            ((CellView) ((BoardView) gameBoard.getChildAt(block)).getChildAt(cell)).mark(player);
            i++;
        }
        highlightContextBoards();
        checkWins();
        updatePlayerInfo();
    }

    @OnClick(R.id.bUndo)
    public void undoMove() {
        if (GameUI.getInstance().getGame().isStarted()) {
            Move m = GameUI.getInstance().getGame().undo();
            int block = m.getBoardNo(), cell = m.getCellNo();
            ((CellView) ((BoardView) gameBoard.getChildAt(block)).getChildAt(cell)).mark(CellVal.B);
            if (m.isFreeHit())
                GameUI.getInstance().getGame().setContextBoardIndex(-1);
            else
                GameUI.getInstance().getGame().setContextBoardIndex(block);
            highlightContextBoards();
            checkWins();
            updatePlayerInfo();
        } else {
            GameUI.getInstance().getGame().setContextBoardIndex(-1);
        }
    }

    @OnClick(R.id.bRestart)
    public void restart() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        GameUI.getInstance().newGame();
                        for (int i = 0; i < gameBoard.getMaxChildren(); i++) {
                            for (int j = 0; j < gameBoard.getMaxChildren(); j++) {
                                ((CellView) ((ViewGroup) gameBoard.getChildAt(i)).getChildAt(j))
                                        .mark(CellVal.B);
                            }
                        }
//                        gameBoard.removeAllViews();
//                        setupThemeAndViews();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Restart game?").setPositiveButton("Restart", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    protected void buildGameOverDialog(CellVal winner) {
        int resourceId = (winner == CellVal.X) ? ThemeManager.getCross() : ThemeManager.getCircle();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.game_over_dialog, null);
        dialogBuilder.setView(dialogView);
        //noinspection ResourceType
        ((ImageView) dialogView.findViewById(R.id.winner)).setImageResource(resourceId);
        (dialogView.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newGameIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(newGameIntent);
                finish();
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Return to main menu?").setPositiveButton("Quit", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    protected abstract void clickView(View v);
}
