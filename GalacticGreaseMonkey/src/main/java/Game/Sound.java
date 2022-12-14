package Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[20];

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/choose.wav");
        soundURL[1] = getClass().getResource("/sound/diamond.wav");
        soundURL[2] = getClass().getResource("/sound/defeat.wav");
        soundURL[3] = getClass().getResource("/sound/gameSound.wav");
        soundURL[4] = getClass().getResource("/sound/gear.wav");
        soundURL[5] = getClass().getResource("/sound/hitAlien.wav");
        soundURL[6] = getClass().getResource("/sound/hitPlayer.wav");
        soundURL[7] = getClass().getResource("/sound/projectile.wav");
        soundURL[8] = getClass().getResource("/sound/victory.wav");
        soundURL[9] = getClass().getResource("/sound/start.wav");
        soundURL[10] = getClass().getResource("/sound/quit.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
        }
    }

    public void play(){ clip.start(); }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop ();
    }
}
