package com.gomoku.repository;

import com.gomoku.model.Player;

public interface PlayerRepository {

    void create(final Player player);

    Player get(final String username);

}
