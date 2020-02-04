package apps.ejemplo.expresaEmoApps;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {
    private ConexionSQLiteHelper dbHelper;

    private static final int ALL_RESPUESTAS=1;
    private static final int SINGLE_RESPUESTAS=2;

    // ---------------------------------------------
    private static final String AUTHORITY = "apps.ejemplo.expresaEmoApps.contentprovider";


    // CREANDO URIs
    public static final Uri CONTENT_URI =  Uri.parse("content://"+AUTHORITY+"/respuestas");

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "respuestas",ALL_RESPUESTAS);
        uriMatcher.addURI(AUTHORITY, "respuestas/#",SINGLE_RESPUESTAS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ConexionSQLiteHelper(getContext());
        return false;
    }

    public MyContentProvider(ConexionSQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("respuestas");

        switch (uriMatcher.match(uri)) {
            case ALL_RESPUESTAS:
                //do nothing
                break;
            case SINGLE_RESPUESTAS:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere("_id" + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, strings, s,
                strings1, null, null, s1);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALL_RESPUESTAS:
                return "vnd.android.cursor.dir/vnd.apps.ejemplo.expresaEmoApps.contentprovider.respuestas";
            case SINGLE_RESPUESTAS:
                return "vnd.android.cursor.item/vnd.apps.ejemplo.expresaEmoApps.contentprovider.respuestas";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_RESPUESTAS:
                //do nothing
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = db.insert("respuestas", null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }
    public void guardarBaseLocal(int p1, int p2, SQLiteDatabase database){
        ContentValues valores = new ContentValues();
        valores.put("p1", p1);
        valores.put("p2", p2);
        database.insert("respuesta",null, valores);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_RESPUESTAS:
                //do nothing
                break;
            case SINGLE_RESPUESTAS:
                String id = uri.getPathSegments().get(1);
                s = "_id" + "=" + id
                        + (!TextUtils.isEmpty(s) ?
                        " AND (" + s + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete("respuestas", s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String s, String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_RESPUESTAS:
                //do nothing
                break;
            case SINGLE_RESPUESTAS:
                String id = uri.getPathSegments().get(1);
                s = "_id" + "=" + id
                        + (!TextUtils.isEmpty(s) ?
                        " AND (" + s + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update("respuestas", values, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }
}
