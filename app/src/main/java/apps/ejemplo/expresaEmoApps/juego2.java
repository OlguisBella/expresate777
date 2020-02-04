package apps.ejemplo.expresaEmoApps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.apk_exprsate.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class juego2 extends AppCompatActivity {
    MediaPlayer mMediaPlayer, background, error, bien;;
    ImageView feliz, triste, asustado, enojado, next;

    SharedPreferences sharedpreferences;

    boolean estado, estadoRespuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ac= getSupportActionBar();
        ac.hide();
        setContentView(R.layout.activity_juego2);

        sharedpreferences = getSharedPreferences("Resultados", MODE_PRIVATE);


        next= findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);

        mMediaPlayer = MediaPlayer.create(this, R.raw.p2ninoalegre);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();

        background = MediaPlayer.create(this, R.raw.pistafondo);
        background.setAudioStreamType(AudioManager.STREAM_MUSIC);
        background.setLooping(true);
        background.start();

        error = MediaPlayer.create(this, R.raw.ohno);
        error.setAudioStreamType(AudioManager.STREAM_MUSIC);

        bien = MediaPlayer.create(this, R.raw.muybien);
        bien.setAudioStreamType(AudioManager.STREAM_MUSIC);

        feliz= (ImageView) findViewById(R.id.feliz);
        feliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analizarRespuesta(1);
            }
        });
        asustado= (ImageView) findViewById(R.id.asustado);
        asustado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analizarRespuesta(2);
            }
        });
        enojado= (ImageView) findViewById(R.id.enojado);
        enojado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analizarRespuesta(3);
            }
        });
        triste= (ImageView) findViewById(R.id.triste);
        triste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analizarRespuesta(4);
            }
        });

        estadoRespuesta=true;
        estado= false;
    }
    public void analizarRespuesta(int numero){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        estado=true;
        if (numero>1){
            error.start();
            next.setVisibility(View.VISIBLE);
            if(estadoRespuesta){
                estadoRespuesta=false;
                editor.putInt("p2",0);
                editor.commit();
            }
        }else {
            bien.start();
            next.setVisibility(View.VISIBLE);
            if(estadoRespuesta){
                estadoRespuesta=false;
                editor.putInt("p2",1);
                editor.commit();
            }
        }

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
        if(estado){
            background.stop();
            Intent intent = new Intent(this, Mapa.class);
            startActivity(intent);
        }
    }
}
