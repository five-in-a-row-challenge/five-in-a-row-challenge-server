package com.gomoku.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

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

    @Autowired
    private GameTaskScheduler gameTaskScheduler;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value = "/start", method = GET)
    public void start() {
        final List<Player> players = playerRepository.findAll();
        if (players.size() >= MINIMUM_PLAYER_NUMBER_TO_START) {
            gameTaskScheduler.startAndScheduleGames(players);
        }
    }

}
