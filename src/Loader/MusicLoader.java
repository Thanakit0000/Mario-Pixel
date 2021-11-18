package Loader;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicLoader {

    public static File theme;
    public static File theme2;
    public static File theme3;
    public static File theme4;
    public static File theme5;
    public static File end;
    public static File jump;
    public static File die;
    public static File getCoin;
    public static File GoombaDie;
    public static File getMushroom;
    public static File bump;
    public static File fireball;
    public static File trampoline;
    public static File pipe;
    public static File title;
    public static File koopa;
    public static File koopaDie;
    public static File koopaHit;

    private float value;
    private int frame;

    Clip clip_loop;
    Clip clip_play;
    
    public int count = 0;

    public void load() {

        theme = new File("src/Music/theme.wav");
        theme2 = new File("src/Music/nextLv.wav");
        theme3 = new File("src/Music/theme2.wav");
        theme5 = new File("src/Music/theme3.wav");
        end = new File("src/Music/end.wav");
        jump = new File("src/Music/jump.wav");
        die = new File("src/Music/die.wav");
        getCoin = new File("src/Music/getCoin.wav");
        GoombaDie = new File("src/Music/Goomba_die.wav");
        getMushroom = new File("src/Music/LevelUP.wav");
        bump = new File("src/Music/bump.wav");
        fireball = new File("src/Music/fireball.wav");
        trampoline = new File("src/Music/trampoline.wav");
        pipe = new File("src/Music/pipe.wav");
        title = new File("src/Music/Title_sound.wav");
        koopa = new File("src/Music/koopa.wav");
        koopaDie = new File("src/Music/koopaDie.wav");
        koopaHit = new File("src/Music/BossHit.wav");

        this.value = -30f;
    }
    
    public void play(File file) {
        try {
            clip_play = AudioSystem.getClip();
            clip_play.open(AudioSystem.getAudioInputStream(file));
            FloatControl controller = (FloatControl) clip_play.getControl(FloatControl.Type.MASTER_GAIN);
            controller.setValue(value); //sound louder 
            clip_play.start();
        } catch (Exception e) {}
    }

    public void loop(File file) {
        try {
            count++;
            clip_loop = AudioSystem.getClip();
            clip_loop.open(AudioSystem.getAudioInputStream(file));
            clip_loop.setFramePosition(frame);
            FloatControl controller = (FloatControl) clip_loop.getControl(FloatControl.Type.MASTER_GAIN);
            controller.setValue(value); //sound louder 
            clip_loop.loop(clip_loop.LOOP_CONTINUOUSLY);  //loop music
            clip_loop.start();
        } catch (Exception e) {}
    }

    public void stopLoop() {
        frame = clip_loop.getFramePosition();
        clip_loop.stop();
    }
}
