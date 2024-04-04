/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import eg.edu.alexu.csd.oop.game.GameObject;
import java.util.ArrayList;

public class ObjectsLoader {

    private static ObjectsLoader objectsLoader = null;
    private GameObjectFactory factory = GameObjectFactory.getInstance();
    private String[] movingNames = {"plate", "bomb","pot"};

    private ObjectsLoader() {
    }

    public static ObjectsLoader getInstance() {
        if (objectsLoader == null) {
            objectsLoader =  new ObjectsLoader();
        }
        return objectsLoader;
    }

    public ArrayList<GameObject> getConstantShapes(int width, int height,int mode) {
        ArrayList<GameObject> constant = new ArrayList<>();
        constant.add(factory.getGameObject(width, height, "background",mode));
        return constant;
    }

    public ArrayList<GameObject> getMovingShapes(int width, int height, int max) {
        ArrayList<GameObject> moving = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            moving.add(factory.getGameObject(width, height, movingNames[(int) (Math.random() * movingNames.length)],0));
        }
        return moving;
    }

    public ArrayList<GameObject> getControlShapes(int width, int height,int mode) {
        ArrayList<GameObject> control = new ArrayList<>();
        control.add(factory.getGameObject(width, height, "clown",mode));
        return control;
    }
}
