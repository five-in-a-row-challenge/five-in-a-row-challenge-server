package com.gomoku.board;

import static com.gomoku.board.BoardFieldType.PLAYER_O;
import static com.gomoku.board.BoardFieldType.PLAYER_X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for {@link Board}.
 *
 * @author zeldan
 *
 */
public class BoardTest {

    private static final int LIMIT_TO_WIN = 5;

    @Test
    public void shouldNotBeFullAfterInitialization() {
        // GIVEN
        final Board board = new Board(1, 1, LIMIT_TO_WIN);

        // WHEN

        // THEN
        assertFalse(board.isFull());
    }

    @Test
    public void shouldBeFullAfterMarkIfTheWidthAndHeightIsOne() {
        // GIVEN
        final Board firstBoard = new Board(1, 1, LIMIT_TO_WIN);

        // WHEN
        final Board actualBoard = firstBoard.mark(1, 1, PLAYER_X);

        // THEN
        assertTrue(actualBoard.isFull());
    }

    @Test
    public void shouldNotBeFullIfOneFieldIsMissingAndTheWidthAndHeightIsThree() {
        // GIVEN
        final Board firstBoard = new Board(3, 3, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(1, 1, PLAYER_X);
        actualBoard = actualBoard.mark(1, 2, PLAYER_O);
        actualBoard = actualBoard.mark(1, 3, PLAYER_X);
        actualBoard = actualBoard.mark(2, 1, PLAYER_O);
        actualBoard = actualBoard.mark(2, 2, PLAYER_X);
        actualBoard = actualBoard.mark(2, 3, PLAYER_O);
        actualBoard = actualBoard.mark(3, 1, PLAYER_X);
        actualBoard = actualBoard.mark(3, 2, PLAYER_O);

        // THEN
        assertFalse(actualBoard.isFull());
    }

    @Test
    public void shouldBeFullAfterMarkAllFieldsAndTheWidthAndHeightIsThree() {
        // GIVEN
        final Board firstBoard = new Board(3, 3, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(1, 1, PLAYER_X);
        actualBoard = actualBoard.mark(1, 2, PLAYER_O);
        actualBoard = actualBoard.mark(1, 3, PLAYER_X);
        actualBoard = actualBoard.mark(2, 1, PLAYER_O);
        actualBoard = actualBoard.mark(2, 2, PLAYER_X);
        actualBoard = actualBoard.mark(2, 3, PLAYER_O);
        actualBoard = actualBoard.mark(3, 1, PLAYER_X);
        actualBoard = actualBoard.mark(3, 2, PLAYER_O);
        actualBoard = actualBoard.mark(3, 3, PLAYER_X);

        // THEN
        assertTrue(actualBoard.isFull());
    }

    @Test
    public void shouldNotBeWinnerHorizontallyIfOneFieldIsMissing() {
        // GIVEN
        final Board firstBoard = new Board(5, 5, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(5, 1, PLAYER_X);
        actualBoard = actualBoard.mark(5, 2, PLAYER_X);
        actualBoard = actualBoard.mark(5, 3, PLAYER_O);
        actualBoard = actualBoard.mark(5, 4, PLAYER_X);
        actualBoard = actualBoard.mark(5, 5, PLAYER_X);

        // THEN
        assertFalse(actualBoard.getWinner().isPresent());
    }

    @Test
    public void shouldXBeTheWinnerHorizontally() {
        // GIVEN
        final Board firstBoard = new Board(5, 5, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(5, 1, PLAYER_X);
        actualBoard = actualBoard.mark(5, 2, PLAYER_X);
        actualBoard = actualBoard.mark(5, 3, PLAYER_X);
        actualBoard = actualBoard.mark(5, 4, PLAYER_X);
        actualBoard = actualBoard.mark(5, 5, PLAYER_X);

        // THEN
        assertEquals(PLAYER_X, actualBoard.getWinner().get());
    }

    @Test
    public void shouldNotBeWinnerVerticallyIfOneFieldIsMissing() {
        // GIVEN
        final Board firstBoard = new Board(5, 5, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(1, 5, PLAYER_X);
        actualBoard = actualBoard.mark(2, 5, PLAYER_X);
        actualBoard = actualBoard.mark(3, 5, PLAYER_O);
        actualBoard = actualBoard.mark(4, 5, PLAYER_X);
        actualBoard = actualBoard.mark(5, 5, PLAYER_X);

        // THEN
        assertFalse(actualBoard.getWinner().isPresent());
    }

    @Test
    public void shouldXBeTheWinnerVertically() {
        // GIVEN
        final Board firstBoard = new Board(5, 5, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(1, 5, PLAYER_X);
        actualBoard = actualBoard.mark(2, 5, PLAYER_X);
        actualBoard = actualBoard.mark(3, 5, PLAYER_X);
        actualBoard = actualBoard.mark(4, 5, PLAYER_X);
        actualBoard = actualBoard.mark(5, 5, PLAYER_X);

        // THEN
        assertEquals(PLAYER_X, actualBoard.getWinner().get());
    }

    @Test
    public void shouldNotBeWinnerHorizontallyIfTheCountIsNotInOneRow() {
        // GIVEN
        final Board firstBoard = new Board(5, 5, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(1, 3, PLAYER_X);
        actualBoard = actualBoard.mark(1, 4, PLAYER_X);
        actualBoard = actualBoard.mark(1, 5, PLAYER_X);
        actualBoard = actualBoard.mark(2, 1, PLAYER_X);
        actualBoard = actualBoard.mark(2, 2, PLAYER_X);

        // THEN
        assertFalse(actualBoard.getWinner().isPresent());
    }

    @Test
    public void shouldNotBeWinnerVerticallyIfTheCountIsNotInOneColumn() {
        // GIVEN
        final Board firstBoard = new Board(5, 5, LIMIT_TO_WIN);

        // WHEN
        Board actualBoard = firstBoard.mark(3, 1, PLAYER_X);
        actualBoard = actualBoard.mark(4, 1, PLAYER_X);
        actualBoard = actualBoard.mark(5, 1, PLAYER_X);
        actualBoard = actualBoard.mark(1, 2, PLAYER_X);
        actualBoard = actualBoard.mark(2, 2, PLAYER_X);

        // THEN
        assertFalse(actualBoard.getWinner().isPresent());
    }

}
