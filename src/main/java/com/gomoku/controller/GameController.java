package com.gomoku.controller;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.domain.game.GameStatus;
import com.gomoku.repository.GameRepository;
import com.gomoku.repository.entity.Game;
import com.gomoku.repository.entity.History;
import com.gomoku.repository.entity.Score;
import com.gomoku.service.GameTaskScheduler;
import com.gomoku.service.exception.MinimumPlayerViolationException;

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

    private static final int MINIMUM_PLAYER_NUMBER_TO_START = 2;

    private final GameTaskScheduler gameTaskScheduler;

    private final GameRepository gameRepository;

    public GameController(final GameTaskScheduler gameTaskScheduler, final GameRepository gameRepository) {
        this.gameTaskScheduler = gameTaskScheduler;
        this.gameRepository = gameRepository;
    }

    @PostMapping
    public Game createGame() {
        return gameRepository.save(new Game());
    }

    @PostMapping("/{gameId}/start")
    public void start(@PathVariable final String gameId) {
        final Game game = gameRepository.findOne(gameId);
        final List<String> players = game.getPlayers();
        if (players != null && players.size() >= MINIMUM_PLAYER_NUMBER_TO_START) {
            game.setGameStatus(GameStatus.IN_PROGRESS);
            gameRepository.save(game);
            startNewGame(gameId);
        } else {
            throw new MinimumPlayerViolationException();
        }
    }

    @PostMapping("/{gameId}/players")
    public void setPlayers(@PathVariable final String gameId, @RequestBody final List<String> playerIds) {
        final Game game = gameRepository.findOne(gameId);
        game.setPlayers(playerIds);
        gameRepository.save(game);
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable final String gameId) {
        return gameRepository.findOne(gameId);
    }

    @GetMapping("/{gameId}/scores")
    public Map<String, Integer> getScore(@PathVariable final String gameId) {
        final List<Score> scoresByGameId = gameRepository.findOne(gameId).getScores();
        return scoresByGameId.stream().collect(groupingBy(Score::getPlayer, summingInt(Score::getScore)));
    }

    @GetMapping("/{gameId}/histories")
    public List<History> getHistories(@PathVariable final String gameId) {
        return gameRepository.findOne(gameId).getHistories();
    }

    private void startNewGame(final String gameId) {
        new Thread(() -> {
            gameTaskScheduler.startAndScheduleGames(gameId);
        }).start();
    }
}
