package com.example.generaltemplate;

import static com.example.generaltemplate.Game.*;

public enum PieceType {

    BASIC("Basic", 5, "A normal piece"),
    EXPLODER("Exploder", 10, "Explodes all pieces in a " + EXPLODER_RANGE + " radius"),
    CHANGER("Changer", 10, "Changes surrounding pieces in a " + CHANGER_RANGE + " radius to player's color"),
    HORIZONTAL_SCORER("Horizontal scorer", 25, "Changes all pieces in a row to player's color"),
    SHIELD("Shield", 10, "Protects certain pieces in a " + SHIELD_RANGE + " radius from changes"),
    SUMMONER("Summoner", 25, "Summons new piece every turn in a " + SUMMONER_RANGE + " radius"),
    WALL("Wall", 5, "Stationary structure that can be placed anywhere. Doesn't count towards total points.");
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
