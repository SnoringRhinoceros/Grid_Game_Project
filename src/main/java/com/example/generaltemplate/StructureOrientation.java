package com.example.generaltemplate;

public enum StructureOrientation {
    UP("up"),
    RIGHT("right"),
    DOWN("down"),
    LEFT("left");

    private final String name;

    StructureOrientation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
