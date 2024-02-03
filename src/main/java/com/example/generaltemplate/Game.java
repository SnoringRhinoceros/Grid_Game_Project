package com.example.generaltemplate;

import javafx.scene.layout.GridPane;

public class Game {
    private Grid grid;

    public Game() {
        grid = new Grid();
    }

    public Grid getGrid() {
        return grid;
    }
}
