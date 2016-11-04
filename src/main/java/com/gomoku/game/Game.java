package com.gomoku.game;

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

    public String getId() {
        return id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date getCreatedDate() {
        return createdDate;
    }
}
