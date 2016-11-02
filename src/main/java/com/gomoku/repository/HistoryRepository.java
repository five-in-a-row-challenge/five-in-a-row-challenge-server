package com.gomoku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gomoku.history.History;

public interface HistoryRepository extends MongoRepository<History, String> {

}
