package com.gomoku.controller;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.repository.ScoreRepository;
import com.gomoku.repository.entity.Score;

/**
 * Rest controller to handle scores.
 *
 * @author zeldan
 *
 */
@CrossOrigin
@RequestMapping("/api/")
@RestController
public class ScoreController {

    private final ScoreRepository scoreRepository;

    public ScoreController(final ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @GetMapping("games/{gameId}/scores")
    public Map<String, Integer> getScore(@PathVariable final String gameId) {
        final List<Score> scoresByGameId = scoreRepository.findByGameId(gameId);
        return scoresByGameId.stream().collect(groupingBy(Score::getPlayer, summingInt(Score::getScore)));
    }
}
