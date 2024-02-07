package com.example.generaltemplate;

public enum PieceType {

    BASIC("Basic", 5, "A normal piece"),
    EXPLODER("Exploder", 10, "Explodes all pieces in a " + " radius"),
    CHANGER("Changer", 10, "Changes surrounding pieces in a " + " radius to player's color"),
    HORIZONTAL_SCORER("Horizontal scorer", 25, "Changes all pieces in a row to player's color"),
    SHIELD("Shield", 10, "Protects all pieces in a " + " radius from changes");
    private final String name;
    private final int price;
    private final String description;

    PieceType(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
