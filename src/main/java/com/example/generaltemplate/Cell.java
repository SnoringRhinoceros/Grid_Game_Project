package com.example.generaltemplate;

public class Cell {
    private TerrainTypes terrainType;

    public Cell(TerrainTypes terrainType) {
        this.terrainType = terrainType;
    }

    public TerrainTypes getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainTypes terrainType) {
        this.terrainType = terrainType;
    }
}
