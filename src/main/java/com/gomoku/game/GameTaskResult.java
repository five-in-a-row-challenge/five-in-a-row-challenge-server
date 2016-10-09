package com.gomoku.game;

import java.util.Optional;

import com.gomoku.board.BoardFieldType;

public class GameTaskResult {

    private final Optional<BoardFieldType> winner;

    public GameTaskResult(final Optional<BoardFieldType> winner) {
        this.winner = winner;
    }

    public Optional<BoardFieldType> getWinner() {
        return winner;
    }
}
