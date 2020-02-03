package apps.ejemplo.expresaEmoApps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.apk_exprsate.R;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class juego2 extends AppCompatActivity {
    MediaPlayer mMediaPlayer, background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ac= getSupportActionBar();
        ac.hide();
        setContentView(R.layout.activity_juego2);
        mMediaPlayer = MediaPlayer.create(this, R.raw.p2ninoalegre);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();

        background = MediaPlayer.create(this, R.raw.pistafondo);
        background.setAudioStreamType(AudioManager.STREAM_MUSIC);
        background.setLooping(true);
        background.start();
    }
    public void regresarHome(View v){
        background.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMediaPlayer.stop();
        background.stop();
    }
    public void nuevaActividad(View v){
        background.stop();
        Intent intent = new Intent(this, Mapa.class);
        startActivity(intent);
    }
}
