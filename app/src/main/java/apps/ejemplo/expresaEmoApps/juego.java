package apps.ejemplo.expresaEmoApps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.apk_exprsate.R;


public class juego extends AppCompatActivity {
    MediaPlayer mMediaPlayer, background, error, bien;
    ImageView feliz, triste, asustado, enojado, next;

    SharedPreferences sharedpreferences;
    ConexionSQLiteHelper db;

    boolean estado, estadoRespuesta, pararActividades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        ActionBar ac= getSupportActionBar();
        ac.hide();

        sharedpreferences = getSharedPreferences("Resultados", MODE_PRIVATE);

        db = new ConexionSQLiteHelper(this.getBaseContext());

        next= findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);

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


        error = MediaPlayer.create(this, R.raw.ohno);
        error.setAudioStreamType(AudioManager.STREAM_MUSIC);

        bien = MediaPlayer.create(this, R.raw.muybien);
        bien.setAudioStreamType(AudioManager.STREAM_MUSIC);


        mMediaPlayer = MediaPlayer.create(this, R.raw.p1ninotriste);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();

        background = MediaPlayer.create(this, R.raw.pistafondo);
        background.setAudioStreamType(AudioManager.STREAM_MUSIC);
        background.setLooping(true);
        background.start();

        estadoRespuesta=true;
        pararActividades=true;
        estado= false;
    }
    public void regresarHome(View v){
        background.stop();
        mMediaPlayer.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMediaPlayer.stop();
        background.stop();
    }
    public void analizarRespuesta(int numero){
        SQLiteDatabase database = db.getReadableDatabase();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        estado=true;
        if (pararActividades){
            if (numero<4){
                error.start();
                next.setVisibility(View.VISIBLE);
                if(estadoRespuesta){
                    estadoRespuesta=false;
                    editor.putInt("p1",0);
                    editor.commit();
                }
            }else {
                bien.start();
                next.setVisibility(View.VISIBLE);
                if(estadoRespuesta){
                    estadoRespuesta=false;
                    editor.putInt("p1",1);
                    editor.commit();
                }
            }
        }
        pararActividades=false;
    }
    public void nuevaActividad(View v){
        if(estado){
            Intent intent = new Intent(this, juego2.class);
            startActivity(intent);
            background.stop();
        }
    }
}
