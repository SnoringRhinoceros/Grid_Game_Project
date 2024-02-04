package com.example.generaltemplate;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Game {
    private final Grid grid;
    private long simulationStartTime;

    private final ArrayList<Player> players = new ArrayList<>();
    private int turnNum = 0;

    public Game() {
        grid = new Grid();
        players.add(new Player("p1", "red"));
        players.add(new Player("p2", "blue"));
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean piecePlayable(int selectedRow, int selectedCol, Movement movement) {
        for (int i = 0; i < grid.getCells().length; i++) {
            for (int j = 0; j < grid.getCells()[i].length; j++) {
                if (grid.getCells()[i][j].hasPiece() && !grid.getCells()[i][j].getPiece().getMovement().equals(Movement.STILL)) {
                    return false;
                }
            }
        }
        return !grid.getCells()[selectedRow+movement.getRowMove()][selectedCol+movement.getColMove()].hasPiece();
    }

    public void playPiece(int selectedRow, int selectedCol, Movement movement) {
        if (getCurrentPlayer().getColor().equals("red")) {
            grid.getCells()[selectedRow][selectedCol].setPiece(new Piece(PieceType.RED_BASIC, movement));
        } else if (getCurrentPlayer().getColor().equals("blue")) {
            grid.getCells()[selectedRow][selectedCol].setPiece(new Piece(PieceType.BLUE_BASIC, movement));
        }
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
                                        turnNum++;
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

    private Player getCurrentPlayer() {
        return players.get(turnNum % players.size());
    }

    public void update() {
        grid.update();
    }
}
