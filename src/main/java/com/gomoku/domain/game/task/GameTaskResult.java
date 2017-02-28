package com.gomoku.domain.game.task;

import static java.util.Collections.unmodifiableList;

import java.util.List;
import java.util.Optional;

import com.gomoku.domain.history.HistoryStep;
import com.gomoku.repository.entity.Player;

/**
 * Result of game task. It contains the winner and the all the steps.
 *
 * @author zeldan
 *
 */
public class GameTaskResult {

    private final Optional<Player> winner;

    private final List<HistoryStep> steps;

    public GameTaskResult(final Optional<Player> winner, final List<HistoryStep> steps) {
        this.winner = winner;
        this.steps = steps;
    }

    public Optional<Player> getWinner() {
        return winner;
    }

    public List<HistoryStep> getSteps() {
        return unmodifiableList(steps);
    }
}
