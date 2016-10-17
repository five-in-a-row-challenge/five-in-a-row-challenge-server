package com.gomoku.history;

public class HistoryStep {

    private final int numberOfStep;

    private final String board;

    public HistoryStep(final int numberOfStep, final String board) {
        this.numberOfStep = numberOfStep;
        this.board = board;
    }

    public int getNumberOfStep() {
        return numberOfStep;
    }

    public String getBoard() {
        return board;
    }

}
