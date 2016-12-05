package com.gomoku.board;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("board")
public class BoardProperties {

    private int width;

    private int height;

    private int limitToWin;

    public BoardProperties() {

    }

    public BoardProperties(final int width, final int height, final int limitToWin) {
        this.width = width;
        this.height = height;
        this.limitToWin = limitToWin;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public int getLimitToWin() {
        return limitToWin;
    }

    public void setLimitToWin(final int limitToWin) {
        this.limitToWin = limitToWin;
    }

}
