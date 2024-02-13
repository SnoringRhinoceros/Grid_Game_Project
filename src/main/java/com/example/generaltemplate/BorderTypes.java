package com.example.generaltemplate;

public enum BorderTypes {
    NONE("-fx-border-color: #000000;"), HOVERED("-fx-border-color: #ADD8E6;");

    private final String CSS;
    BorderTypes(String CS) {
        this.CSS = CS;
    }

    public String getCSS() {
        return CSS;
    }
}
