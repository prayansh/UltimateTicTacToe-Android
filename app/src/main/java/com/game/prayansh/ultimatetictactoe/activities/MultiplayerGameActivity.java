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
import android.view.View;
import android.widget.Toast;

import com.game.prayansh.ultimatetictactoe.exceptions.BoardSolvedException;
import com.game.prayansh.ultimatetictactoe.exceptions.GameOverException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidBlockException;
import com.game.prayansh.ultimatetictactoe.exceptions.InvalidMoveException;
import com.game.prayansh.ultimatetictactoe.models.CellVal;
import com.game.prayansh.ultimatetictactoe.models.Game;
import com.game.prayansh.ultimatetictactoe.models.Move;
import com.game.prayansh.ultimatetictactoe.ui.CellView;
import com.game.prayansh.ultimatetictactoe.ui.GameUI;

/**
 * Created by Prayansh on 16-07-02.
 */
public class MultiPlayerGameActivity extends GameActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void clickView(View v) {
        CellView cv = (CellView) v;
        Game game = GameUI.getInstance().getGame();
        CellVal player = game.getPlayer();

        //TODO create free hit message

        try {
            Move m = game.playMove(cv.getBlock(), cv.getCell());
            cv.mark(player);
        } catch (InvalidMoveException e) {
            Toast.makeText(getApplicationContext(), "You can't play there", Toast.LENGTH_SHORT).show();
        } catch (GameOverException e) {
            cv.mark(player);
            buildGameOverDialog(e.getWinner());
        } catch (InvalidBlockException e) {
            Toast.makeText(getApplicationContext(),
                    "You have to play on " + e.getContextIndex() + " board", Toast.LENGTH_SHORT).show();
        } catch (BoardSolvedException e) {
            Toast.makeText(getApplicationContext(), "You can't play on solved board", Toast.LENGTH_SHORT).show();
        } finally {
            checkWins();
            updatePlayerInfo();
            highlightContextBoards();
        }

    }
}
