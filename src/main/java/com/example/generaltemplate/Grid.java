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
    private final Image SIDES_IMG = makeImg("src/main/resources/com/example/generaltemplate/img/sides_background.png");
    private final Image CORNER_IMG = makeImg("src/main/resources/com/example/generaltemplate/img/corner_background.png");

    public Grid() {
        cells = new Cell[GRID_SIZE][GRID_SIZE];
        buttons = new Button[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col] = new Cell(BorderTypes.NONE);
                buttons[row][col] = new Button();
                buttons[row][col].setMinHeight(BUTTON_SIZE);
                buttons[row][col].setMinWidth(BUTTON_SIZE);
                buttons[row][col].setMaxHeight(BUTTON_SIZE);
                buttons[row][col].setMaxWidth(BUTTON_SIZE);
            }
        }

        cells[6][5].setSolidObject(new Structure(StructureType.RICOCHET, -1));
        cells[12][13].setSolidObject(new Structure(StructureType.RICOCHET, 1));
        cells[13][5].setSolidObject(new Structure(StructureType.RICOCHET, -1));
        cells[5][13].setSolidObject(new Structure(StructureType.RICOCHET, 1));
        cells[9][4].setSolidObject(new Structure(StructureType.RICOCHET, 1));
        cells[8][14].setSolidObject(new Structure(StructureType.RICOCHET, -1));
        cells[4][9].setSolidObject(new Structure(StructureType.RICOCHET, 1));
        cells[14][10].setSolidObject(new Structure(StructureType.RICOCHET, 1));
//        cells[7][4].setSolidObject(new Structure(StructureType.RICOCHET, 1));
//        cells[7][7].setSolidObject(new Structure(StructureType.RICOCHET, -1));
//        cells[8][6].setSolidObject(new Structure(StructureType.RICOCHET, 1));
    }

    public boolean isLocBorder(int row, int col) {
        return row == 0 || row == cells.length-1 || col == 0 || col == cells.length-1;
    }

    public boolean isLocCorner(int row, int col) {
        return  (col == 0 || col == cells[0].length-1) && (row == 0 || row == cells.length-1);
    }

    public void update() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (row == 7 && col == 18) {
                    int foo = 1;
                }
                ImageView img;
                if (cells[row][col].hasSolidObject()) {
                    img = new ImageView(cells[row][col].getSolidObject().getImg());
                } else {
                    if ((row == 0 && col != cells[0].length-1 && col != 0)
                            || (col == 0 && row != cells.length-1 && row != 0)
                            || (row == cells[0].length-1 && col != 0 && col != cells[0].length-1)
                            || (col == cells.length-1 && row != cells.length-1 && row != 0)) {
                        img = new ImageView(SIDES_IMG);
                    } else if ((row == 0 && col == 0) || (row == 0 && col == cells[0].length-1)
                            || (row == cells.length-1 && col == 0) || (row == cells.length-1 && col == cells[0].length-1)) {
                        img = new ImageView(CORNER_IMG);
                    } else {
                        img = new ImageView(BLANK_CELL_IMG);
                    }
                }
                img.setFitHeight(BUTTON_SIZE-2);
                img.setPreserveRatio(true);
                buttons[row][col].setGraphic(img);
                buttons[row][col].setStyle(cells[row][col].getBorderType().getCSS());
            }
        }
    }

    public void movePiece(int oldRow, int oldCol, int newRow, int newCol) {
        cells[newRow][newCol].setSolidObject(cells[oldRow][oldCol].getSolidObject());
        cells[oldRow][oldCol].setSolidObject(null);
    }

    public boolean checkLocValid(int row, int col) {
        return 1 <= row && row < cells.length-1 && 1 <= col && col < cells[0].length-1 && !cells[row][col].hasSolidObject();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Button[][] getButtons() {
        return buttons;
    }

    private ArrayList<int[]> getNearbyLocs(int row, int col, int range) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = Math.max(row-range, 0) ; i < Math.min(row+range+1, cells.length); i++) {
            for (int j = Math.max(col-range, 0); j < Math.min(col+range+1, cells[i].length); j++) {
                int[] loc = {i, j};
                result.add(loc);
            }
        }
        return result;
    }

    public ArrayList<int[]> getNearbyAvailableLocs(int row, int col, int range) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int[] loc : getNearbyLocs(row, col, range)) {
            if (!cells[loc[0]][loc[1]].hasPiece() && !isLocBorder(loc[0], loc[1])) {
                result.add(loc);
            }
        }
        return result;
    }

    public ArrayList<int[]> getNearbyPieceLocs(int row, int col, int range) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int[] loc: getNearbyLocs(row, col, range)) {
            if (cells[loc[0]][loc[1]].hasPiece()) {
                result.add(loc);
            }
        }
        return result;
    }

    public ArrayList<int[]> getAllPieceLocsOfType(PieceType pieceType) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].hasPiece()) {
                    if (pieceType.equals(((Piece) cells[i][j].getSolidObject()).getPieceType())) {
                        int[] loc = {i, j};
                        result.add(loc);
                    }
                }
            }
        }
        return result;
    }
}
