package com.gomoku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gomoku.domain.player.Player;

/**
 * Interface for player repository, it manages the players like create a new one, or retrieve all players.
 *
 * @author zeldan
 *
 */
public interface PlayerRepository extends MongoRepository<Player, String> {

}
