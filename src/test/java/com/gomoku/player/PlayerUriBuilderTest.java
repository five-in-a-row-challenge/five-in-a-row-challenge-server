package com.gomoku.player;

import static com.gomoku.board.BoardFieldType.PLAYER_X;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Unit test for {@link PlayerUriBuilder}.
 *
 * @author zeldan
 *
 */
public class PlayerUriBuilderTest {

    private static final int BOARD_WIDTH = 2;
    private static final int BOARD_HEIGHT = 3;

    private PlayerUriBuilder underTest;

    @Before
    public void setUp() {
        underTest = new PlayerUriBuilder(BOARD_WIDTH, BOARD_HEIGHT);
    }

    @Test
    public void shouldBuildProperUriByParameters() {
        // GIVEN
        final String actualTable = "NNXNNO";

        // WHEN
        final URI uri = underTest.buildUri("http://computer:8080", actualTable, PLAYER_X);

        // THEN
        final MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
        assertEquals(parameters.getFirst("width"), valueOf(BOARD_WIDTH));
        assertEquals(parameters.getFirst("height"), valueOf(BOARD_HEIGHT));
        assertEquals(parameters.getFirst("table"), actualTable);
        assertEquals(parameters.getFirst("player"), PLAYER_X.toString());

    }

}
