package com.example.generaltemplate;

public enum Movement {
    STILL(0, 0), UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    private final int rowMove;
    private final int colMove;

    Movement(int rowMove, int colMove) {
        this.rowMove = rowMove;
        this.colMove = colMove;
    }

    public int getRowMove() {
        return rowMove;
    }

    public int getColMove() {
        return colMove;
    }

    public static Movement getMovement(int row, int col) {
        for (Movement movement: values()) {
            if (row == movement.rowMove && col == movement.colMove) {
                return movement;
            }
        }
        throw new RuntimeException("No movement exists");
    }
}
