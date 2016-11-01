package com.gomoku.history;

import java.util.List;
import java.util.Optional;

import com.gomoku.player.Player;

public class History {

    private static Long historyId = 1L;

    private final long id;

    private final int round;

    private final int gameNumber;

    private final Player firstPlayer;

    private final Player secondPlayer;

    private final Optional<Player> winner;

    private final List<HistoryStep> steps;

    public History(final int round, final int gameNumber, final Player firstPlayer, final Player secondPlayer,
            final Optional<Player> winner, final List<HistoryStep> steps) {
        id = historyId++;
        this.round = round;
        this.gameNumber = gameNumber;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
        this.steps = steps;
    }

    public long getId() {
        return id;
    }

    public int getRound() {
        return round;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Optional<Player> getWinner() {
        return winner;
    }

    public List<HistoryStep> getSteps() {
        return steps;
    }

}
