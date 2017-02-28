package com.gomoku.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/histories")
@RestController
public class HistoryController {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryController(final HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @RequestMapping(method = GET, value = "/{historyId}")
    public History getHistory(@PathVariable final String historyId) {
        return historyRepository.findOne(historyId);
    }

}
