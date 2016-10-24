package com.gomoku.repository;

import java.util.List;

import com.gomoku.history.History;

public interface HistoryRepository {

    Long save(final History history);

    List<History> findAll();

    History find(long id);

    void deleteAll();
}
