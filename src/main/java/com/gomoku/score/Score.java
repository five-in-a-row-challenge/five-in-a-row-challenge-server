package com.gomoku.score;

import org.springframework.data.annotation.Id;

/**
 * Score entity
 *
 * @author zeldan
 *
 */
public class Score {
    @Id
    private String id;

    private final String gameId;

    private final int round;

    private final int gameNumber;

    private final String playerId;

    private final int score;

    public Score(final String gameId, final int round, final int gameNumber, final String playerId, final int score) {
        super();
        this.gameId = gameId;
        this.round = round;
        this.gameNumber = gameNumber;
        this.playerId = playerId;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
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
