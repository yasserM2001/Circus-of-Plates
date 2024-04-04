package model;

public class EasyDifficulty implements Strategy {

    @Override
    public int getMovingSpeed() {
        return 1;
    }

    @Override
    public int getNoOfLives() {
        return 5;
    }

    @Override
    public int noOfFallingShapes() {
        return 10;
    }

    @Override
    public int scoreIncrease() {
        return 15;
    }

}
