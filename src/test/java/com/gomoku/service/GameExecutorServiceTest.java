package com.gomoku.service;

import static com.gomoku.domain.board.BoardFieldType.PLAYER_O;
import static com.gomoku.domain.board.BoardFieldType.PLAYER_X;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomoku.domain.board.Board;
import com.gomoku.domain.board.BoardFieldType;
import com.gomoku.domain.game.task.GameState;
import com.gomoku.repository.entity.Player;
import com.gomoku.service.GameExecutorService;

/**
 * Unit test for {@link GameExecutorService}.
 */
public class GameExecutorServiceTest {

    private static final String FIRST_PLAYER_ID = "Player1";
    private static final String FIRST_PLAYER_NETWORK_ADDRESS = "http://192.168.0.1:8080";
    private static final String SECOND_PLAYER_ID = "Player2";
    private static final String SECOND_PLAYER_NETWORK_ADDRESS = "http://192.168.0.2:8080";

    private static final int ROW_NUMBER_TO_MARK = 1;
    private static final int COLUMN_NUMBER_TO_MARK = 3;

    @Mock
    private RestTemplate restTemplateMock;

    private GameExecutorService underTest;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        underTest = new GameExecutorService(new ObjectMapper(), restTemplateMock);
    }

    @Test
    public void shouldMoveToNextGameStateByUserResponse() {
        // GIVEN
        final ResponseEntity<String> userAnswerResponse = createUserAnswerResponse(COLUMN_NUMBER_TO_MARK, ROW_NUMBER_TO_MARK);
        when(restTemplateMock.exchange(any(URI.class), eq(GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(userAnswerResponse);
        final Map<BoardFieldType, Player> players = initPlayers();
        final GameState previousGameState = new GameState(players, new Board(3, 3, 3));

        // WHEN
        final Optional<GameState> actualGameState = underTest.playOneRound(previousGameState, PLAYER_X);

        // THEN
        assertEquals(previousGameState.getBoard().toString(), "NNNNNNNNN");
        assertTrue(actualGameState.isPresent());
        assertEquals(actualGameState.get().getBoard().toString(), "NNXNNNNNN");
    }

    private Map<BoardFieldType, Player> initPlayers() {
        final Map<BoardFieldType, Player> players = new HashMap<>();
        players.put(PLAYER_O, new Player(FIRST_PLAYER_ID, FIRST_PLAYER_NETWORK_ADDRESS));
        players.put(PLAYER_X, new Player(SECOND_PLAYER_ID, SECOND_PLAYER_NETWORK_ADDRESS));
        return players;
    }

    private ResponseEntity<String> createUserAnswerResponse(final int x, final int y) {
        final String response = "{"
                + "\"x\": " + x + ","
                + "\"y\": " + y
                + "}";
        return new ResponseEntity<String>(response, OK);
    }
}
