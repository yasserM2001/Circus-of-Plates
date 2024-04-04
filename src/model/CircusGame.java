/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;


import controller.CircusWorld;
import controller.CircusGameController;

/**
 *
 * @author pc
 */
public class CircusGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CircusGameController gameController = new CircusGameController(() -> new CircusWorld(new EasyDifficulty(), 850, 600,0));
        gameController.start();
    }
}
