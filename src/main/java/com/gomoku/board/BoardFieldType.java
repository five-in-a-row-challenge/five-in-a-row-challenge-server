package com.gomoku.board;

/**
 *
 * Type of the board field. There are two players: X and the Y, otherwise the type is NONE.
 *
 * @author zeldan
 *
 */
public enum BoardFieldType {

    NONE("N"), PLAYER_X("X"), PLAYER_O("O");

    private String type;

    private BoardFieldType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
