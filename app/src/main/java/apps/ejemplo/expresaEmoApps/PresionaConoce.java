package apps.ejemplo.expresaEmoApps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.apk_exprsate.R;

public class PresionaConoce extends AppCompatActivity {
    ImageView feliz, triste, asustado, enojado;
    MediaPlayer mMediaPlayer, background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presiona_conoce);
        ActionBar ac= getSupportActionBar();
        ac.hide();

        feliz= (ImageView) findViewById(R.id.feliz);
            feliz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VentanaEmergenteDialog(1);
                }
            });
        asustado= (ImageView) findViewById(R.id.asustado);
        asustado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VentanaEmergenteDialog(2);
            }
        });
        enojado= (ImageView) findViewById(R.id.enojado);
        enojado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VentanaEmergenteDialog(3);
            }
        });
        triste= (ImageView) findViewById(R.id.triste);
        triste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VentanaEmergenteDialog(4);
            }
        });

        mMediaPlayer = MediaPlayer.create(this, R.raw.presionareconoce);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();
        background = MediaPlayer.create(this, R.raw.pistafondo);
        background.setAudioStreamType(AudioManager.STREAM_MUSIC);
        background.setLooping(true);
        background.start();
    }

    public void nuevaActividad(View v){
        Intent intent = new Intent(this, juego.class);
        background.stop();
        startActivity(intent);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMediaPlayer.stop();
        background.stop();
    }
    public void regresarHome(View v){
        background.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void VentanaEmergenteDialog(int numero){
        final Dialog myDialog =  new Dialog(PresionaConoce.this);
        myDialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.ventana);
        myDialog.setTitle("Conoce");
        myDialog.show();
        ImageView contenido= myDialog.findViewById(R.id.imagen);

        if (numero==1){
            contenido.setImageResource(R.drawable.alegrecuento);
        }else if(numero==2){
            contenido.setImageResource(R.drawable.miedopeguen);
        }else if(numero==3){
            contenido.setImageResource(R.drawable.enojadoestudiar);
        }else {
            contenido.setImageResource(R.drawable.tristenoobedecer);
        }

        LinearLayout linearLayout= (LinearLayout) myDialog.findViewById(R.id.contenedor);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.cancel();
            }
        });
    }
}
