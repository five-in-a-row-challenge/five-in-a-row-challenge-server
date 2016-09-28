package com.gomoku.board;

import static com.gomoku.board.BoardType.O;
import static com.gomoku.board.BoardType.X;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * Unit test for {@link Board}.
 *
 * @author zeldan
 *
 */
public class BoardTest {

    @Test
    public void shouldNotBeFullAfterInitialization() {
        // GIVEN
        final Board board = new Board(1, 1);

        // WHEN

        // THEN
        assertFalse(board.isFull());
    }

    @Test
    public void shouldBeFullAfterMarkIfTheWidthAndHeightIsOne() {
        // GIVEN
        final Board board = new Board(1, 1);

        // WHEN
        board.mark(1, 1, X);

        // THEN
        assertTrue(board.isFull());
    }

    @Test
    public void shouldNotBeFullifOneFieldIsMissingAndTheWidthAndHeightIsThree() {
        // GIVEN
        final Board board = new Board(3, 3);

        // WHEN
        board.mark(1, 1, X);
        board.mark(1, 2, O);
        board.mark(1, 3, X);
        board.mark(2, 1, O);
        board.mark(2, 2, X);
        board.mark(2, 3, O);
        board.mark(3, 1, X);
        board.mark(3, 2, O);

        // THEN
        assertFalse(board.isFull());
    }

    @Test
    public void shouldBeFullAfterMarkAllFieldsAndTheWidthAndHeightIsThree() {
        // GIVEN
        final Board board = new Board(3, 3);

        // WHEN
        board.mark(1, 1, X);
        board.mark(1, 2, O);
        board.mark(1, 3, X);
        board.mark(2, 1, O);
        board.mark(2, 2, X);
        board.mark(2, 3, O);
        board.mark(3, 1, X);
        board.mark(3, 2, O);
        board.mark(3, 3, X);

        // THEN
        assertTrue(board.isFull());
    }

}
