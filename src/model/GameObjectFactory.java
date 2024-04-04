/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import eg.edu.alexu.csd.oop.game.GameObject;

/**
 *
 * @author pc
 */
public class GameObjectFactory {

    private static GameObjectFactory factory = null;
    private String[] plateNames = {"/plate1.png", "/plate2.png"};
    private String[] potNames = {"/pot1.png", "/pot2.png"};

    private GameObjectFactory() {
    }

    public static GameObjectFactory getInstance() {
        if (factory == null) {
            factory = new GameObjectFactory();
        }
        return factory;
    }

    public GameObject getGameObject(int width, int height, String s, int mode) {
        GameObject gameObject = null;
        if (s.equalsIgnoreCase("clown")) {
            int x = width / 3;
            int y = (int) (height * 0.65);
            if (mode == 0) {
                gameObject = new Clown(new MoveHorizontally(x, y), "/clown.png");
            } else if (mode == 1) {
                gameObject = new Clown(new MoveHorizontally(x, y), "/santa.png");
            }
        } else if (s.equalsIgnoreCase("background")) {
            if (mode == 0) {
                gameObject = new BackGround(new NotMove(0, 0), "/background.png");
            } else if (mode == 1) {
                gameObject = new BackGround(new NotMove(0, 0), "/background2.png");
            }
        } else if (s.equalsIgnoreCase("plate")) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height / 4);
            String image = plateNames[(int) (Math.random() * plateNames.length)];
            gameObject = new Plate(new MoveVertically(x, y), image);
        } else if (s.equalsIgnoreCase("pot")) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height / 4);
            String image = potNames[(int) (Math.random() * potNames.length)];
            gameObject = new Pot(new MoveVertically(x, y), image);
        } else if (s.equalsIgnoreCase("bomb")) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height / 4);
            gameObject = new Bomb(new MoveVertically(x, y), "/bomb.png");
        }
        return gameObject;
    }
}
