package com.gomoku.player;

import static com.gomoku.board.BoardFieldType.PLAYER_X;
import static java.lang.String.valueOf;
import static org.testng.Assert.assertEquals;

import java.net.URI;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @BeforeMethod
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
        assertEquals(valueOf(BOARD_WIDTH), parameters.getFirst("width"));
        assertEquals(valueOf(BOARD_HEIGHT), parameters.getFirst("height"));
        assertEquals(actualTable, parameters.getFirst("table"));
        assertEquals(PLAYER_X.toString(), parameters.getFirst("player"));

    }

}
