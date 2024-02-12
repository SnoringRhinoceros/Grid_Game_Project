package com.example.generaltemplate;

import javafx.scene.image.Image;

import static com.example.generaltemplate.GameController.makeImg;

public class SolidObject {
    private Image img;

    public SolidObject(String imgPath) {
        this.img = makeImg(imgPath);
    }

    public Image getImg() {
        return img;
    }

    public void setImg(String imgPath) {
        this.img = makeImg(imgPath);
    }
}
