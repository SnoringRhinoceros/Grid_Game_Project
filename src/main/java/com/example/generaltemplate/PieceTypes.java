package com.example.generaltemplate;

import javafx.scene.image.Image;

public enum PieceTypes {
    BASIC("basic", "");

    private String name;
    private Image img;

    PieceTypes(String name, String imgPath) {
        this.name = name;
        this.img = new Image(imgPath);
    }

    public String getName() {
        return name;
    }

    public Image getImg() {
        return img;
    }
}
