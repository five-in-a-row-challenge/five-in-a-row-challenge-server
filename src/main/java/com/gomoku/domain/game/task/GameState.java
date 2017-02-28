package com.gomoku.domain.game.task;

import java.util.Map;

import com.gomoku.domain.board.Board;
import com.gomoku.domain.board.BoardFieldType;
import com.gomoku.domain.player.Player;

/**
 * Actual state of the game.
 *
 * @author zeldan
 *
 */
public class GameState {

    private final Board board;

    private final Map<BoardFieldType, Player> players;

    public GameState(final Map<BoardFieldType, Player> players, final Board board) {
        this.players = players;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public Map<BoardFieldType, Player> getPlayers() {
        return players;
    }

}
