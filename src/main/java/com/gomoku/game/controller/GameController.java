package com.gomoku.game.controller;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.game.Game;
import com.gomoku.game.repository.GameRepository;
import com.gomoku.game.task.GameTaskScheduler;
import com.gomoku.history.History;
import com.gomoku.history.repository.HistoryRepository;
import com.gomoku.player.Player;
import com.gomoku.player.repository.PlayerRepository;

/**
 * Rest controller to handle game actions.
 *
 * @author zeldan
 *
 */
@RequestMapping("/api/games")
@RestController
public class GameController {

    private static final int MINIMUM_PLAYER_NUMBER_TO_START = 2;
    private static final int MINUTE_WAIT_BEFORE_START = 1;

    private static final Logger LOG = getLogger(GameController.class);

    @Autowired
    private GameTaskScheduler gameTaskScheduler;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private HistoryRepository historyRepository;

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
