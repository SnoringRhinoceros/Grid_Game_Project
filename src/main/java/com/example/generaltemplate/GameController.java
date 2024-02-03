package com.example.generaltemplate;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;


public class GameController {
    @FXML
    public GridPane board;
    private Game game;
    private MyScreenController myScreenController;

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

        myScreenController = new MyScreenController();

        MyScreen playView = new MyScreen("playView");
        playView.addFXMLElement(board);
        myScreenController.add(playView);

        myScreenController.activate("playView");

        update();
    }


    private void update() {
        game.getGrid().update();
    }
}