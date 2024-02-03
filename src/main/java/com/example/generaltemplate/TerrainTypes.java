package com.example.generaltemplate;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public enum TerrainTypes {
    PLAINS("src/main/resources/com/example/generaltemplate/img/plains.png");

    private Image img;
    TerrainTypes(String imgPath) {
        try {
            this.img = new Image(new FileInputStream(imgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Image getImg() {
        return img;
    }
}
