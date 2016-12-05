package com.gomoku.score;

/**
 * Type of the score and it's amount.
 *
 * @author zeldan
 *
 */
public enum ScoreType {

    VICTORY(5), DRAW(2);

    final int score;

    private ScoreType(final int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}
