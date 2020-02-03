package apps.ejemplo.expresaEmoApps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


import com.example.apk_exprsate.R;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer, background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ac= getSupportActionBar();
        ac.hide();
        mMediaPlayer = MediaPlayer.create(this, R.raw.expresate);
        background = MediaPlayer.create(this, R.raw.pistafondo);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        background.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        background.setLooping(true);
        mMediaPlayer.start();
        background.start();
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMediaPlayer.stop();
        background.stop();
    }

    public void nuevaActividad(View v){
        mMediaPlayer.stop();
        background.stop();
        Intent intent = new Intent(this, PresionaConoce.class);
        startActivity(intent);
    }
}
