package com.acme.eac2_calvo_l;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper {

    private Context mCtx = null;
    private DataBaseHelperInternal mDbHelper = null;
    private SQLiteDatabase mDb = null;
    private static final String DATABASE_NAME = "BDLLIBRES";
    private static final int DATABASE_VERSION = 1;

    //tabla y campos
    private static final String DATABASE_TABLE_LLIBRES = "LLIBRES";
    public static final String SL_ID = "ID";
    public static final String SL_TITOL = "TITOL";
    public static final String SL_AUTOR = "AUTOR";
    public static final String SL_EDITORIAL = "EDITORIAL";
    public static final String SL_TIPUSTAPA = "TIPUSTAPA";
    public static final String SL_PREU = "PREU";
    // SQL de creación de la tabla

    private static final String DATABASE_CREATE_LLIBRES =
            "create table if not exists "+ DATABASE_TABLE_LLIBRES +" ("+SL_ID+" integer primary key autoincrement, "+SL_TITOL+" text not null, "+SL_AUTOR+" text not null, "
                    +SL_EDITORIAL+" text not null, "+SL_TIPUSTAPA+" text not null, "+SL_PREU+" text not null )";

    //constructor
    public DataBaseHelper(Context ctx) {
        this.mCtx = ctx;
    }

    public DataBaseHelper open() throws SQLException{
        mDbHelper = new DataBaseHelperInternal(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    //obtener todos los elementos.
    //Devuelve objeto tipo Cursor
    public Cursor getItems() {
        return mDb.query(DATABASE_TABLE_LLIBRES, new String[] {SL_ID, SL_TITOL, SL_AUTOR, SL_EDITORIAL, SL_TIPUSTAPA, SL_PREU}, null, null, null, null,SL_ID);
    }

    /*Crear Elemento en la tabla
    método insert()
    Método para insertar un libro con todos los parámetros
     */
    public long insertItem (Llibre llibre ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(SL_TITOL, llibre.getTitol());
        initialValues.put(SL_AUTOR, llibre.getAutors());
        initialValues.put(SL_EDITORIAL,llibre.getEditorial());
        initialValues.put(SL_TIPUSTAPA, llibre.getTipus());
        initialValues.put(SL_PREU, llibre.getPreu());
        return mDb.insert(DATABASE_TABLE_LLIBRES, null, initialValues);
    }


    //Método para obtener ItembyTitle
    public Cursor getItembyTitle(String titol){
        return mDb.rawQuery(" select "+ SL_TITOL +","+ SL_AUTOR +","+ SL_EDITORIAL +","+ SL_TIPUSTAPA +","
                + SL_PREU + " from " + DATABASE_TABLE_LLIBRES  + " where "
                + SL_TITOL + " like '" + titol + "'",null);
    }

    //Delete per titol
    public int delete(String titol) {
        return mDb.delete(DATABASE_TABLE_LLIBRES, SL_TITOL + "=?", new String[]{ titol});
    }

    public int updateItem( String titol, String autor, String editorial, String tipus, String preu) {
        ContentValues cv = new ContentValues();
        cv.put(SL_TITOL, titol); //Añade
        cv.put(SL_AUTOR, autor); //idem
        cv.put(SL_EDITORIAL, editorial);//idem
        cv.put(SL_TIPUSTAPA, tipus);//idem
        cv.put(SL_PREU, preu);//idem
        return mDb.update(DATABASE_TABLE_LLIBRES, cv, SL_TITOL + "=?", new String[]{titol});
    }




//CLASE PRIVADA STATIC PARA CONTROL DE LA SQLITE
/*Se encapsula completamente y hereda de SQLiteOpenHeper
se encargará de crear las tablas y detectar cambios de versiones
 */
    private static class DataBaseHelperInternal extends SQLiteOpenHelper{

        public DataBaseHelperInternal (Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTables(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            deleteTables(db);
            createTables(db);
        }

        private void createTables (SQLiteDatabase db){
            db.execSQL(DATABASE_CREATE_LLIBRES);
        }

        private void deleteTables (SQLiteDatabase db){
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LLIBRES);
        }


    }
}

