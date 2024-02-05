package com.example.generaltemplate;

public enum Colors {
    RED("red"), BLUE("blue");

    private final String name;

    Colors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
