package com.gomoku.repository.entity;

import static com.gomoku.domain.game.GameStatus.CREATED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gomoku.domain.game.GameStatus;

/**
 *
 * The game entity, it contains auditing information.
 *
 * @author zeldan
 *
 */
public class Game {

    @Id
    private String id;

    @CreatedDate
    private Date createdDate;

    private final List<History> histories = new ArrayList<>();

    private final List<Score> scores = new ArrayList<>();

    private GameStatus gameStatus = CREATED;

    public String getId() {
        return id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setGameStatus(final GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @JsonIgnore
    public List<History> getHistories() {
        return histories;
    }

    @JsonIgnore
    public List<Score> getScores() {
        return scores;
    }

    public void addHistory(final History history) {
        histories.add(history);
    }

    public void addScore(final Score score) {
        scores.add(score);
    }

}
