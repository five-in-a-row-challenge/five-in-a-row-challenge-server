package com.gomoku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gomoku.game.Game;

public interface GameRepository extends MongoRepository<Game, String> {

}
