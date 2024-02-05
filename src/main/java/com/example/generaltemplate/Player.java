package com.example.generaltemplate;

import java.util.ArrayList;

public class Player {
    private String name;
    private Colors color;
    private ArrayList<PieceType> piecesOwned;

    public Player(String name, Colors color) {
        this.name = name;
        this.color = color;
        piecesOwned = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Colors getColor() {
        return color;
    }

    public ArrayList<PieceType> getPiecesOwned() {
        return piecesOwned;
    }
}
