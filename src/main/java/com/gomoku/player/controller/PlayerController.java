package com.gomoku.player.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.player.Player;
import com.gomoku.player.repository.PlayerRepository;

/**
 * Rest controller to handle players.
 *
 * @author zeldan
 *
 */
@RequestMapping("/api/players")
@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(method = GET)
    public List<Player> players() {
        return playerRepository.findAll();
    }

    @RequestMapping(method = POST)
    public void storePlayer(@RequestBody final Player player) {
        playerRepository.save(player);
    }

    @RequestMapping(value = "/{username}", method = GET)
    public Player getPlayer(@PathVariable final String username) {
        return playerRepository.findOne(username);
    }
}
