package com.gomoku.game;

import java.util.List;
import java.util.Optional;

import com.gomoku.board.BoardFieldType;
import com.gomoku.history.HistoryStep;

public class GameTaskResult {

    private final Optional<BoardFieldType> winner;

    private final List<HistoryStep> steps;

    public GameTaskResult(final Optional<BoardFieldType> winner, final List<HistoryStep> steps) {
        this.winner = winner;
        this.steps = steps;
    }

    public Optional<BoardFieldType> getWinner() {
        return winner;
    }

    public List<HistoryStep> getSteps() {
        return steps;
    }
}
