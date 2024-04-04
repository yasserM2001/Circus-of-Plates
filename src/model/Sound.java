/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author pc
 */
public class Sound {

    private static Sound sound = null;
    private Clip music;
    private Clip gotcha;
    private static int mode;

    private Sound() {
    }

    public static Sound getInstance(int mode) {
        Sound.mode=mode;
        if (sound == null) {
            sound = new Sound();
        }
        return sound;
    }

    public void startTheme() {
        try {
            URL url;
            if (mode == 0) {
                url = Sound.class.getResource("/Theme.wav");
            } else {
                url = Sound.class.getResource("/MerryChristmas.wav");
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            music = AudioSystem.getClip();
            music.open(audioIn);
            music.loop(5);
            music.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopTheme() {
        if (music != null) {
            music.stop();
        }
    }

    public void boom() {
        try {
            URL url = Sound.class.getResource("/Boom.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip boom = AudioSystem.getClip();
            boom.open(audioIn);
            FloatControl gainControl = (FloatControl) boom.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue((float) +6.0206);
            boom.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void caught() {
        try {
            URL url = Sound.class.getResource("/Caught.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            gotcha = AudioSystem.getClip();
            gotcha.open(audioIn);
            gotcha.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
