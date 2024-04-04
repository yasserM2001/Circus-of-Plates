package controller;

import java.util.LinkedList;
import java.util.List;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import model.AbstractGameObject;
import model.Bomb;
import model.Clown;
import model.MoveHorizontally;
import model.ObjectsLoader;
import model.Sound;
import model.Strategy;

public class CircusWorld implements World {

    private static final int MAX_TIME = 1 * 60 * 1000; // 1 minute
    private int noOfLives;

    private static final int DEFAULT_SEPEED = 10;
    private static final int DEFAULT_CONTROL_SEPEED = 20;

    private final long startTime;
    private int score;

    private final int screenWidth;
    private final int screeneHeight;

    private List<GameObject> constant;
    private List<GameObject> moving;
    private List<GameObject> control;

    private List<GameObject> leftHand;
    private List<GameObject> rightHand;

    private ObjectsLoader loader;
    private Strategy difficulty;
    private Sound sound ;
    private int mode;//0 normal , 1 christmas

    public CircusWorld(Strategy s, int width, int height, int mode) {
        this.sound=Sound.getInstance(mode);
        this.sound.startTheme();
        this.mode = mode;
        this.difficulty = s;
        this.noOfLives = difficulty.getNoOfLives();
        this.screenWidth = width;
        this.screeneHeight = height;
        this.startTime = System.currentTimeMillis();
        this.score = 0;
        loader = ObjectsLoader.getInstance();

        this.constant = new LinkedList<>();
        this.moving = new LinkedList<>();
        this.control = new LinkedList<>();

        this.leftHand = new LinkedList<>();
        this.rightHand = new LinkedList<>();
        createGameObject();
    }

    private void createGameObject() {
        //Add contant objects
        constant = loader.getConstantShapes(screenWidth, screeneHeight, mode);
        //Add moving Objects
        moving = loader.getMovingShapes(screenWidth, screeneHeight, difficulty.noOfFallingShapes());
        //Add control objects
        control = loader.getControlShapes(screenWidth, screeneHeight, mode);
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return this.constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return this.moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return this.control;
    }

    @Override
    public int getWidth() {
        return this.screenWidth;
    }

    @Override
    public int getHeight() {
        return this.screeneHeight;
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth())
                && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight() / 2 + o2.getHeight() / 2);
    }

    private boolean objectIntersectLeft(GameObject o) {
        GameObject clown = control.get(0);
        return (Math.abs(clown.getX() - o.getX()) <= o.getWidth() - 10
                && (o.getY() == clown.getY() - o.getHeight() || (o.getY() >= clown.getY() - o.getHeight() && o.getY() <= clown.getY() - o.getHeight() + 2)));//height
    }
//return (Math.abs(clown.getX() - o.getX()) <= o.getWidth() - 10 && o.getY() == clown.getY() - o.getHeight());

    private boolean objectIntersectRight(GameObject o) {
        GameObject clown = control.get(0);
        int x = clown.getX() + 135;
        return (Math.abs(x - o.getX()) <= o.getWidth() - 10
                && (o.getY() == clown.getY() - o.getHeight() || (o.getY() >= clown.getY() - o.getHeight() && o.getY() <= clown.getY() - o.getHeight() + 2)));//height
    }

    public void checkScore(List<GameObject> hand) {
        if (hand.size() >= 3) {
            AbstractGameObject z1 = (AbstractGameObject) hand.get(hand.size() - 1);
            AbstractGameObject z2 = (AbstractGameObject) hand.get(hand.size() - 2);
            AbstractGameObject z3 = (AbstractGameObject) hand.get(hand.size() - 3);
            if (z1.getColor().equals(z2.getColor()) && z2.getColor().equals(z3.getColor())) {
                hand.remove(z1);
                hand.remove(z2);
                hand.remove(z3);
                control.remove(z1);
                control.remove(z2);
                control.remove(z3);
                score = score + difficulty.scoreIncrease();
            }
        }
    }

    @Override
    public boolean refresh() {

        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        Clown clown = (Clown) control.get(0);

        for (int i = 0; i < moving.size(); i++) {
            GameObject o = moving.get(i);
            o.setY((o.getY() + difficulty.getMovingSpeed()));//move Down
            if (o.getY() >= getHeight()) {
                o.setY(-1 * (int) (Math.random() * getHeight()));
                o.setX((int) (Math.random() * getWidth()));
            }
            //intersect left hand empty
            if (leftHand.isEmpty()) {
                if (objectIntersectLeft(o)) {
                    moving.remove(o);
                    if (o instanceof Bomb) {
                        noOfLives--;
                        sound.boom();
                    } else {
                        sound.caught();
                        ((AbstractGameObject) o).setState(new MoveHorizontally(o.getX(), o.getY()));
                        o.setX(clown.getX() - 5);
                        control.add(o);
                        leftHand.add(o);
                    }
                    GameObject toAdd = loader.getMovingShapes(screenWidth, screeneHeight, 1).get(0);
                    moving.add(toAdd);
                }
            } else {
                if (intersect(leftHand.get(leftHand.size() - 1), o)) {
                    moving.remove(o);
                    if (o instanceof Bomb) {
                        noOfLives--;
                        sound.boom();
                    } else {
                        sound.caught();
                        ((AbstractGameObject) o).setState(new MoveHorizontally(o.getX(), o.getY()));
                        o.setX(leftHand.get(leftHand.size() - 1).getX());
                        o.setY(leftHand.get(leftHand.size() - 1).getY() - o.getHeight());
                        control.add(o);
                        leftHand.add(o);
                    }
                    GameObject toAdd = loader.getMovingShapes(screenWidth, screeneHeight, 1).get(0);
                    moving.add(toAdd);
                }
            }
            checkScore(leftHand);
            if (rightHand.isEmpty()) {
                if (objectIntersectRight(o)) {
                    moving.remove(o);
                    if (o instanceof Bomb) {
                        noOfLives--;
                        sound.boom();
                    } else {
                        sound.caught();
                        ((AbstractGameObject) o).setState(new MoveHorizontally(o.getX(), o.getY()));
                        o.setX(clown.getX() + 130);
                        control.add(o);
                        rightHand.add(o);
                    }
                    GameObject toAdd = loader.getMovingShapes(screenWidth, screeneHeight, 1).get(0);
                    moving.add(toAdd);
                }
            } else {
                if (intersect(o, rightHand.get(rightHand.size() - 1))) {
                    moving.remove(o);
                    if (o instanceof Bomb) {
                        noOfLives--;
                        sound.boom();
                    } else {
                        sound.caught();
                        ((AbstractGameObject) o).setState(new MoveHorizontally(o.getX(), o.getY()));
                        o.setX(rightHand.get(rightHand.size() - 1).getX());
                        o.setY(rightHand.get(rightHand.size() - 1).getY() - o.getHeight());
                        control.add(o);
                        rightHand.add(o);
                    }
                    GameObject toAdd = loader.getMovingShapes(screenWidth, screeneHeight, 1).get(0);
                    moving.add(toAdd);
                }
            }
            checkScore(rightHand);
        }

        if (clown.getX() == 0 || clown.getX() + clown.getWidth() == this.screenWidth) {
            for (int j = 0; j < leftHand.size(); j++) {
                GameObject l = leftHand.get(j);
                l.setX(clown.getX() - 5);
            }
            for (int j = 0; j < rightHand.size(); j++) {
                GameObject r = rightHand.get(j);
                r.setX(clown.getX() + 130);
            }
        }
        if (noOfLives == 0) {
            sound.stopTheme();
            moving.clear();
            return false;
        }
        if(timeout){
            sound.stopTheme();
            moving.clear();
        }

        return !timeout;
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000) + "   |   Lives=" + noOfLives;// update status
    }

    @Override
    public int getSpeed() {
        return DEFAULT_SEPEED;
    }

    @Override
    public int getControlSpeed() {
        return DEFAULT_CONTROL_SEPEED;
    }
    
    public Sound getSound() {
        return sound;
    }
}
