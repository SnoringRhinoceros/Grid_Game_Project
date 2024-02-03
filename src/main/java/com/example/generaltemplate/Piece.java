package com.example.generaltemplate;

import javafx.scene.image.Image;

import static com.example.generaltemplate.GameController.makeImg;

public enum Piece {
    BASIC("basic", "src/main/resources/com/example/generaltemplate/img/red/basic_piece.png");

    private String name;
    private Image img;
    private Movement movement;
    private boolean movedAlready;

    Piece(String name, String imgPath) {
        this.name = name;
        this.img = makeImg(imgPath);
        this.movement = Movement.STILL;
    }

    public String getName() {
        return name;
    }

    public Image getImg() {
        return img;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public boolean isMovedAlready() {
        return movedAlready;
    }

    public void setMovedAlready(boolean movedAlready) {
        this.movedAlready = movedAlready;
    }
}
