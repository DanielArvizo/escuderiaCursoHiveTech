package com.hivetech.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.hivetech.myapplication.globales.DEBUG_TAG;
import static com.hivetech.myapplication.globales.mostrarToast;

public class clientDataBase extends SQLiteOpenHelper {

    private static final int VERSION_BASE_DE_DATOS = 1;
    private static final String NOMBRE_BASE_DE_DATOS = "clientes.db";
    Context context;

    public clientDataBase(Context context){
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CLIENTS_DATABASE = "CREATE TABLE " + clientContract.clientColumns.TABLE_NAME + " ("+
                clientContract.clientColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                clientContract.clientColumns.NOMBRE + " TEXT NOT NULL," +
                clientContract.clientColumns.TELEFONO + " TEXT NOT NULL," +
                clientContract.clientColumns.EDAD + " INTEGER NOT NULL," +
                clientContract.clientColumns.MARCA_VEHICULO + " TEXT NOT NULL," +
                clientContract.clientColumns.MODELO_VEHICULO + " TEXT NOT NULL," +
                clientContract.clientColumns.FECHA_REGISTRO + " TEXT NOT NULL" +
                ")";
        try {
            db.execSQL(CLIENTS_DATABASE);
            Log.i(DEBUG_TAG, "clientes database created!!");
            Log.i(DEBUG_TAG, "SQL SENTENCE: " + CLIENTS_DATABASE);
        }catch (Exception e){
            Log.w(DEBUG_TAG,"Exception requests db on db.execSQL: " + e);
            mostrarToast(context,"Error creating clients database: " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_BASE_DE_DATOS);
        onCreate(db);
    }
}
