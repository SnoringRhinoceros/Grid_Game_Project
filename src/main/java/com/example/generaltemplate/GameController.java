package com.example.generaltemplate;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameController {
    @FXML
    public GridPane board;
    private Game game;
    public GameController() {

    }

    @FXML
    public void initialize() {
        game = new Game();

        for (int i = 0; i < game.getGrid().getButtons().length; i++) {
            for (int j = 0; j < game.getGrid().getButtons()[i].length; j++) {
                board.add(game.getGrid().getButtons()[i][j], j, i);
            }
        }

        board.setAlignment(Pos.CENTER);
        board.setGridLinesVisible(true);
        update();
    }


    private void update() {
        game.getGrid().update();
    }
}