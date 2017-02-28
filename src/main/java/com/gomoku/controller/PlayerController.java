package com.gomoku.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.repository.PlayerRepository;
import com.gomoku.repository.entity.Player;

/**
 * Rest controller to handle players.
 *
 * @author zeldan
 *
 */
@CrossOrigin
@RequestMapping("/api/players")
@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(method = POST)
    public void storePlayer(@RequestBody final Player player) {
        playerRepository.save(player);
    }

    @RequestMapping(method = GET)
    public List<Player> players() {
        return playerRepository.findAll();
    }

    @RequestMapping(method = GET, value = "/{username}")
    public Player getPlayer(@PathVariable final String username) {
        return playerRepository.findOne(username);
    }

    @RequestMapping(method = DELETE, value = "/{username}")
    public void deletePlayer(@PathVariable final String username) {
        playerRepository.delete(username);
    }
}
