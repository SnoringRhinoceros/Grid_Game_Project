package com.example.generaltemplate;

public enum BorderTypes {
    NONE("-fx-border-color: #000000;"), HOVERED("-fx-border-color: #d22b2b;");

    private final String CSS;
    BorderTypes(String CS) {
        this.CSS = CS;
    }

    public String getCSS() {
        return CSS;
    }
}
