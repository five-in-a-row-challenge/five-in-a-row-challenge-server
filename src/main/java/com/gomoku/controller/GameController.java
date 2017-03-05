package com.gomoku.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.domain.game.GameStatus;
import com.gomoku.repository.GameRepository;
import com.gomoku.repository.PlayerRepository;
import com.gomoku.repository.entity.Game;
import com.gomoku.repository.entity.Player;
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

    private final GameTaskScheduler gameTaskScheduler;

    private final GameRepository gameRepository;

    private final PlayerRepository playerRepository;

    public GameController(final GameTaskScheduler gameTaskScheduler, final GameRepository gameRepository, final PlayerRepository playerRepository) {
        this.gameTaskScheduler = gameTaskScheduler;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @PostMapping
    public String createGame() {
        return gameRepository.save(new Game()).getId();
    }

    @PostMapping("/{gameId}/start")
    public String start(@PathVariable final String gameId) {
        final List<Player> players = playerRepository.findAll();
        if (players.size() >= MINIMUM_PLAYER_NUMBER_TO_START) {
            final Game game = gameRepository.findOne(gameId);
            game.setGameStatus(GameStatus.IN_PROGRESS);
            gameRepository.save(game);
            startNewGame(gameId, players);
            return "IN_PROGRESS";
        } else {
            throw new RuntimeException("Minimum number of players is 2.");
        }
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    private void startNewGame(final String gameId, final List<Player> players) {
        new Thread(() -> {
            gameTaskScheduler.startAndScheduleGames(gameId, players);
        }).start();
    }
}
