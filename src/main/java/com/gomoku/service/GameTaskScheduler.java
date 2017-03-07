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
import org.springframework.stereotype.Service;

import com.gomoku.config.properties.GameProperties;
import com.gomoku.domain.game.task.GameTaskResult;
import com.gomoku.domain.score.ScoreType;
import com.gomoku.repository.GameRepository;
import com.gomoku.repository.entity.Game;
import com.gomoku.repository.entity.History;
import com.gomoku.repository.entity.Player;
import com.gomoku.repository.entity.Score;

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

    private final GameTaskService gameTaskService;

    private final ScheduledExecutorService scheduler;

    private final GameRepository gameRepository;

    private final GameProperties gameProperties;

    public GameTaskScheduler(final GameTaskService gameTaskService, final ScheduledExecutorService scheduler,
            final GameRepository gameRepository, final GameProperties gameProperties) {
        this.gameTaskService = gameTaskService;
        this.scheduler = scheduler;
        this.gameRepository = gameRepository;
        this.gameProperties = gameProperties;
    }

    public void startAndScheduleGames(final String gameId, final List<Player> players) {

        final ScheduledFuture<?> countdown = scheduler.schedule(() -> LOG.info("Out of time!"), gameProperties.getLengthOfTheGameInMinutes(), MINUTES);
        int round = 1;
        final Game game = gameRepository.findOne(gameId);
        while (!countdown.isDone()) {
            try {
                startRound(game, round++, players);
                sleep(gameProperties.getLengthOfOneRoundInMinutes() * ONE_MINUTE_IN_MILLISEC);
            } catch (final InterruptedException e) {
                LOG.warn("The game is interrupted.");
            }
        }
        game.setGameStatus(FINISHED);
        gameRepository.save(game);
    }

    private void startRound(final Game game, final int round, final List<Player> players) {
        LOG.info("The round '{}' is started.", round);
        final AtomicInteger gameNr = new AtomicInteger(1);
        players.forEach(playerOne -> {
            players.forEach(playerTwo -> {
                if (!playerOne.equals(playerTwo)) {
                    LOG.info("--- Player '{}' versus Player '{}'", playerOne.getUserName(), playerTwo.getUserName());
                    final GameTaskResult gameTaskResult = gameTaskService.matchAgainstEachOther(playerOne, playerTwo);
                    final Optional<Player> winner = gameTaskResult.getWinner();
                    game.addHistory(new History(round, gameNr.getAndIncrement(), playerOne.getUserName(), playerTwo.getUserName(), getWinnerPlayerId(winner),
                            gameTaskResult.getSteps()));
                    if (winner.isPresent()) {
                        game.addScore(new Score(round, gameNr.get(), winner.get().getUserName(), ScoreType.VICTORY.getScore()));
                        LOG.info("------ The winner is: " + winner.get().getUserName());
                    } else {
                        final int scoreOfDraw = ScoreType.DRAW.getScore();
                        game.addScore(new Score(round, gameNr.get(), playerOne.getUserName(), scoreOfDraw));
                        game.addScore(new Score(round, gameNr.get(), playerTwo.getUserName(), scoreOfDraw));
                        LOG.info("------ The game is draw.");
                    }
                    gameRepository.save(game);
                }
            });
        });
    }

    private Optional<String> getWinnerPlayerId(final Optional<Player> player) {
        if (player.isPresent()) {
            return Optional.of(player.get().getUserName());
        }
        return Optional.empty();
    }

}
