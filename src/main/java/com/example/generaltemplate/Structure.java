package com.example.generaltemplate;

public class Structure extends SolidObject{
    private final StructureType structureType;
    private final int slope;

    public Structure(StructureType structureType, int slope) {
        super("src/main/resources/com/example/generaltemplate/img/structures/" + structureType.getName() + "/" + slope + ".png");
        this.structureType = structureType;
        this.slope = slope;
    }

    public int getSlope() {
        return slope;
    }

    public StructureType getStructureType() {
        return structureType;
    }
}
