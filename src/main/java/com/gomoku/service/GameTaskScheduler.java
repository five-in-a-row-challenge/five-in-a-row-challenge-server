package com.gomoku.service;

import static com.gomoku.domain.game.GameStatus.FINISHED;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomoku.config.properties.GameProperties;
import com.gomoku.domain.game.Game;
import com.gomoku.domain.game.task.GameTask;
import com.gomoku.domain.game.task.GameTaskResult;
import com.gomoku.domain.history.History;
import com.gomoku.domain.player.Player;
import com.gomoku.domain.score.Score;
import com.gomoku.domain.score.ScoreType;
import com.gomoku.repository.GameRepository;
import com.gomoku.repository.HistoryRepository;
import com.gomoku.repository.ScoreRepository;

/**
 * Scheduler service to start and schedule games between every players in the given time period.
 *
 * @author zeldan
 *
 */
@Service
public class GameTaskScheduler {

    private static final int ONE_MINUTE_IN_MILLISEC = 60000;

    private static final Logger LOG = getLogger(GameTaskScheduler.class);

    private final GameTask gameTask;

    private final HistoryRepository historyRepository;

    private final ScheduledExecutorService scheduler;

    private final GameRepository gameRepository;

    private final ScoreRepository scoreRepository;

    private final GameProperties gameProperties;

    @Autowired
    public GameTaskScheduler(final GameTask gameTask, final HistoryRepository historyRepository, final ScheduledExecutorService scheduler,
            final GameRepository gameRepository, final ScoreRepository scoreRepository, final GameProperties gameProperties) {
        this.gameTask = gameTask;
        this.historyRepository = historyRepository;
        this.scheduler = scheduler;
        this.gameRepository = gameRepository;
        this.scoreRepository = scoreRepository;
        this.gameProperties = gameProperties;
    }

    public void startAndScheduleGames(final String gameId, final List<Player> players) {

        final ScheduledFuture<?> countdown = scheduler.schedule(() -> LOG.info("Out of time!"), gameProperties.getLengthOfTheGameInMinutes(), MINUTES);
        int round = 1;
        while (!countdown.isDone()) {
            try {
                startRound(gameId, round++, players);
                sleep(gameProperties.getLengthOfOneRoundInMinutes() * ONE_MINUTE_IN_MILLISEC);
            } catch (final InterruptedException e) {
                LOG.warn("The game is interrupted.");
            }
        }
        final Game game = gameRepository.findOne(gameId);
        game.setGameStatus(FINISHED);
        gameRepository.save(game);
    }

    private void startRound(final String gameId, final int round, final List<Player> players) {
        LOG.info("The round '{}' is started.", round);
        final AtomicInteger gameNr = new AtomicInteger(1);
        players.forEach(playerOne -> {
            players.forEach(playerTwo -> {
                if (!playerOne.equals(playerTwo)) {
                    LOG.info("--- Player '{}' versus Player '{}'", playerOne.getUserName(), playerTwo.getUserName());
                    final GameTaskResult gameTaskResult = gameTask.matchAgainstEachOther(playerOne, playerTwo);
                    final Optional<Player> winner = gameTaskResult.getWinner();
                    final History history = new History(gameId, round, gameNr.getAndIncrement(), playerOne, playerTwo, winner, gameTaskResult.getSteps());
                    historyRepository.save(history);
                    if (winner.isPresent()) {
                        scoreRepository.save(new Score(gameId, round, gameNr.get(), winner.get().getUserName(), ScoreType.VICTORY.getScore()));
                        LOG.info("------ The winner is: " + winner.get().getUserName());
                    } else {
                        final int scoreOfDraw = ScoreType.DRAW.getScore();
                        scoreRepository.save(new Score(gameId, round, gameNr.get(), playerOne.getUserName(), scoreOfDraw));
                        scoreRepository.save(new Score(gameId, round, gameNr.get(), playerTwo.getUserName(), scoreOfDraw));
                        LOG.info("------ The game is draw.");
                    }
                    LOG.info("------ The id of history is: " + history.getId());
                }
            });
        });
    }

}
