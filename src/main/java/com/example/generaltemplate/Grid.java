package com.example.generaltemplate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Grid {
    private final int GRID_SIZE = 20;
    private final int BUTTON_SIZE = 20;
    private Cell[][] cells;
    private Button[][] buttons;

    public Grid() {
        cells = new Cell[GRID_SIZE][GRID_SIZE];
        buttons = new Button[GRID_SIZE][GRID_SIZE];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Button[][] getButtons() {
        return buttons;
    }
}
