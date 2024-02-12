package com.example.generaltemplate;

public enum StructureType {
    RICOCHET("ricochet");

    private final String name;

    StructureType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
