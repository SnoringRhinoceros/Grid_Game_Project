package com.example.generaltemplate;

import javafx.scene.image.Image;

import static com.example.generaltemplate.GameController.makeImg;

public class Piece {
    private final PieceType pieceType;
    private final Colors color;
    private final Image image;
    private Movement movement;

    public Piece(PieceType pieceType, Colors color, Movement movement) {
        this.pieceType = pieceType;
        this.color = color;
        this.image = makeImg("src/main/resources/com/example/generaltemplate/img/" + color.getName() +"/"+ pieceType.getName() + ".png");
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
}
