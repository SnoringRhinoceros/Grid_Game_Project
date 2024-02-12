package com.example.generaltemplate;

public class Cell {
    private BorderTypes borderType;
    private SolidObject solidObject;
    private boolean shielded;

    public Cell(BorderTypes borderType) {
        this.borderType = borderType;
    }

    public BorderTypes getBorderType() {
        return borderType;
    }

    public void setBorderType(BorderTypes borderType) {
        this.borderType = borderType;
    }

    public SolidObject getSolidObject() {return solidObject;}

    public void setSolidObject(SolidObject solidObject) {
        this.solidObject = solidObject;
    }

    public boolean hasPiece() {
        return solidObject != null && solidObject.getClass().equals(Piece.class);
    }

    public boolean hasSolidObject() {
        return solidObject != null;
    }

    public boolean hasStructure() {return solidObject != null && solidObject.getClass().equals(Structure.class);}

    public Piece getPiece() {
        return (Piece) solidObject;
    }

    public boolean isNotShielded() {
        return !shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }
}
