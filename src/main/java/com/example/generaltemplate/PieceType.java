package com.example.generaltemplate;

public enum PieceType {

    BASIC("basic piece");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
