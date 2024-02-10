package com.example.generaltemplate;

import javafx.animation.AnimationTimer;

import java.util.*;

public class Game {
    private final Grid grid;
    private long simulationStartTime;

    private final ArrayList<Player> players = new ArrayList<>();
    private int turnNum = 0;
    private boolean turnOngoing = false;
    private int currentBuyer;


    // constants ↓
    private final int EXPLODER_RANGE = 3;
    private final int CHANGER_RANGE = 2;
    private final int SHIELD_RANGE = 1;
    private final List<PieceType> PROTECTED_PIECE_TYPES = Arrays.asList(PieceType.BASIC, PieceType.SHIELD);
    private final int STARTING_MONEY = 100;
    private final int MAX_TURN_NUMS = 5;

    public Game() {
        grid = new Grid();
        players.add(new Player("p1", Colors.RED, STARTING_MONEY));
        players.add(new Player("p2", Colors.BLUE, STARTING_MONEY));
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isPiecePlayable(PieceType pieceType, int selectedRow, int selectedCol, Movement movement) {
        if ((selectedRow == 0 && selectedCol != grid.getCells()[0].length-1 && selectedCol != 0)
                || (selectedCol == 0 && selectedRow != grid.getCells().length-1 && selectedRow != 0)
                || (selectedRow == grid.getCells()[0].length-1 && selectedCol != 0 && selectedCol != grid.getCells()[0].length-1)
                || (selectedCol == grid.getCells().length-1 && selectedRow != grid.getCells().length-1 && selectedRow != 0)) {
            if (pieceType.equals(PieceType.EXPLODER) || pieceType.equals(PieceType.CHANGER) || pieceType.equals(PieceType.HORIZONTAL_SCORER)) {
                return true;
            }
            return !grid.getCells()[selectedRow+movement.getRowMove()][selectedCol+movement.getColMove()].hasPiece();
        }
        return false;
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
                                    if (cell.getPiece().isNotAlreadyMoved() && grid.checkLocValid(row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove())) {
                                        cell.getPiece().setAlreadyMoved(true);
                                        grid.movePiece(row, col, row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove());
                                        noPiecesMoved = false;
                                    } else if (cell.getPiece().isNotAlreadyMoved()){
                                        // piece logic is here

                                        updateShieldedCells();
                                        cell.getPiece().setMovement(Movement.STILL);
                                        if (cell.getPiece().getPieceType().equals(PieceType.EXPLODER)) {
                                            for (int[] loc: grid.getNearbyPieceLocs(row, col, EXPLODER_RANGE)) {
                                                if (grid.getCells()[loc[0]][loc[1]].isNotShielded()) {
                                                    grid.getCells()[loc[0]][loc[1]].setPiece(null);
                                                }
                                            }
                                            grid.getCells()[row][col].setPiece(null);
                                        } else if (cell.getPiece().getPieceType().equals(PieceType.CHANGER)) {
                                            for (int[] loc: grid.getNearbyPieceLocs(row, col, CHANGER_RANGE)) {
                                                if (cell.isNotShielded()) {
                                                    grid.getCells()[loc[0]][loc[1]].getPiece().setColor(cell.getPiece().getColor());
                                                }
                                            }
                                            grid.getCells()[row][col].setPiece(null);
                                        } else if (cell.getPiece().getPieceType().equals(PieceType.HORIZONTAL_SCORER)) {
                                            for (Cell sCell : grid.getCells()[row]) {
                                                if (sCell.hasPiece()) {
                                                    if (cell.isNotShielded()) {
                                                        sCell.getPiece().setColor(cell.getPiece().getColor());
                                                    }
                                                }
                                            }
                                            grid.getCells()[row][col].setPiece(null);
                                        }
                                        updateShieldedCells();
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

        public void updateShieldedCells() {
            for (int i = 0; i < grid.getCells().length; i++) {
                for (int j = 0; j < grid.getCells()[i].length; j++) {
                    grid.getCells()[i][j].setShielded(false);
                }
            }

            for (int[] shieldLoc : grid.getAllPieceLocsOfType(PieceType.SHIELD)) {
                for (int[] pieceLoc : grid.getNearbyPieceLocs(shieldLoc[0], shieldLoc[1], SHIELD_RANGE)) {
                    if (grid.getCells()[pieceLoc[0]][pieceLoc[1]].getPiece().getColor().equals(grid.getCells()[shieldLoc[0]][shieldLoc[1]].getPiece().getColor()) && PROTECTED_PIECE_TYPES.contains(grid.getCells()[pieceLoc[0]][pieceLoc[1]].getPiece().getPieceType())) {
                        grid.getCells()[pieceLoc[0]][pieceLoc[1]].setShielded(true);
                    }
                }
            }
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

    public boolean incrementCurrentBuyer() {
        currentBuyer++;
        return  currentBuyer >= players.size();
    }

    public Player getCurrentBuyer() {
        return players.get(currentBuyer);
    }

    public boolean isOver() {
        return getCurrentPlayer().getPiecesOwned().isEmpty() || turnNum+1 > MAX_TURN_NUMS;
    }

    public Player findWinner() {
        Map<Player, Integer> playerPieceNums = new HashMap<>();
        for (int i = 0; i < grid.getCells().length; i++) {
            for (int j = 0; j < grid.getCells()[i].length; j++) {
                if (grid.getCells()[i][j].hasPiece()) {
                    for (Player player: players) {
                        if (player.getColor().equals(grid.getCells()[i][j].getPiece().getColor())) {
                            if (playerPieceNums.containsKey(player)) {
                                playerPieceNums.put(player, playerPieceNums.get(player)+1);
                            } else {
                                playerPieceNums.put(player, 1);
                            }
                        }
                    }

                }
            }
        }

        Player winner = null;
        int curVal = -1;
        for(Map.Entry<Player, Integer> entry : playerPieceNums.entrySet()) {
            if (entry.getValue() > curVal) {
                winner = entry.getKey();
                curVal = entry.getValue();
            } else if (entry.getValue() == curVal) {
                winner = null;
            }
        }
        return winner;
    }

    public int getTurnsLeft() {
        return MAX_TURN_NUMS - turnNum;
    }
}
