package com.gomoku.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.repository.HistoryRepository;
import com.gomoku.repository.entity.History;

/**
 * Rest controller to handle histories.
 *
 * @author zeldan
 *
 */
@CrossOrigin
@RequestMapping("/api/")
@RestController
public class HistoryController {

    private final HistoryRepository historyRepository;

    public HistoryController(final HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping("histories/{historyId}")
    public History getHistory(@PathVariable final String historyId) {
        return historyRepository.findOne(historyId);
    }

    @GetMapping("games/{gameId}/histories")
    public List<History> getHistories(@PathVariable final String gameId) {
        return historyRepository.findAllByGameId(gameId);
    }
}
