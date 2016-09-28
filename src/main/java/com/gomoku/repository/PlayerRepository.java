package com.gomoku.repository;

import java.util.List;

import com.gomoku.board.Player;

/**
 * Interface for player repository, it manages the players like create a new one, or retrieve all players.
 *
 * @author zeldan
 *
 */
public interface PlayerRepository {

    void save(final Player player);

    Player find(final String username);

    List<Player> findAll();

}
