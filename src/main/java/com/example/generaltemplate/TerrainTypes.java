package com.example.generaltemplate;

public enum TerrainTypes {
    NONE("");

    private final String CSS;
    TerrainTypes(String CS) {
        this.CSS = CS;
    }

    public String getCSS() {
        return CSS;
    }
}
