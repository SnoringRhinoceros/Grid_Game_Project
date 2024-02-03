package com.example.generaltemplate;

import javafx.scene.Node;

import java.util.ArrayList;

public class MyScreen {
    private final String name;
    private final ArrayList<Node> FXMLElements = new ArrayList<>();

    public MyScreen(String name) {
        this.name = name;
    }

    public void addFXMLElement(Node element) {
        FXMLElements.add(element);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getFXMLElements() {
        return FXMLElements;
    }
}
