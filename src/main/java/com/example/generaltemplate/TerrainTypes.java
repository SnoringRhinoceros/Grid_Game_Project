package com.example.generaltemplate;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.generaltemplate.GameController.makeImage;

public enum TerrainTypes {
    PLAINS("src/main/resources/com/example/generaltemplate/img/plains.png");

    private Image img;
    TerrainTypes(String imgPath) {
        this.img = makeImage(imgPath);
    }

    public Image getImg() {
        return img;
    }
}
