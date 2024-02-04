package com.example.generaltemplate;

import javafx.animation.AnimationTimer;

public class Game {
    private Grid grid;
    private long simulationStartTime;

    public Game() {
        grid = new Grid();
    }

    public Grid getGrid() {
        return grid;
    }

    public void playPiece(int selectedRow, int selectedCol, Movement movement) {
        grid.getCells()[selectedRow][selectedCol].setPiece(Piece.BASIC);
        grid.getCells()[selectedRow][selectedCol].getPiece().setMovement(movement);
        update();
    }

    public void simulateTurn() {
        simulationStartTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - simulationStartTime > 100000000.0) {
                    for (int row = 0; row < grid.getCells().length; row++) {
                        for (int col = 0; col < grid.getCells()[row].length; col++) {
                            if (grid.getCells()[row][col].hasPiece()) {
                                grid.getCells()[row][col].getPiece().setMovedAlready(false);
                            }
                        }
                    }

                    boolean doneSimulating = true;
                    for (int row = 0; row < grid.getCells().length; row++) {
                        for (int col = 0; col < grid.getCells()[row].length; col++) {
                            Cell cell = grid.getCells()[row][col];
                            if (cell.hasPiece()) {
                                if (cell.getPiece().getMovement() != Movement.STILL && !cell.getPiece().isMovedAlready()) {
                                    if (grid.checkLocValid(row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove())) {
                                        cell.getPiece().setMovedAlready(true);
                                        grid.movePiece(row, col, row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove());
                                    }
                                    doneSimulating = false;
                                }
                            }
                        }
                    }
                    if (doneSimulating) {
                        stop();
                    }
                    update();
                    simulationStartTime = System.nanoTime();
                }
            }
        }.start();
    }

    public void update() {
        grid.update();
    }
}
