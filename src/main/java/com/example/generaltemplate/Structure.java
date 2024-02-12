package com.example.generaltemplate;

import static com.example.generaltemplate.GameController.makeImg;

public class Structure extends SolidObject{
    private final StructureType structureType;

    public Structure(StructureType structureType) {
        super("src/main/resources/com/example/generaltemplate/img/structures/" + structureType.getName() + "/right.png");
        this.structureType = structureType;
    }

    public StructureType getStructureType() {
        return structureType;
    }
}
