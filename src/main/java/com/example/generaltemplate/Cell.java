package com.example.generaltemplate;

public class Cell {
    private TerrainTypes terrainType;
    private Piece piece;

    public Cell(TerrainTypes terrainType) {
        this.terrainType = terrainType;
    }

    public TerrainTypes getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainTypes terrainType) {
        this.terrainType = terrainType;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }
}
