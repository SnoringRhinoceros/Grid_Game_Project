package com.example.generaltemplate;

public enum PieceType {

    BASIC("Basic"), EXPLODER("Exploder"), CHANGER("Changer");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
