package com.example.generaltemplate;

public enum BorderTypes {
    NONE("-fx-border-color: #000000;"), PLAYABLE("-fx-border-color: #d22b2b;"), UNPLAYABLE("-fx-border-color: #808080");

    private final String CSS;
    BorderTypes(String CS) {
        this.CSS = CS;
    }

    public String getCSS() {
        return CSS;
    }
}
