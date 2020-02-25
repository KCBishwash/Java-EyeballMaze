package src.eyeball_game_android;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.MenuItem;

public class SoundManager {

    private MediaPlayer myMediaPlayer = new MediaPlayer();
    private Boolean sound = true;

    public void playSound(boolean victory, Context context) {
        if (victory) {
            this.stopPlaying();
            this.myMediaPlayer = MediaPlayer.create(context, R.raw.sound_victory);
            this.myMediaPlayer.start();
        } else {
            this.stopPlaying();
            this.myMediaPlayer = MediaPlayer.create(context, R.raw.sound_defeat);
            this.myMediaPlayer.start();
        }
    }

    private void stopPlaying() {
        if (this.myMediaPlayer != null) {
            if (this.myMediaPlayer.isPlaying())
                this.myMediaPlayer.stop();
            this.myMediaPlayer.reset();
            this.myMediaPlayer.release();
            this.myMediaPlayer = null;
        }
    }

    public void toggleSound(MenuItem sound) {
        float volume;
        if (this.sound) {
            this.sound = false;
            volume = (float) 0;
            sound.setTitle("Toggle Sound: Currently off");
        } else {
            this.sound = true;
            volume = (float) 1;
            sound.setTitle("Toggle Sound: Currently on");
        }
        this.myMediaPlayer.setVolume(volume, volume);
    }

}
