package com.example.generaltemplate;

import javafx.scene.image.Image;

import static com.example.generaltemplate.GameController.makeImg;

public enum PieceType {
    BASIC("basic", "src/main/resources/com/example/generaltemplate/img/red/basic_piece.png");

    private String name;
    private Image img;


    PieceType(String name, String imgPath) {
        this.name = name;
        this.img = makeImg(imgPath);
    }

    public String getName() {
        return name;
    }

    public Image getImg() {
        return img;
    }

}
