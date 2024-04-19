/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package multithreadapplicationdemo;

/**
 *
 * @author Uesr
 */

public class MultiThreading {

    public static void main(String[] args) {
        playNotes();
    }

    private static void playNotes() {
        Object lock = new Object();
        Thread thread1 = new Thread(new MusicThread(lock, "Thread 1",
                "C:/Users/51102/Music/sound/do.wav",
                "C:/Users/51102/Music/sound/mi.wav",
                "C:/Users/51102/Music/sound/sol.wav",
                "C:/Users/51102/Music/sound/si.wav",
                "C:/Users/51102/Music/sound/do-octave.wav"));
        Thread thread2 = new Thread(new MusicThread(lock, "Thread 2",
                "C:/Users/51102/Music/sound/re.wav",
                "C:/Users/51102/Music/sound/fa.wav",
                "C:/Users/51102/Music/sound/la.wav",
                "C:/Users/51102/Music/sound/do-octave.wav"));

        thread1.start();
     
        thread2.start();
    }
}

class MusicThread implements Runnable {

    private final Object lock;
    private final String threadName;
    private final String[] tones;

    public MusicThread(Object lock, String threadName, String... tones) {
        this.lock = lock;
        this.threadName = threadName;
        this.tones = tones;
    }

    @Override
    public void run() {
        for (String tone : tones) {
            synchronized (lock) {
                // Play the tone
                playTone(tone);
                // Notify the other thread to proceed
                lock.notify();
                try {
                    // Wait for the other thread to finish playing its tone
                    if (!tone.equals(tones[tones.length - 1])) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void playTone(String tone) {
        FilePlayer player = new FilePlayer();
        System.out.println("Thread: " + threadName + " - Playing Note: " + tone);
        player.play(tone);
    }
}