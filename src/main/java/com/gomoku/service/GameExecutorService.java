package com.gomoku.service;

import static com.gomoku.domain.player.PlayerUriBuilder.buildUri;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomoku.domain.board.Board;
import com.gomoku.domain.board.BoardFieldType;
import com.gomoku.domain.game.task.GameState;
import com.gomoku.domain.player.Player;

/**
 * Gomoku game service, it is responsible for execute one match on the board by the actual state of the game and actual player.
 *
 * @author zeldan
 *
 */
@Service
public class GameExecutorService {

    private static final Logger LOG = getLogger(GameExecutorService.class);

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    @Autowired
    public GameExecutorService(final ObjectMapper objectMapper, final RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public Optional<GameState> playOneRound(final GameState gameState, final BoardFieldType actualPlayer) {
        final Player player = gameState.getPlayers().get(actualPlayer);
        final Board board = gameState.getBoard();
        final URI playerURI = buildUri(player.getNetworkAddress(), board, actualPlayer);
        final HttpEntity<String> userResponse = executeURI(playerURI);
        final Map<String, Integer> readValue = getValueFromUserResponse(userResponse.getBody());
        if (!readValue.isEmpty()) {
            final Board changedBoard = board.mark(readValue.get("x"), readValue.get("y"), actualPlayer);
            return of(new GameState(gameState.getPlayers(), changedBoard));
        }
        return empty();
    }

    private ResponseEntity<String> executeURI(final URI playerURI) {
        return restTemplate.exchange(
                playerURI,
                GET,
                getHttpEntity(),
                String.class);
    }

    private Map<String, Integer> getValueFromUserResponse(final String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<HashMap<String, Integer>>() {
            });
        } catch (final IOException e) {
            LOG.error("An error occured while try to parse user response.", e);
        }
        return Collections.<String, Integer> emptyMap();
    }

    private HttpEntity<Object> getHttpEntity() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

}
