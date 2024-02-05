package com.example.generaltemplate;

import javafx.scene.image.Image;

import static com.example.generaltemplate.GameController.makeImg;

public class Piece {
    private final PieceType pieceType;
    private Colors color;
    private Image image;
    private Movement movement;
    private boolean alreadyMoved;

    public Piece(PieceType pieceType, Colors color, Movement movement) {
        this.pieceType = pieceType;
        this.color = color;
        this.image = makeImg("src/main/resources/com/example/generaltemplate/img/" + color.getName() +"/"+ pieceType.getName().toLowerCase() + " piece.png");
        this.movement = movement;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Colors getColor() {
        return color;
    }

    public Image getImage() {
        return image;
    }

    public void setColor(Colors color) {
        this.color = color;
        image = makeImg("src/main/resources/com/example/generaltemplate/img/" + this.color.getName() +"/"+ pieceType.getName().toLowerCase() + " piece.png");
    }

    public boolean isAlreadyMoved() {
        return alreadyMoved;
    }

    public void setAlreadyMoved(boolean alreadyMoved) {
        this.alreadyMoved = alreadyMoved;
    }
}
