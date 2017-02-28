package com.gomoku.domain.player;

import static com.gomoku.domain.board.BoardFieldType.PLAYER_X;
import static java.lang.String.valueOf;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;

import com.gomoku.domain.board.Board;
import com.gomoku.domain.player.PlayerUriBuilder;

/**
 * Unit test for {@link PlayerUriBuilder}.
 *
 * @author zeldan
 *
 */
public class PlayerUriBuilderTest {

    private static final int BOARD_WIDTH = 2;
    private static final int BOARD_HEIGHT = 3;

    @Test
    public void shouldBuildProperUriByParameters() {
        // GIVEN
        final Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT, 1);

        // WHEN
        final URI uri = PlayerUriBuilder.buildUri("http://computer:8080", board, PLAYER_X);

        // THEN
        final MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
        assertEquals(parameters.getFirst("width"), valueOf(BOARD_WIDTH));
        assertEquals(parameters.getFirst("height"), valueOf(BOARD_HEIGHT));
        assertEquals(parameters.getFirst("table").length(), BOARD_WIDTH * BOARD_HEIGHT);
        assertEquals(parameters.getFirst("player"), PLAYER_X.toString());
    }

    @Test
    public void shouldURIStartWithSpecifiedPath() {
        // GIVEN
        final Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT, 1);

        // WHEN
        final URI uri = PlayerUriBuilder.buildUri("http://computer:8080", board, PLAYER_X);

        // THEN
        final String fullPath = UriComponentsBuilder.fromUri(uri).build().toString();
        assertTrue(fullPath.startsWith("http://computer:8080/nextMove"));
    }
}
