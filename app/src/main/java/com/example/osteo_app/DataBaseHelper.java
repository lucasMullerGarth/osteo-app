package com.example.trabalho;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USUARIO = "usuario";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USUARIO + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "idade INTEGER, " +
                "ano_diagnostico INTEGER, " +
                "celular TEXT, " +
                "genero TEXT, " +
                "comorbidades TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // recria o banco se houver atualização
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }
}
