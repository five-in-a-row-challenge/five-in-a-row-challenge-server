package com.gomoku.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gomoku.history.History;
import com.gomoku.repository.HistoryRepository;

@Repository
public class InMemoryHistoryRepository implements HistoryRepository {

    private static final Map<Long, History> HISTORIES = new HashMap<>();
    private static Long historyId = 0L;

    @Override
    public Long save(final History history) {
        HISTORIES.put(++historyId, history);
        return historyId;
    }

    @Override
    public List<History> findAll() {
        return new ArrayList<History>(HISTORIES.values());
    }

    @Override
    public History find(final long id) {
        return HISTORIES.get(id);
    }

    @Override
    public void deleteAll() {
        HISTORIES.clear();
    }
}
