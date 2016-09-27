package com.gomoku.repository.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gomoku.model.Player;
import com.gomoku.repository.PlayerRepository;

@Service
public class InMemoryPlayerRepository implements PlayerRepository {

    private static final Map<String, Player> PLAYERS = new HashMap<>();

    @Override
    public void create(final Player player) {
        PLAYERS.put(player.getUserName(), player);
    }

    @Override
    public Player get(final String username) {
        return PLAYERS.get(username);
    }

}
