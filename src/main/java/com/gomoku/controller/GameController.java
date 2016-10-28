package com.gomoku.controller;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.game.GameTaskScheduler;
import com.gomoku.player.Player;
import com.gomoku.repository.PlayerRepository;

/**
 * Rest controller to handle game actions.
 *
 * @author zeldan
 *
 */
@RequestMapping("game")
@RestController
public class GameController {

    private static final int MINIMUM_PLAYER_NUMBER_TO_START = 2;
    private static final int MINUTE_WAIT_BEFORE_START = 10;

    private static final Logger LOG = getLogger(GameController.class);

    @Autowired
    private GameTaskScheduler gameTaskScheduler;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value = "/start", method = GET)
    public void start() {
        final List<Player> players = playerRepository.findAll();
        if (players.size() >= MINIMUM_PLAYER_NUMBER_TO_START) {
            startNewGame(players);
        }
    }

    private void startNewGame(final List<Player> players) {
        final Runnable runnable = () -> {
            try {
                MINUTES.sleep(MINUTE_WAIT_BEFORE_START);
            } catch (final InterruptedException e) {
                LOG.warn("An exception is occurred while try to wait before start and schedule games.", e);
            }
            gameTaskScheduler.startAndScheduleGames(players);
        };
        final Thread thread = new Thread(runnable);
        thread.start();
    }
}
