package com.example.generaltemplate;

public enum StructureType {
    RICOCHET("ricochet");

    private StructureOrientation orientation;

    private final String name;

    StructureType(String name) {
        this.name = name;
    }

    public StructureOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(StructureOrientation orientation) {
        this.orientation = orientation;
    }

    public String getName() {
        return name;
    }
}
