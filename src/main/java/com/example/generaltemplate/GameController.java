package com.example.generaltemplate;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class GameController {
    @FXML
    public GridPane board;
    private Game game;
    private MyScreenController myScreenController;
    private int selectedRow;
    private int selectedCol;

    @FXML
    public void initialize() {
        EventHandler<ActionEvent> handleBoardClick = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {

                selectedRow = GridPane.getRowIndex((Button) event.getSource());
                selectedCol = GridPane.getColumnIndex((Button) event.getSource());
                System.out.println(selectedRow + "," + selectedCol);
            }
        };

        game = new Game();
        for (int i = 0; i < game.getGrid().getButtons().length; i++) {
            for (int j = 0; j < game.getGrid().getButtons()[i].length; j++) {
                board.add(game.getGrid().getButtons()[i][j], j, i);
                game.getGrid().getButtons()[i][j].setOnAction(handleBoardClick);
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