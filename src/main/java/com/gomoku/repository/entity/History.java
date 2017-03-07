package com.gomoku.repository.entity;

import static java.util.Collections.unmodifiableList;

import java.util.List;
import java.util.Optional;

import com.gomoku.domain.history.HistoryStep;

/**
 * Immutable class to store history.
 *
 * @author zeldan
 */
public class History {

    private final int round;

    private final int gameNumber;

    private final String firstPlayerId;

    private final String secondPlayerId;

    private final Optional<String> winner;

    private final List<HistoryStep> steps;

    public History(final int round, final int gameNumber, final String firstPlayerId, final String secondPlayerId, final Optional<String> winner,
            final List<HistoryStep> steps) {
        this.round = round;
        this.gameNumber = gameNumber;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.winner = winner;
        this.steps = steps;
    }

    public int getRound() {
        return round;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public String getFirstPlayerId() {
        return firstPlayerId;
    }

    public String getSecondPlayerId() {
        return secondPlayerId;
    }

    public Optional<String> getWinner() {
        return winner;
    }

    public List<HistoryStep> getSteps() {
        return unmodifiableList(steps);
    }

}
