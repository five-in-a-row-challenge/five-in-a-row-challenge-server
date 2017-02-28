package com.gomoku.domain.game;

import static com.gomoku.domain.game.GameStatus.IN_PROGRESS;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    private GameStatus gameStatus;

    public Game() {
        gameStatus = IN_PROGRESS;
    }

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

}
