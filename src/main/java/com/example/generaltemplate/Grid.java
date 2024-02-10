package com.example.generaltemplate;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static com.example.generaltemplate.GameController.makeImg;

public class Grid {
    private final int GRID_SIZE = 20;
    private final int BUTTON_SIZE = 20;
    private final Cell[][] cells;
    private final Button[][] buttons;
    private final Image BLANK_CELL_IMG = makeImg("src/main/resources/com/example/generaltemplate/img/background.png");

    public Grid() {
        cells = new Cell[GRID_SIZE][GRID_SIZE];
        buttons = new Button[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col] = new Cell(TerrainTypes.NONE);
                buttons[row][col] = new Button();
                buttons[row][col].setMinHeight(BUTTON_SIZE);
                buttons[row][col].setMinWidth(BUTTON_SIZE);
                buttons[row][col].setMaxHeight(BUTTON_SIZE);
                buttons[row][col].setMaxWidth(BUTTON_SIZE);
            }
        }
    }

    public void update() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                ImageView img;
                if (cells[row][col].hasPiece()) {
                    img = new ImageView(cells[row][col].getPiece().getImage());
                } else {
                    img = new ImageView(BLANK_CELL_IMG);
                }
                img.setFitHeight(BUTTON_SIZE-2);
                img.setPreserveRatio(true);
                buttons[row][col].setGraphic(img);
                buttons[row][col].setStyle(cells[row][col].getTerrainType().getCSS());
            }
        }
    }

    public void movePiece(int oldRow, int oldCol, int newRow, int newCol) {
        cells[newRow][newCol].setPiece(cells[oldRow][oldCol].getPiece());
        cells[oldRow][oldCol].setPiece(null);
    }

    public boolean checkLocValid(int row, int col) {
        return 1 <= row && row < cells.length-1 && 1 <= col && col < cells[0].length-1 && !cells[row][col].hasPiece();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Button[][] getButtons() {
        return buttons;
    }

    public ArrayList<int[]> getNearbyPieceLocs(int row, int col, int range) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = Math.max(row-range, 0) ; i < Math.min(row+range+1, cells.length); i++) {
            for (int j = Math.max(col-range, 0); j < Math.min(col+range+1, cells[i].length); j++) {
                if (!(i == row && j == col)) {
                    if (cells[i][j].hasPiece()) {
                        int[] loc = {i, j};
                        result.add(loc);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<int[]> getAllPieceLocsOfType(PieceType pieceType) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].hasPiece()) {
                    if (pieceType.equals(cells[i][j].getPiece().getPieceType())) {
                        int[] loc = {i, j};
                        result.add(loc);
                    }
                }
            }
        }
        return result;
    }
}
