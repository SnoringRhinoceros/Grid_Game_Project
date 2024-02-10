package com.example.generaltemplate;

public class Cell {
    private BorderTypes borderType;
    private Piece piece;
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

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public boolean isNotShielded() {
        return !shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }
}
