/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multithreadapplicationdemo;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author user
 */
public class FilePlayer {
    
    private boolean isThread1Turn = true;
        public void play(String filePath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
                        Thread.sleep(clip.getMicrosecondLength() / 1000);

		} catch (Exception e) {
			System.out.println("Error with playing sound.");
			e.printStackTrace();
		}

	}
          public synchronized void playDoOctave() {
        if (isThread1Turn) {
            play("do-octave");
            isThread1Turn = false;
            notify(); // Notify the waiting thread (Thread2)
        }
    }

    public synchronized void waitForTurn() {
        while (isThread1Turn) {
            try {
                wait(); // Wait until it's Thread1's turn
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void resetTurn() {
        isThread1Turn = true;
    }
}
