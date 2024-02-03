package com.example.generaltemplate;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Grid {
    private final int GRID_SIZE = 20;
    private final int BUTTON_SIZE = 20;
    private Cell[][] cells;
    private Button[][] buttons;

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
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                ImageView img = new ImageView();
                img.setFitHeight(BUTTON_SIZE);
                img.setPreserveRatio(true);
                buttons[row][col].setGraphic(img);
                buttons[row][col].setStyle(cells[row][col].getTerrainType().getCSS());
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Button[][] getButtons() {
        return buttons;
    }
}
