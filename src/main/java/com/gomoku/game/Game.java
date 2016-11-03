package com.gomoku.game;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

public class Game {

    @Id
    private String id;

    @CreatedDate
    private Date createdDate;

    public String getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
