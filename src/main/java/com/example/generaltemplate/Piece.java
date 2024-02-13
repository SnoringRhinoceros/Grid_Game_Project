package com.example.generaltemplate;

import javafx.scene.image.Image;

import static com.example.generaltemplate.GameController.makeImg;

public class Piece extends SolidObject{
    private final PieceType pieceType;
    private Colors color;
    private Movement movement;
    private boolean alreadyMoved;
    private int bounceNum = 0;

    public Piece(PieceType pieceType, Colors color, Movement movement) {
        super("src/main/resources/com/example/generaltemplate/img/" + color.getName() +"/"+ pieceType.getName().toLowerCase() + " piece.png");
        this.pieceType = pieceType;
        this.color = color;
        this.movement = movement;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        if (movement.equals(Movement.STILL)) {
            clearBounceNum();
        }
        this.movement = movement;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
        setImg("src/main/resources/com/example/generaltemplate/img/" + this.color.getName() +"/"+ pieceType.getName().toLowerCase() + " piece.png");
    }

    public boolean isNotAlreadyMoved() {
        return !alreadyMoved;
    }

    public void setAlreadyMoved(boolean alreadyMoved) {
        this.alreadyMoved = alreadyMoved;
    }

    public int getBounceNum() {
        return bounceNum;
    }

    public void incrementBounceNum() {
        bounceNum++;
    }

    private void clearBounceNum() {
        bounceNum = 0;
    }
}
