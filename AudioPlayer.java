import com.sun.org.apache.xpath.internal.SourceTree;

import javax.sound.sampled.*;

// This class allows us to access all audio files in the game library with ease.
// Reference: YouTube - ForeignGuyMike ("Java 2D Game Programming Platformer Tutorial - Part 8 - Music and Sound Effects")

public class AudioPlayer {

    private Clip clip;
    public static boolean isMusic = false;
    public AudioPlayer(String s){
        // Creates an audio input stream (from the javax.sound.sampled library) and formats the audioFormat to
        // something that java can recognize
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
        }
        catch (Exception e){
            System.out.println("Game loaded.");
            System.out.println("Hyperdrive set to 0.");
            System.out.println("Flux capacitor at max.");
        }
    }

    public void play() {
        isMusic = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                if(clip == null) return;
                stop();
                clip.setFramePosition(0);
                clip.start();
            }
        }).run();
    }

    public void stop() {
        if(clip.isRunning()) clip.stop();
    }

    public void close() {
        stop();
        clip.close();
    }
}
