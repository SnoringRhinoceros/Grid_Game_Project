package com.example.generaltemplate;

public enum BorderTypes {
    NONE("-fx-border-color: #000000;");

    private final String CSS;
    BorderTypes(String CS) {
        this.CSS = CS;
    }

    public String getCSS() {
        return CSS;
    }
}
