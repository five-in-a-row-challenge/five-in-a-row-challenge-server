package com.gomoku.controller;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.domain.game.Game;
import com.gomoku.domain.history.History;
import com.gomoku.domain.player.Player;
import com.gomoku.domain.score.Score;
import com.gomoku.repository.GameRepository;
import com.gomoku.repository.HistoryRepository;
import com.gomoku.repository.PlayerRepository;
import com.gomoku.repository.ScoreRepository;
import com.gomoku.service.GameTaskScheduler;

/**
 * Rest controller to handle game actions.
 *
 * @author zeldan
 *
 */
@CrossOrigin
@RequestMapping("/api/games")
@RestController
public class GameController {

    private static final Logger LOG = getLogger(GameController.class);

    private static final int MINIMUM_PLAYER_NUMBER_TO_START = 2;
    private static final int MINUTE_WAIT_BEFORE_START = 1;

    private final GameTaskScheduler gameTaskScheduler;

    private final GameRepository gameRepository;

    private final PlayerRepository playerRepository;

    private final HistoryRepository historyRepository;

    private final ScoreRepository scoreRepository;

    @Autowired
    public GameController(final GameTaskScheduler gameTaskScheduler, final GameRepository gameRepository, final PlayerRepository playerRepository,
            final HistoryRepository historyRepository, final ScoreRepository scoreRepository) {
        this.gameTaskScheduler = gameTaskScheduler;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.historyRepository = historyRepository;
        this.scoreRepository = scoreRepository;
    }

    @RequestMapping(method = POST, value = "/start")
    public String start() {
        final List<Player> players = playerRepository.findAll();
        if (players.size() >= MINIMUM_PLAYER_NUMBER_TO_START) {
            final Game game = gameRepository.save(new Game());
            final String gameId = game.getId();
            startNewGame(gameId, players);
            return gameId;
        } else {
            throw new RuntimeException("Minimum number of players is 2.");
        }
    }

    @RequestMapping(method = GET)
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @RequestMapping(method = GET, value = "/{gameId}/histories")
    public List<History> getHistories(@PathVariable final String gameId) {
        return historyRepository.findAllByGameId(gameId);
    }

    @RequestMapping(method = GET, value = "/{gameId}/scores")
    public Map<String, Integer> getScore(@PathVariable final String gameId) {
        final List<Score> scoresByGameId = scoreRepository.findByGameId(gameId);
        return scoresByGameId.stream().collect(groupingBy(Score::getPlayer, summingInt(Score::getScore)));
    }

    private void startNewGame(final String gameId, final List<Player> players) {
        final Runnable runnable = () -> {
            try {
                MINUTES.sleep(MINUTE_WAIT_BEFORE_START);
            } catch (final InterruptedException e) {
                LOG.warn("An exception is occurred while try to wait before start and schedule games.", e);
            }
            gameTaskScheduler.startAndScheduleGames(gameId, players);
        };
        final Thread thread = new Thread(runnable);
        thread.start();
    }
}
