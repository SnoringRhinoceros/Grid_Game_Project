package com.example.generaltemplate;

import javafx.scene.Node;

import java.util.ArrayList;

public class MyScreenController {
    private final ArrayList<MyScreen> myScreens = new ArrayList<>();
    private MyScreen currentMyScreen;

    public void add(MyScreen MyScreen) {
        myScreens.add(MyScreen);
    }

    public void activate(String name) {
        for (MyScreen MyScreen : myScreens) {
            if (MyScreen.getName().equals(name)) {
                clearAll();
                ArrayList<Node> nodes = MyScreen.getFXMLElements();
                for (Node node: nodes) {
                    node.setVisible(true);
                }
                currentMyScreen = MyScreen;
            }
        }
    }

    private void clearAll() {
        for (MyScreen MyScreen : myScreens) {
            ArrayList<Node> nodes = MyScreen.getFXMLElements();
            for (Node node: nodes) {
                node.setVisible(false);
            }
        }
    }

    public MyScreen getCurrentScreen() {
        return currentMyScreen;
    }
}
