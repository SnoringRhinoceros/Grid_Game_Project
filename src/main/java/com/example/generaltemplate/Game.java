package com.example.generaltemplate;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Game {
    private final Grid grid;
    private long simulationStartTime;

    private final ArrayList<Player> players = new ArrayList<>();
    private int turnNum = 0;
    private boolean turnOngoing = false;
    private final int EXPLODER_RANGE = 3;
    private final int CHANGER_RANGE = 2;

    public Game() {
        grid = new Grid();
        players.add(new Player("p1", Colors.RED));
        players.add(new Player("p2", Colors.BLUE));

        players.get(0).getPiecesOwned().add(PieceType.BASIC);
        players.get(0).getPiecesOwned().add(PieceType.BASIC);
        players.get(0).getPiecesOwned().add(PieceType.BASIC);
        players.get(0).getPiecesOwned().add(PieceType.BASIC);
        players.get(0).getPiecesOwned().add(PieceType.EXPLODER);
        players.get(0).getPiecesOwned().add(PieceType.CHANGER);

        players.get(1).getPiecesOwned().add(PieceType.BASIC);
        players.get(1).getPiecesOwned().add(PieceType.BASIC);
        players.get(1).getPiecesOwned().add(PieceType.BASIC);
        players.get(1).getPiecesOwned().add(PieceType.BASIC);
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean normalPiecePlayable(int selectedRow, int selectedCol, Movement movement) {
        return !grid.getCells()[selectedRow+movement.getRowMove()][selectedCol+movement.getColMove()].hasPiece();
    }

    public void playPiece(Player player, PieceType pieceType, int selectedRow, int selectedCol, Movement movement) {
        player.getPiecesOwned().remove(pieceType);
        grid.getCells()[selectedRow][selectedCol].setPiece(new Piece(pieceType, player.getColor(), movement));
        update();
    }

    public void simulateTurn(Runnable endFunc) {
        turnOngoing = true;
        simulationStartTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - simulationStartTime > 100000000.0) {
                    boolean noPiecesMoved = true;
                    for (int row = 0; row < grid.getCells().length; row++) {
                        for (int col = 0; col < grid.getCells()[row].length; col++) {
                            Cell cell = grid.getCells()[row][col];
                            if (cell.hasPiece()) {
                                cell.getPiece().setAlreadyMoved(false);
                            }
                        }
                    }

                    for (int row = 0; row < grid.getCells().length; row++) {
                        for (int col = 0; col < grid.getCells()[row].length; col++) {
                            Cell cell = grid.getCells()[row][col];
                            if (cell.hasPiece()) {
                                if (cell.getPiece().getMovement() != Movement.STILL) {
                                    if (!cell.getPiece().isAlreadyMoved() && grid.checkLocValid(row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove())) {
                                        cell.getPiece().setAlreadyMoved(true);
                                        grid.movePiece(row, col, row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove());
                                        noPiecesMoved = false;
                                    } else if (!cell.getPiece().isAlreadyMoved()){
                                        cell.getPiece().setMovement(Movement.STILL);
                                        if (cell.getPiece().getPieceType().equals(PieceType.EXPLODER)) {
                                            for (int[] loc: grid.getNearbyPieceLocs(row, col, EXPLODER_RANGE)) {
                                                grid.getCells()[loc[0]][loc[1]].setPiece(null);
                                            }
                                            grid.getCells()[row][col].setPiece(null);
                                        } else if (cell.getPiece().getPieceType().equals(PieceType.CHANGER)) {
                                            for (int[] loc: grid.getNearbyPieceLocs(row, col, CHANGER_RANGE)) {
                                                grid.getCells()[loc[0]][loc[1]].getPiece().setColor(cell.getPiece().getColor());
                                            }
                                            grid.getCells()[row][col].setPiece(null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (noPiecesMoved) {
                        turnNum++;
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
