package com.gomoku.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gomoku.board.Player;
import com.gomoku.repository.PlayerRepository;

/**
 * In-memory player repository, it stores every players in a {@link Map}.
 *
 * @author zeldan
 *
 */
@Service
public class InMemoryPlayerRepository implements PlayerRepository {

    private static final Map<String, Player> PLAYERS = new HashMap<>();

    @Override
    public void save(final Player player) {
        PLAYERS.put(player.getUserName(), player);
    }

    @Override
    public Player find(final String username) {
        return PLAYERS.get(username);
    }

    @Override
    public List<Player> findAll() {
        return (List<Player>) PLAYERS.values();
    }

}
