package com.example.generaltemplate;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GameController {
    @FXML
    public GridPane board;
    @FXML
    public ListView<String> ownedPiecesListView, piecesToBuyListView;
    @FXML
    public AnchorPane gameAnchorPane, buyAnchorPane, ownedPiecesAnchorPane;
    @FXML
    public Button startBtn, buyPieceBtn;
    @FXML
    public Label moneyLbl;
    private Game game;
    private MyScreenController myScreenController;
    private int selectedRow, selectedCol;
    private PieceType selectedPiece;
    private ListView<String> selectedListView;

    public static Image makeImg(String imgPath) {
        try {
            return new Image(new FileInputStream(imgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void initialize() {
        EventHandler<MouseEvent> handleSelectedPieceListViewClick = event -> {
            String selectedName = ((ListView<String>) event.getSource()).getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                for (PieceType pieceType: PieceType.values()) {
                    if (pieceType.getName().equals(selectedName)) {
                        selectedPiece = pieceType;
                        selectedListView = (ListView<String>) event.getSource();
                    }
                }
            } else {
                selectedPiece = null;
            }
        };

        piecesToBuyListView.setOnMouseClicked(handleSelectedPieceListViewClick);
        ownedPiecesListView.setOnMouseClicked(handleSelectedPieceListViewClick);

        EventHandler<MouseEvent> handleBuyPieceBtnClick = event -> {
            if (selectedPiece != null && selectedListView.equals(piecesToBuyListView)) {
                game.getCurrentBuyer().addMoney(-selectedPiece.getPrice());
                game.getCurrentBuyer().getPiecesOwned().add(selectedPiece);
                updateBuyView();
            }
        };
        buyPieceBtn.setOnMouseClicked(handleBuyPieceBtnClick);


        EventHandler<ActionEvent> handleBoardClick = event -> {

            selectedRow = GridPane.getRowIndex((Button) event.getSource());
            selectedCol = GridPane.getColumnIndex((Button) event.getSource());
            System.out.println(selectedRow + "," + selectedCol);

            if (selectedPiece != null) {
                Movement movement = Movement.STILL;
                if (selectedRow == 0) {
                    movement = Movement.DOWN;
                } else if (selectedRow == game.getGrid().getCells().length-1) {
                    movement = Movement.UP;
                } else if (selectedCol == 0) {
                    movement = Movement.RIGHT;
                } else if (selectedCol == game.getGrid().getCells()[0].length-1) {
                    movement = Movement.LEFT;
                }

                if (!game.isTurnOngoing() && (game.normalPiecePlayable(selectedRow, selectedCol, movement) || selectedPiece.equals(PieceType.EXPLODER))) {
                    game.playPiece(game.getCurrentPlayer(), selectedPiece, selectedRow, selectedCol, movement);
                    game.simulateTurn(this::switchTurn);
                }
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
        playView.addFXMLElement(gameAnchorPane);
        playView.addFXMLElement(ownedPiecesAnchorPane);
        myScreenController.add(playView);

        MyScreen buyView = new MyScreen("buyView");
        buyView.addFXMLElement(ownedPiecesAnchorPane);
        buyView.addFXMLElement(buyAnchorPane);

        myScreenController.add(buyView);

        myScreenController.activate("buyView");

        EventHandler<MouseEvent> startBtnClick = mouseEvent -> {
            myScreenController.activate("playView");
        };

        startBtn.setOnMouseClicked(startBtnClick);

        updateBuyView();
    }

    public void switchTurn() {
        selectedPiece = null;
        updatePlayView();
    }

    private void updatePlayView() {
        game.update();
        updateOwnedPiecesListView();
    }

    private void updateBuyView() {
        updatePiecesToBuyListView();
        updateMoneyLbl();
        updateOwnedPiecesListView();
    }

    private void updateOwnedPiecesListView() {
       ownedPiecesListView.getItems().clear();
        for (PieceType piece: game.getCurrentPlayer().getPiecesOwned()) {
            ownedPiecesListView.getItems().add(piece.getName());
        }
    }

    private void updatePiecesToBuyListView() {
        piecesToBuyListView.getItems().clear();
        for (PieceType piece : PieceType.values()) {
            piecesToBuyListView.getItems().add(piece.getName());
        }
    }

    private void updateMoneyLbl() {
        moneyLbl.setText("Money: " + game.getCurrentBuyer().getMoney());
    }
}