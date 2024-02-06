package com.example.generaltemplate;

import java.util.ArrayList;

public class Player {
    private String name;
    private Colors color;
    private ArrayList<PieceType> piecesOwned;
    private int money;

    public Player(String name, Colors color, int money) {
        this.name = name;
        this.color = color;
        this.money = money;
        piecesOwned = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Colors getColor() {
        return color;
    }

    public ArrayList<PieceType> getPiecesOwned() {
        return piecesOwned;
    }
}
