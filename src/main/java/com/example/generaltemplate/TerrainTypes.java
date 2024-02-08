package com.example.generaltemplate;

public enum TerrainTypes {
    NONE("-fx-border-color: #000000;");

    private final String CSS;
    TerrainTypes(String CS) {
        this.CSS = CS;
    }

    public String getCSS() {
        return CSS;
    }
}
