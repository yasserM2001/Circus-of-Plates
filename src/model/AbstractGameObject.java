/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import eg.edu.alexu.csd.oop.game.GameObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author pc
 */
public abstract class AbstractGameObject implements GameObject {

    private static final int MAX_MSTATE = 1;
    // an array of sprite images that are drawn sequentially
    private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
    private String color;
    private State state;
    private boolean visible;
    private String imageName;
    private static final HashMap gameObjectsMap = new HashMap();

    public AbstractGameObject(State state, String imageName) {
        if (imageName.contains("1")) {
            color = "blue";
        } else if (imageName.contains("2")) {
            color = "red";
        }
        this.state = state;
        visible = true;
        this.imageName = imageName;
        BufferedImage b = (BufferedImage) gameObjectsMap.get(imageName);
        if (b == null) {
            try {
                System.out.println("new");
                System.out.println(imageName);
                spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(imageName));
                gameObjectsMap.put(imageName, spriteImages[0]);
            } catch (IOException e) {
            }
        } else {
            spriteImages[0] = b;
            System.out.println("Same");
            System.out.println(imageName);
        }
        System.out.println(spriteImages[0].hashCode());
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        this.state.setX(state.getX());
        this.state.setY(state.getY());
    }

    @Override
    public int getX() {
        return state.getX();
    }

    @Override
    public void setX(int x) {
        state.setX(x);
    }

    @Override
    public int getY() {
        return state.getY();
    }

    @Override
    public void setY(int y) {
        state.setY(y);
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getColor() {
        return color;
    }

}
