package com.gomoku.game;

import java.util.Map;

import com.gomoku.board.Board;
import com.gomoku.board.BoardFieldType;
import com.gomoku.player.Player;
import com.gomoku.player.PlayerUriBuilder;

public class GameState {

    private final Board board;

    private final PlayerUriBuilder playerUriBuilder;

    private final Map<BoardFieldType, Player> players;

    public GameState(final Map<BoardFieldType, Player> players, final Board board) {
        this.players = players;
        this.board = board;
        this.playerUriBuilder = new PlayerUriBuilder(board.getWidth(), board.getHeight());
    }

    public Board getBoard() {
        return board;
    }

    public Map<BoardFieldType, Player> getPlayers() {
        return players;
    }

    public PlayerUriBuilder getPlayerUriBuilder() {
        return playerUriBuilder;
    }
}
