package com.example.generaltemplate;

import javafx.animation.AnimationTimer;

import java.util.*;

import static com.example.generaltemplate.GameController.generateRandNum;

public class Game {
    private final Grid grid;
    private long simulationStartTime;

    private final ArrayList<Player> players = new ArrayList<>();
    private int turnNum = 0;
    private boolean turnOngoing = false;
    private int currentBuyer;


    // constants ↓
    public static final int EXPLODER_RANGE = 3;
    public static final int CHANGER_RANGE = 2;
    public static final int SHIELD_RANGE = 1;
    public static final List<PieceType> PROTECTED_PIECE_TYPES = Arrays.asList(PieceType.BASIC, PieceType.SHIELD);
    public static final int STARTING_MONEY = 100;
    public static final int MAX_TURN_NUMS = 100;
    public static final int MAX_BOUNCES = 2;
    public static final int SUMMONER_RANGE = 1;

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
        grid.getCells()[selectedRow][selectedCol].setSolidObject(new Piece(pieceType, player.getColor(), movement));
        grid.update();
    }

    public void simulateTurn(Runnable endFunc) {
        turnOngoing = true;
        simulationStartTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - simulationStartTime > 100000000.0) {

                    // resets all pieces
                    for (int row = 0; row < grid.getCells().length; row++) {
                        for (int col = 0; col < grid.getCells()[row].length; col++) {
                            Cell cell = grid.getCells()[row][col];
                            if (cell.hasPiece()) {
                                cell.getPiece().setAlreadyMoved(false);
                            }
                        }
                    }

                    // simulates piece movement
                    for (int row = 0; row < grid.getCells().length; row++) {
                        for (int col = 0; col < grid.getCells()[row].length; col++) {
                            Cell cell = grid.getCells()[row][col];
                            if (cell.hasPiece()) {
                                if (cell.getPiece().getMovement() != Movement.STILL) {
                                    if (!cell.getPiece().isAlreadyMoved()) {
                                        // does either bouncing or moving
                                        if (grid.getCells()[row+cell.getPiece().getMovement().getRowMove()][col+cell.getPiece().getMovement().getColMove()].hasStructure()
                                                && ((Structure) grid.getCells()[row+cell.getPiece().getMovement().getRowMove()][col+cell.getPiece().getMovement().getColMove()].getSolidObject()).getStructureType().equals(StructureType.RICOCHET)
                                                && cell.getPiece().getBounceNum() < MAX_BOUNCES) {
                                            Structure struct = (Structure) grid.getCells()[row + cell.getPiece().getMovement().getRowMove()][col + cell.getPiece().getMovement().getColMove()].getSolidObject();
                                            cell.getPiece().setMovement(Movement.getMovement(cell.getPiece().getMovement().getColMove() * struct.getSlope() * -1, cell.getPiece().getMovement().getRowMove() * struct.getSlope() * -1));
                                            cell.getPiece().incrementBounceNum();
                                        } else if (grid.checkLocValid(row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove())) {
                                            cell.getPiece().setAlreadyMoved(true);
                                            grid.movePiece(row, col, row+cell.getPiece().getMovement().getRowMove(), col+cell.getPiece().getMovement().getColMove());
                                        } else {
                                            // piece stopped moving logic is here
                                            updateShieldedCells();
                                            cell.getPiece().setMovement(Movement.STILL);
                                            if (cell.getPiece().getPieceType().equals(PieceType.EXPLODER)) {
                                                for (int[] loc : grid.getNearbyPieceLocs(row, col, EXPLODER_RANGE)) {
                                                    if (grid.getCells()[loc[0]][loc[1]].isNotShielded()) {
                                                        grid.getCells()[loc[0]][loc[1]].setSolidObject(null);
                                                    }
                                                }
                                                grid.getCells()[row][col].setSolidObject(null);
                                            } else if (cell.getPiece().getPieceType().equals(PieceType.CHANGER)) {
                                                for (int[] loc : grid.getNearbyPieceLocs(row, col, CHANGER_RANGE)) {
                                                    if (cell.isNotShielded()) {
                                                        grid.getCells()[loc[0]][loc[1]].getPiece().setColor(cell.getPiece().getColor());
                                                    }
                                                }
                                                grid.getCells()[row][col].setSolidObject(null);
                                            } else if (cell.getPiece().getPieceType().equals(PieceType.HORIZONTAL_SCORER)) {
                                                for (Cell sCell : grid.getCells()[row]) {
                                                    if (sCell.hasPiece()) {
                                                        if (cell.isNotShielded()) {
                                                            sCell.getPiece().setColor(cell.getPiece().getColor());
                                                        }
                                                    }
                                                }
                                                grid.getCells()[row][col].setSolidObject(null);
                                            }
                                            updateShieldedCells();



                                            // switches turn

                                            // performs summons
                                            for (int i = 0; i < grid.getCells().length; i++) {
                                                for (int j = 0; j < grid.getCells()[i].length; j++) {
                                                    Cell c = grid.getCells()[i][j];
                                                    if (c.hasPiece() && c.getPiece().getPieceType().equals(PieceType.SUMMONER)) {
                                                        ArrayList<int[]> availableLocs = grid.getNearbyAvailableLocs(i, j, SUMMONER_RANGE);
                                                        if (!availableLocs.isEmpty()) {
                                                            int[] spawnLoc = availableLocs.get(generateRandNum(0, availableLocs.size()-1));
                                                            grid.getCells()[spawnLoc[0]][spawnLoc[1]].setSolidObject(new Piece(PieceType.BASIC, c.getPiece().getColor(), Movement.STILL));
                                                        }
                                                    }
                                                }
                                            }

                                            grid.update();
                                            turnNum++;
                                            turnOngoing = false;
                                            endFunc.run();
                                            stop();
                                            return;
                                        }
                                        grid.update();
                                        simulationStartTime = System.nanoTime();
                                    }
                                }
                            }
                        }
                    }
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

    private void updateBoardPieceTrajectory(PieceType selectedPiece, int hoveredRow, int hoveredCol) {
        if (selectedPiece != null) {
            int pieceRow = hoveredRow;
            int pieceCol = hoveredCol;
            Movement pieceMovement = getPieceMovementBasedOnSpawn(hoveredRow, hoveredCol);
            int bounceNum = 0;
            while (pieceMovement != Movement.STILL) {
                if (grid.getCells()[pieceRow+pieceMovement.getRowMove()][pieceCol+pieceMovement.getColMove()].hasStructure()
                        && ((Structure) grid.getCells()[pieceRow+pieceMovement.getRowMove()][pieceCol+pieceMovement.getColMove()].getSolidObject()).getStructureType().equals(StructureType.RICOCHET)
                        && bounceNum < MAX_BOUNCES) {
                    Structure struct = (Structure) grid.getCells()[pieceRow + pieceMovement.getRowMove()][pieceCol + pieceMovement.getColMove()].getSolidObject();
                    pieceMovement = Movement.getMovement(pieceMovement.getColMove() * struct.getSlope() * -1, pieceMovement.getRowMove() * struct.getSlope() * -1);
                    bounceNum++;
                } else if (grid.checkLocValid(pieceRow+pieceMovement.getRowMove(), pieceCol+pieceMovement.getColMove())) {
                    pieceRow += pieceMovement.getRowMove();
                    pieceCol += pieceMovement.getColMove();
                    grid.getCells()[pieceRow][pieceCol].setBorderType(BorderTypes.HOVERED);
                }else {
                    pieceMovement = Movement.STILL;
                }
            }
        }
    }


    public Player getCurrentPlayer() {
        return players.get(turnNum % players.size());
    }

    public void update(PieceType selectedPiece, int hoveredRow, int hoveredCol) {
        boolean buttonHovered = false;
        for (int i = 0; i < grid.getCells().length; i++) {
            for (int j = 0; j < grid.getCells()[i].length; j++) {
                if (grid.getButtons()[j][i].isHover()) {
                    buttonHovered = true;
                    grid.getCells()[j][i].setBorderType(BorderTypes.HOVERED);
                } else {
                    grid.getCells()[j][i].setBorderType(BorderTypes.NONE);
                }
            }
        }
        if (buttonHovered) {
            updateBoardPieceTrajectory(selectedPiece, hoveredRow, hoveredCol);
        }
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

    public Movement getPieceMovementBasedOnSpawn(int row, int col) {
        Movement movement = Movement.STILL;
        if (row == 0) {
            movement = Movement.DOWN;
        } else if (row == grid.getCells().length-1) {
            movement = Movement.UP;
        } else if (col == 0) {
            movement = Movement.RIGHT;
        } else if (col == grid.getCells()[0].length-1) {
            movement = Movement.LEFT;
        }
        return movement;
    }
}
