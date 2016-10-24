package com.gomoku.game;

import java.util.Map;

import com.gomoku.board.Board;
import com.gomoku.board.BoardFieldType;
import com.gomoku.player.Player;

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
