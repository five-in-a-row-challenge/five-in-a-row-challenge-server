package com.gomoku.repository.entity;

/**
 * Immutable class to store Score.
 *
 * @author zeldan
 *
 */
public class Score {

    private final int round;

    private final int gameNumber;

    private final String playerId;

    private final int score;

    public Score(final int round, final int gameNumber, final String playerId, final int score) {
        this.round = round;
        this.gameNumber = gameNumber;
        this.playerId = playerId;
        this.score = score;
    }

    public int getRound() {
        return round;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public String getPlayer() {
        return playerId;
    }

    public int getScore() {
        return score;
    }

}
