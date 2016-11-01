package com.gomoku.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.history.History;
import com.gomoku.repository.HistoryRepository;

/**
 * Rest controller to handle histories.
 *
 * @author zeldan
 *
 */
@RequestMapping("/api/history")
@RestController
public class HistoryController {

    @Autowired
    private HistoryRepository historeRepository;

    @RequestMapping(method = GET)
    public List<History> getAll() {
        return historeRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public History getPlayer(@PathVariable final Long id) {
        return historeRepository.find(id);
    }
}
