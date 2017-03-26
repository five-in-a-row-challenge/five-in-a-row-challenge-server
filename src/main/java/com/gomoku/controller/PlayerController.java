package com.gomoku.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gomoku.controller.exception.PlayerNotFoundException;
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

    private final PlayerRepository playerRepository;

    public PlayerController(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping
    @ResponseStatus(value = CREATED)
    public void storePlayer(@RequestBody final Player player) {
        playerRepository.save(player);
    }

    @GetMapping
    public List<Player> players() {
        return playerRepository.findAll();
    }

    @GetMapping("/{username}")
    public Player getPlayer(@PathVariable final String username) {
        return getPlayerByUsername(username);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(value = NO_CONTENT)
    public void deletePlayer(@PathVariable final String username) {
        final Player player = getPlayerByUsername(username);
        playerRepository.delete(player);
    }

    public Player getPlayerByUsername(final String username) {
        final Player player = playerRepository.findOne(username);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return player;
    }
}
