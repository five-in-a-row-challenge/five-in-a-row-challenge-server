package com.gomoku.history.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.history.History;
import com.gomoku.history.repository.HistoryRepository;

/**
 * Rest controller to handle histories.
 *
 * @author zeldan
 *
 */
@RequestMapping("/api/histories")
@RestController
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;

    @RequestMapping(method = GET, value = "/{historyId}")
    public History getHistory(@PathVariable final String historyId) {
        return historyRepository.findOne(historyId);
    }

}
