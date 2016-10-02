package com.gomoku.player;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

import com.gomoku.board.BoardFieldType;

/**
 * URI Builder to build player URI.
 *
 * @author zeldan
 *
 */
public class PlayerUriBuilder {

    private final int boardWidth;

    private final int boardHeight;

    public PlayerUriBuilder(final int boardWidth, final int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public URI buildUri(final String baseUrl, final String table, final BoardFieldType boardFieldType) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("width", boardWidth)
                .queryParam("height", boardHeight)
                .queryParam("table", table)
                .queryParam("player", boardFieldType);
        return builder.build().encode().toUri();
    }
}
