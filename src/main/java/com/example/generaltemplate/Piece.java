package com.example.generaltemplate;

public class Piece {
    private final PieceType pieceType;
    private Movement movement;
    public Piece(PieceType pieceType, Movement movement) {
        this.pieceType = pieceType;
        this.movement = movement;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
