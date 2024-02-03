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
    private static Image PLAINS_IMG;
    private final int BUTTON_SIZE = 20;
    public GameController() {
        try {
            PLAINS_IMG = new Image(new FileInputStream("src/main/resources/com/example/generaltemplate/img/plains.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        game = new Game();
        for (int i = 0; i < game.getGrid().getButtons().length; i++) {
            for (int j = 0; j < game.getGrid().getButtons()[i].length; j++) {
                game.getGrid().getButtons()[i][j] = new Button();
                game.getGrid().getButtons()[i][j].setMinHeight(BUTTON_SIZE);
                game.getGrid().getButtons()[i][j].setMinWidth(BUTTON_SIZE);
                game.getGrid().getButtons()[i][j].setMaxHeight(BUTTON_SIZE);
                game.getGrid().getButtons()[i][j].setMaxWidth(BUTTON_SIZE);
                ImageView img = new ImageView(PLAINS_IMG);
                img.setFitHeight(BUTTON_SIZE);
                img.setPreserveRatio(true);
                game.getGrid().getButtons()[i][j].setGraphic(img);
                board.add(game.getGrid().getButtons()[i][j], j, i);
            }
        }

        board.setAlignment(Pos.CENTER);
        board.setGridLinesVisible(true);
    }


    private void update() {

    }
}