package com.gomoku.service;

import static com.gomoku.domain.board.BoardFieldType.NONE;
import static com.gomoku.domain.board.BoardFieldType.PLAYER_O;
import static com.gomoku.domain.board.BoardFieldType.PLAYER_X;
import static java.util.Collections.emptyMap;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gomoku.config.properties.BoardProperties;
import com.gomoku.domain.board.Board;
import com.gomoku.domain.board.BoardFieldType;
import com.gomoku.domain.game.task.GameState;
import com.gomoku.domain.game.task.GameTaskResult;
import com.gomoku.domain.history.HistoryStep;
import com.gomoku.repository.entity.Player;
import com.gomoku.service.GameExecutorService;
import com.gomoku.service.GameTaskService;

/**
 * Unit test for {@link GameTaskService}.
 *
 * @author zeldan
 *
 */
public class GameTaskServiceTest {

    private static final Player FIRST_PLAYER = new Player("firstPlayer", "http://192.168.0.1:8080");
    private static final Player SECOND_PLAYER = new Player("secondPlayer", "http://192.168.0.2:9000");

    private static final int BOARD_LIMIT_TWO_TO_WIN = 2;
    private static final int BOARD_LIMIT_THREE_TO_WIN = 3;
    private static final int BOARD_WIDTH = 2;
    private static final int BOARD_HEIGHT = 2;

    @Mock
    private GameExecutorService gameService;

    private GameTaskService underTest;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        underTest = new GameTaskService(new BoardProperties(BOARD_WIDTH, BOARD_HEIGHT, BOARD_LIMIT_TWO_TO_WIN), gameService);
    }

    @Test
    public void shouldChangeActualPlayerInEveryRound() {
        // GIVEN
        final Board notFullBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT, BOARD_LIMIT_THREE_TO_WIN, new BoardFieldType[][] {
                { PLAYER_X, PLAYER_O },
                { PLAYER_O, NONE }
        });

        final Board fullBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT, BOARD_LIMIT_THREE_TO_WIN, new BoardFieldType[][] {
                { PLAYER_X, PLAYER_O },
                { PLAYER_O, PLAYER_X }
        });

        final GameState gameStateWithNotFullBoard = new GameState(emptyMap(), notFullBoard);
        when(gameService.playOneRound(any(GameState.class), eq(PLAYER_O))).thenReturn(of(gameStateWithNotFullBoard));
        when(gameService.playOneRound(eq(gameStateWithNotFullBoard), eq(PLAYER_X))).thenReturn(of(gameStateWithNotFullBoard));
        when(gameService.playOneRound(eq(gameStateWithNotFullBoard), eq(PLAYER_O))).thenReturn(of(new GameState(emptyMap(), fullBoard)));

        // WHEN
        final GameTaskResult gameTaskResult = underTest.matchAgainstEachOther(FIRST_PLAYER, SECOND_PLAYER);

        // THEN
        assertFalse(gameTaskResult.getWinner().isPresent());
        final InOrder inOrder = inOrder(gameService);
        inOrder.verify(gameService).playOneRound(any(GameState.class), eq(PLAYER_O));
        inOrder.verify(gameService).playOneRound(gameStateWithNotFullBoard, PLAYER_X);
        inOrder.verify(gameService).playOneRound(gameStateWithNotFullBoard, PLAYER_O);
    }

    @Test
    public void shouldPlayerXBeTheWinner() {
        // GIVEN
        final Board fullBoardWithWinnerPlayerX = new Board(BOARD_WIDTH, BOARD_HEIGHT, BOARD_LIMIT_TWO_TO_WIN, new BoardFieldType[][] {
                { PLAYER_X, PLAYER_X },
                { PLAYER_O, PLAYER_X }
        });
        when(gameService.playOneRound(any(GameState.class), eq(PLAYER_O))).thenReturn(of(new GameState(emptyMap(), fullBoardWithWinnerPlayerX)));

        // WHEN
        final GameTaskResult gameTaskResult = underTest.matchAgainstEachOther(FIRST_PLAYER, SECOND_PLAYER);

        // THEN
        final Optional<Player> winner = gameTaskResult.getWinner();
        assertTrue(winner.isPresent());
        assertEquals(winner.get(), SECOND_PLAYER);
    }

    @Test
    public void shouldStoreHistoryAboutEverySteps() {
        // GIVEN
        final Board notFullBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT, BOARD_LIMIT_THREE_TO_WIN, new BoardFieldType[][] {
                { PLAYER_X, PLAYER_O },
                { PLAYER_O, NONE }
        });
        final Board fullBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT, BOARD_LIMIT_THREE_TO_WIN, new BoardFieldType[][] {
                { PLAYER_X, PLAYER_O },
                { PLAYER_O, PLAYER_X }
        });

        final GameState gameStateWithNotFullBoard = new GameState(emptyMap(), notFullBoard);
        when(gameService.playOneRound(any(GameState.class), eq(PLAYER_O))).thenReturn(of(gameStateWithNotFullBoard));
        when(gameService.playOneRound(eq(gameStateWithNotFullBoard), Mockito.eq(PLAYER_X))).thenReturn(of(new GameState(emptyMap(), fullBoard)));

        // WHEN
        final GameTaskResult gameTaskResult = underTest.matchAgainstEachOther(FIRST_PLAYER, SECOND_PLAYER);

        // THEN
        final List<HistoryStep> steps = gameTaskResult.getSteps();
        assertEquals(steps.size(), 2);
        assertHistoryStep(steps.get(0), 1, "XOON");
        assertHistoryStep(steps.get(1), 2, "XOOX");
    }

    private void assertHistoryStep(final HistoryStep historyStep, final int numberOfStep, final String actualBoard) {
        assertEquals(historyStep.getNumberOfStep(), numberOfStep);
        assertEquals(historyStep.getBoard(), actualBoard);

    }

}
