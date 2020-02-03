package apps.ejemplo.expresaEmoApps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.apk_exprsate.R;


public class juego extends AppCompatActivity {
    MediaPlayer mMediaPlayer, background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        ActionBar ac= getSupportActionBar();
        ac.hide();

        mMediaPlayer = MediaPlayer.create(this, R.raw.p1ninotriste);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();

        background = MediaPlayer.create(this, R.raw.pistafondo);
        background.setAudioStreamType(AudioManager.STREAM_MUSIC);
        background.setLooping(true);
        background.start();
    }
    public void regresarHome(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        background.stop();
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMediaPlayer.stop();
        background.stop();
    }
    public void nuevaActividad(View v){
        Intent intent = new Intent(this, juego2.class);
        startActivity(intent);
        background.stop();
    }
}
