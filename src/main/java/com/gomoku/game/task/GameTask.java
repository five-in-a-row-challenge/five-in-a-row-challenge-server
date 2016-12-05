package com.gomoku.game.task;

import static com.gomoku.board.BoardFieldType.PLAYER_O;
import static com.gomoku.board.BoardFieldType.PLAYER_X;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomoku.board.Board;
import com.gomoku.board.BoardFieldType;
import com.gomoku.board.BoardProperties;
import com.gomoku.history.HistoryStep;
import com.gomoku.player.Player;

/**
 * Task for playing game between two players.
 *
 * @author zeldan
 *
 */
@Component
public class GameTask {

    private final BoardProperties boardProperties;

    private final GameExecutorService gameService;

    @Autowired
    public GameTask(final BoardProperties boardProperties, final GameExecutorService gameService) {
        this.boardProperties = boardProperties;
        this.gameService = gameService;
    }

    public GameTaskResult matchAgainstEachOther(final Player firstPlayer, final Player secondPlayer) {
        final List<HistoryStep> steps = new ArrayList<>();
        final Map<BoardFieldType, Player> playersWithId = createPlayersType(firstPlayer, secondPlayer);
        Board board = new Board(boardProperties.getWidth(), boardProperties.getHeight(), boardProperties.getLimitToWin());
        GameState gameState = new GameState(playersWithId, board);
        BoardFieldType actualPlayer = PLAYER_O;
        int numberOfStep = 0;
        while (boardIsNotFullAndThereIsNoWinner(board)) {
            final Optional<GameState> newGameState = gameService.playOneRound(gameState, actualPlayer);
            if (newGameState.isPresent()) {
                gameState = newGameState.get();
            }
            board = newGameState.get().getBoard();
            actualPlayer = changePlayer(actualPlayer);
            steps.add(new HistoryStep(++numberOfStep, board.toString()));
        }
        return new GameTaskResult(getWinnerPlayer(playersWithId, board.getWinner()), steps);
    }

    private Optional<Player> getWinnerPlayer(final Map<BoardFieldType, Player> playersWithId, final Optional<BoardFieldType> winnerField) {
        if (winnerField.isPresent()) {
            return of(playersWithId.get(winnerField.get()));
        } else {
            return empty();
        }
    }

    private Map<BoardFieldType, Player> createPlayersType(final Player firstPlayer, final Player secondPlayer) {
        final Map<BoardFieldType, Player> playersWithId = new HashMap<>();
        playersWithId.put(PLAYER_O, firstPlayer);
        playersWithId.put(PLAYER_X, secondPlayer);
        return playersWithId;
    }

    private boolean boardIsNotFullAndThereIsNoWinner(final Board board) {
        return !board.getWinner().isPresent() && !board.isFull();
    }

    private BoardFieldType changePlayer(final BoardFieldType actualPlayer) {
        return PLAYER_O.equals(actualPlayer) ? PLAYER_X : PLAYER_O;
    }

}
