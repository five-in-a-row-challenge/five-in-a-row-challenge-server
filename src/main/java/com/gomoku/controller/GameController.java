package com.gomoku.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.game.GameTaskScheduler;

@RequestMapping("game")
@RestController
public class GameController {

    @Autowired
    private GameTaskScheduler gameTaskScheduler;

    @RequestMapping(value = "/start", method = GET)
    public void start() {
        gameTaskScheduler.startAndScheduleGames();
    }

}
