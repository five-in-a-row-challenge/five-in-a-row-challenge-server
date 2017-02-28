package com.gomoku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gomoku.repository.entity.Game;

/**
 * History repository to manage game entities.
 *
 * @author zeldan
 */
public interface GameRepository extends MongoRepository<Game, String> {

}
