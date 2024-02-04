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

    public boolean piecePlayable(int selectedRow, int selectedCol, Movement movement) {
        return !grid.getCells()[selectedRow+movement.getRowMove()][selectedCol+movement.getColMove()].hasPiece();
    }

    public void playPiece(int selectedRow, int selectedCol, Movement movement) {
        grid.getCells()[selectedRow][selectedCol].setPiece(new Piece(PieceType.BASIC, movement));
        update();
    }

    public void simulateTurn() {
        simulationStartTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - simulationStartTime > 100000000.0) {
                    boolean doneSimulating = true;
                    boolean broken = false;
                    for (int row = 0; row < grid.getCells().length; row++) {
                        for (int col = 0; col < grid.getCells()[row].length; col++) {
                            Cell cell = grid.getCells()[row][col];
                            if (cell.hasPiece()) {
                                if (cell.getPiece().getMovement() != Movement.STILL) {
                                    if (grid.checkLocValid(row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove())) {
                                        grid.movePiece(row, col, row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove());
                                        doneSimulating = false;
                                        broken = true;
                                        break;
                                    } else {
                                        cell.getPiece().setMovement(Movement.STILL);
                                    }
                                    doneSimulating = false;
                                }
                            }
                        }
                        if (broken) {
                            break;
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
