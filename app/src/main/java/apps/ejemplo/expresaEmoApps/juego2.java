package apps.ejemplo.expresaEmoApps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.apk_exprsate.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class juego2 extends AppCompatActivity {
    public final static String host = "https://restapiexpresate.herokuapp.com/SaveRespuesta";
    MediaPlayer mMediaPlayer, background, error, bien;;
    ImageView feliz, triste, asustado, enojado, next;

    SharedPreferences sharedpreferences;
    ConexionSQLiteHelper db;

    boolean estado, estadoRespuesta, pararActividades;
    int res1=0, res2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ac= getSupportActionBar();
        ac.hide();
        setContentView(R.layout.activity_juego2);

        sharedpreferences = getSharedPreferences("Resultados", MODE_PRIVATE);
        db = new ConexionSQLiteHelper(this.getBaseContext());

        next= findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);

        mMediaPlayer = MediaPlayer.create(this, R.raw.p2ninoalegre);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();

        res1=0;
        res2=0;
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
        pararActividades=true;
    }
    public void analizarRespuesta(int numero){
        SQLiteDatabase database = db.getReadableDatabase();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        estado=true;
        int id=1;
        if (pararActividades){
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
        pararActividades=false;
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
            guardarBaseLocal();
            Intent intent = new Intent(this, Mapa.class);
            startActivity(intent);
        }
    }
    private void leerBaseDatosLocal(){
        ConexionSQLiteHelper con= new ConexionSQLiteHelper(this);
        SQLiteDatabase database = con.getReadableDatabase();
        Cursor cursor = con.leerBaseDatosLocal(database);

        while (cursor.moveToNext()){
            int p1 = cursor.getInt(cursor.getColumnIndex("p1"));
            int p2 = cursor.getInt(cursor.getColumnIndex("p2"));
            res1=p1;
            res2=p2;

        }

        cursor.close();
        con.close();
    }
    private void guardarBaseLocal(){
        ConexionSQLiteHelper con= new ConexionSQLiteHelper(this);
        SQLiteDatabase database = con.getWritableDatabase();
        MyContentProvider mypro = new MyContentProvider(con);
        int pre1, pre2;
        pre1 = sharedpreferences.getInt("p1",0);
        pre2 = sharedpreferences.getInt("p2",0);
        mypro.guardarBaseLocal(pre1, pre2, database);
        if(checkInternet()){
            new Registro().execute();
        }
        leerBaseDatosLocal();
        con.close();
    }
    public boolean checkInternet(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni= cm.getActiveNetworkInfo();
        return (ni!=null && ni.isConnected());
    }

    class Registro extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                try {
                    return HttpPost(host);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Se ha detectado un error.";
            }
        }
    }
    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        JSONObject jsonObject = buidJsonObject();

        setPostRequestContent(conn, jsonObject);

        conn.connect();

        return conn.getResponseMessage()+"";

    }
    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("p1", String.valueOf(res1));
        jsonObject.accumulate("p2",  String.valueOf(res2));

        return jsonObject;
    }
    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }
}
