package com.example.osteo_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {

    private DataBaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DataBaseHelper(context);
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

        long id = db.insert(DataBaseHelper.TABLE_USUARIO, null, values);

        db.close();
        return id;
    }

    public Usuario getUsuario() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DataBaseHelper.TABLE_USUARIO, null, null, null, null, null, null, "1");

        Usuario usuario = null;
        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            int idade = cursor.getInt(cursor.getColumnIndexOrThrow("idade"));
            Integer anoDiagnostico = cursor.getInt(cursor.getColumnIndexOrThrow("ano_diagnostico"));
            String celular = cursor.getString(cursor.getColumnIndexOrThrow("celular"));
            String genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"));
            String comorbidades = cursor.getString(cursor.getColumnIndexOrThrow("comorbidades"));

            if (cursor.isNull(cursor.getColumnIndexOrThrow("ano_diagnostico"))) {
                anoDiagnostico = null;
            }

            usuario = new Usuario(id, nome, idade, anoDiagnostico, celular, genero, comorbidades);
            cursor.close();
        }
        db.close();
        return usuario;
    }

    public void excluirTodosUsuarios() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DataBaseHelper.TABLE_USUARIO, null, null);
        db.close();
    }
}
