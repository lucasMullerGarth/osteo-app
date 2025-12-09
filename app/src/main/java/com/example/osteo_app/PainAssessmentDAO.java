package com.example.osteo_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PainAssessmentDAO {

    private DataBaseHelper dbHelper;

    public PainAssessmentDAO(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public long inserirAvaliacao(long usuarioId, String data, int nivelDor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("usuario_id", usuarioId);
        values.put("data_registro", data);
        values.put("escala_dor", nivelDor);

        long id = db.insert(DataBaseHelper.TABLE_DOR, null, values);

        db.close();
        return id;
    }

    public List<Pair<String, Integer>> getAllPainAssessments(long usuarioId) {
        List<Pair<String, Integer>> assessments = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataBaseHelper.TABLE_DOR,
                new String[]{"data_registro", "escala_dor"},
                "usuario_id = ?",
                new String[]{String.valueOf(usuarioId)},
                null, null, "data_registro ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data_registro"));
                int nivelDor = cursor.getInt(cursor.getColumnIndexOrThrow("escala_dor"));
                assessments.add(new Pair<>(data, nivelDor));
            }
            cursor.close();
        }
        db.close();
        return assessments;
    }
}
