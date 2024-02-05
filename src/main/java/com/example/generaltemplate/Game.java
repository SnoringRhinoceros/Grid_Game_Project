package com.example.generaltemplate;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Game {
    private final Grid grid;
    private long simulationStartTime;

    private final ArrayList<Player> players = new ArrayList<>();
    private int turnNum = 0;
    private boolean turnOngoing = false;

    public Game() {
        grid = new Grid();
        players.add(new Player("p1", Colors.RED));
        players.add(new Player("p2", Colors.BLUE));

        players.get(0).getPiecesOwned().add(PieceType.BASIC);
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean piecePlayable(int selectedRow, int selectedCol, Movement movement) {
        return !(grid.getCells()[selectedRow+movement.getRowMove()][selectedCol+movement.getColMove()].hasPiece() || turnOngoing);
    }

    public void playPiece(int selectedRow, int selectedCol, Movement movement) {
        for (Colors color: Colors.values()) {
            if (getCurrentPlayer().getColor().equals(color)) {
                grid.getCells()[selectedRow][selectedCol].setPiece(new Piece(PieceType.BASIC, color, movement));
            }
        }
        update();
    }

    public void simulateTurn(Runnable endFunc) {
        turnOngoing = true;
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
                        turnOngoing = false;
                        endFunc.run();
                        stop();
                    }
                    update();
                    simulationStartTime = System.nanoTime();
                }
            }
        }.start();
    }

    public Player getCurrentPlayer() {
        return players.get(turnNum % players.size());
    }

    public void update() {
        grid.update();
    }

    public boolean isTurnOngoing() {
        return turnOngoing;
    }
}
