package apps.ejemplo.expresaEmoApps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {
    final String CREAR_TABLA_RESPUESTAS="CREATE TABLE respuesta (id INTEGER primary key autoincrement, p1 INTEGER, p2 INTEGER)";
    private static final String DATABASE_NAME= "expresate.db";
    private static final int DATABASE_VERSION= 1;

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ConexionSQLiteHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_RESPUESTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS respuesta");
        onCreate(db);
    }

    public void agregarResultado(int id, int p1, int p2){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("id", id);
            valores.put("p1", p1);
            valores.put("p2", p2);
            db.insert("respuesta", null, valores);
            db.close();
        }
    }

    public void actualizarResultado(int id, int p1, int p2){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("id", id);
            valores.put("p1", p1);
            valores.put("p2", p2);
            db.update("respuesta", valores, "id="+id, null);
            db.close();
        }
    }

    public void guardarBaseLocal(int p1, int p2, SQLiteDatabase database){
        ContentValues valores = new ContentValues();
        valores.put("p1", p1);
        valores.put("p2", p2);
        database.insert("respuesta",null, valores);
    }
    public Cursor leerBaseDatosLocal(SQLiteDatabase database){
        String [] projection = {"id","p1","p2"};
          return (database.query("respuesta",projection, null, null, null, null, null));
    }

    public void actualizarBaseDatosLocal (int p1, int p2, SQLiteDatabase database){
        ContentValues valores = new ContentValues();
        valores.put("p1",p1);
        valores.put("p2",p2);
        String selection = "respuesta LIKE?";
        String [] selection_args ={""+p1, ""+p2};
        database.update("respuesta", valores, selection, selection_args);
    }

}
