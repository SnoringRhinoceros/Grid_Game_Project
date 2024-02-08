package com.example.generaltemplate;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public AnchorPane gameAnchorPane, buyAnchorPane, ownedPiecesAnchorPane, endScreenAnchorPane;
    @FXML
    public Button startBtn, buyPieceBtn, nextPlayerBuyBtn;
    @FXML
    public Label moneyLbl, buyViewPlayerNameLbl, playViewPlayerNameLbl, turnsLeftLbl;
    @FXML
    public TextArea buyPieceDescriptionTextArea, playPieceDescriptionTextArea;
    @FXML
    public ImageView endScreenImageView;
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
        startBtn.setVisible(false);
        buyPieceDescriptionTextArea.setEditable(false);
        buyPieceDescriptionTextArea.setWrapText(true);
        playPieceDescriptionTextArea.setEditable(false);
        playPieceDescriptionTextArea.setWrapText(true);

        EventHandler<MouseEvent> handleNextPlayerBuyBtnClick = event -> {
            if (!game.incrementCurrentBuyer()) {
                startBtn.setVisible(true);
                nextPlayerBuyBtn.setVisible(false);
            }
            updateBuyView(game.getCurrentBuyer());
        };
        nextPlayerBuyBtn.setOnMouseClicked(handleNextPlayerBuyBtnClick);

        EventHandler<MouseEvent> handleSelectedPieceListViewClick = event -> {
            String selectedName = ((ListView<String>) event.getSource()).getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                for (PieceType pieceType: PieceType.values()) {
                    if (pieceType.getName().equals(selectedName)) {
                        selectedPiece = pieceType;
                        selectedListView = (ListView<String>) event.getSource();
                        if (event.getSource().equals(piecesToBuyListView)) {
                            updateBuyViewPlayerStuff(game.getCurrentBuyer());
                        } else if (event.getSource().equals(ownedPiecesListView) && myScreenController.getCurrentScreen().getName().equals("playView")) {
                            updatePlayView();
                        }
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
                updateBuyView(game.getCurrentBuyer());
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

                if (!game.isTurnOngoing() && game.isPiecePlayable(selectedPiece, selectedRow, selectedCol, movement)) {
                    game.playPiece(game.getCurrentPlayer(), selectedPiece, selectedRow, selectedCol, movement);
                    updatePlayView();
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

        MyScreen endView = new MyScreen("endView");
        endView.addFXMLElement(endScreenAnchorPane);
        myScreenController.add(endView);

        myScreenController.activate("buyView");

        EventHandler<MouseEvent> startBtnClick = mouseEvent -> {
            selectedPiece = null;
            myScreenController.activate("playView");
            updatePlayView();
        };

        startBtn.setOnMouseClicked(startBtnClick);

        updateBuyView(game.getCurrentBuyer());
    }

    public void switchTurn() {
        if (game.isOver()) {
            activateEndScreen();
        }
        selectedPiece = null;
        updatePlayView();
    }

    private void updatePlayView() {
        game.update();
        updateOwnedPiecesListView(game.getCurrentPlayer());
        updatePlayPieceDescriptionTextArea();
        updatePlayViewPlayerNameLbl();
        updateTurnsLeftLbl();
    }

    private void updateTurnsLeftLbl() {
        turnsLeftLbl.setText("Turns left: " + game.getTurnsLeft());
    }

    private void activateEndScreen() {
        myScreenController.activate("endView");
        endScreenImageView.setImage(makeImg("src/main/resources/com/example/generaltemplate/img/end_screen/" + game.findWinner().getName() + ".png"));
    }

    private void updatePlayViewPlayerNameLbl() {
        playViewPlayerNameLbl.setText(game.getCurrentPlayer().getName() + "'s turn");
    }

    private void updatePlayPieceDescriptionTextArea() {
        if (selectedPiece != null) {
            playPieceDescriptionTextArea.setText(selectedPiece.getName() + ":\n" + selectedPiece.getDescription());
        }
    }

    private void updateBuyView(Player player) {
        updatePiecesToBuyListView();
        updateBuyViewPlayerStuff(player);
        updateBuyViewPlayerNameLbl();
    }

    private void updateBuyViewPlayerStuff(Player player) {
        updateMoneyLbl();
        updateOwnedPiecesListView(player);
        updateBuyPieceDescriptionTextArea();
    }

    private void updateBuyPieceDescriptionTextArea() {
        buyPieceDescriptionTextArea.clear();
        if (selectedPiece != null) {
            buyPieceDescriptionTextArea.setText(selectedPiece.getName() + "\n\nPrice: " + selectedPiece.getPrice() + "\n" + selectedPiece.getDescription());
        }
    }

    private void updateOwnedPiecesListView(Player player) {
        int selectedItem = 0;
        if (!ownedPiecesListView.getSelectionModel().getSelectedIndices().isEmpty()) {
            selectedItem = ownedPiecesListView.getSelectionModel().getSelectedIndices().get(ownedPiecesListView.getSelectionModel().getSelectedIndices().size()-1);
        }
        ownedPiecesListView.getItems().clear();
        for (PieceType piece: player.getPiecesOwned()) {
            ownedPiecesListView.getItems().add(piece.getName());
        }
        ownedPiecesListView.getSelectionModel().select(selectedItem);
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

    private void updateBuyViewPlayerNameLbl() {buyViewPlayerNameLbl.setText(game.getCurrentBuyer().getName() + " buy stuff");}
}