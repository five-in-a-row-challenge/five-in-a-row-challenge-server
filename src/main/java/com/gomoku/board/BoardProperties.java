package com.gomoku.board;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("board")
public class BoardProperties {

    private int width;

    private int height;

    private int limitToWin;

    public BoardProperties() {

    }

    public BoardProperties(int width, int height, int limitToWin) {
        this.width = width;
        this.height = height;
        this.limitToWin = limitToWin;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLimitToWin() {
        return limitToWin;
    }

    public void setLimitToWin(int limitToWin) {
        this.limitToWin = limitToWin;
    }

}
