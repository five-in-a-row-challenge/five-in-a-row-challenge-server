package com.gomoku.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gomoku.domain.score.Score;

/**
 * History repository to manage score entities.
 *
 * @author zeldan
 */
public interface ScoreRepository extends MongoRepository<Score, String> {

    List<Score> findByGameId(final String gameId);

}
