package com.gomoku.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gomoku.repository.entity.History;

/**
 * History repository to manage history entities.
 *
 * @author zeldan
 */
public interface HistoryRepository extends MongoRepository<History, String> {

    List<History> findAllByGameId(final String gameId);
}
