package hu.uni.miskolc.u678mf.musicplayer;

import android.media.MediaPlayer;

// Singleton for MediaPlayer
public class MyMediaPlayer {
    static MediaPlayer instance;

    public static MediaPlayer getInstance() {
        if(instance == null) {
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static int currentIndex = -1;
}
