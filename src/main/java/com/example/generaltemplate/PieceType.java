package com.example.generaltemplate;

public enum PieceType {

    BASIC("Basic", 5), EXPLODER("Exploder", 10), CHANGER("Changer", 10), HORIZONTAL_SCORER("Horizontal scorer", 25), SHIELD("Shield", 10);
    private final String name;
    private final int price;

    PieceType(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
