package com.gomoku.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gomoku.player.Player;
import com.gomoku.repository.PlayerRepository;

/**
 * In-memory player repository, it stores every players in a {@link Map}.
 *
 * @author zeldan
 *
 */
@Repository
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
        return new ArrayList<Player>(PLAYERS.values());
    }

}
