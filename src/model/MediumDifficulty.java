/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pc
 */
public class MediumDifficulty implements Strategy {

    @Override
    public int getMovingSpeed() {
        return 2;
    }

    @Override
    public int getNoOfLives() {
        return 3;
    }

    @Override
    public int noOfFallingShapes() {
        return 15;
    }

    @Override
    public int scoreIncrease() {
        return 10;
    }

}
