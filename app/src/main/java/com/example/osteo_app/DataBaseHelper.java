package com.example.osteo_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_USUARIO = "usuario";
    public static final String TABLE_DOR = "dor_historico";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserTable = "CREATE TABLE " + TABLE_USUARIO + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "idade INTEGER, " +
                "ano_diagnostico INTEGER, " +
                "celular TEXT, " +
                "genero TEXT, " +
                "comorbidades TEXT)";
        db.execSQL(createUserTable);

        String createPainTable = "CREATE TABLE " + TABLE_DOR + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "data_registro TEXT NOT NULL, " +
                "escala_dor INTEGER NOT NULL, " +
                "local_afetado TEXT, " +
                "observacao TEXT, " +
                "FOREIGN KEY(usuario_id) REFERENCES " + TABLE_USUARIO + "(id))";
        db.execSQL(createPainTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }
}
