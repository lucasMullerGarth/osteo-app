package com.example.trabalho;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {

    private DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long inserirUsuario(String nome, int idade, Integer anoDiagnostico,
                               String celular, String genero, String comorbidades) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("idade", idade);
        values.put("ano_diagnostico", anoDiagnostico);
        values.put("celular", celular);
        values.put("genero", genero);
        values.put("comorbidades", comorbidades);

        long id = db.insert(DatabaseHelper.TABLE_USUARIO, null, values);

        db.close();
        return id;
    }
}
