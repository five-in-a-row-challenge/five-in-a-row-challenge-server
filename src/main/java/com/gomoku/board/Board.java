package com.gomoku.board;

import static com.gomoku.board.BoardType.NONE;
import static java.util.Arrays.fill;

/**
 * The board of gomoku game.
 *
 * @author zeldan
 *
 */
public class Board {

    private final BoardType[][] table;

    public Board(final int width, final int height) {
        this.table = new BoardType[width][height];
        for (final BoardType[] row : table) {
            fill(row, NONE);
        }
    }

    public void mark(final int x, final int y, final BoardType boardType) {
        table[x - 1][y - 1] = boardType;
    }

    public boolean isFull() {
        for (final BoardType[] row : table) {
            for (final BoardType element : row) {
                if (NONE.equals(element)) {
                    return false;
                }
            }
        }
        return true;
    }
}
