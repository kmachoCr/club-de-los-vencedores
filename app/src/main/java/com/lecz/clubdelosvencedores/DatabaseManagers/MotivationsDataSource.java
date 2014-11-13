package com.lecz.clubdelosvencedores.DatabaseManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lecz.clubdelosvencedores.objects.Motivations;

import java.util.ArrayList;

public class MotivationsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id", "motiv_money", "motiv_aesthetic", "motiv_family", "motiv_health"};

    public MotivationsDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Motivations createMotivation(Motivations motivation) {
        ContentValues values = new ContentValues();
        values.put("motiv_money", motivation.isMotiv_money());
        values.put("motiv_aesthetic", motivation.isMotiv_aesthetic());
        values.put("motiv_family", motivation.isMotiv_family());
        values.put("motiv_health", motivation.isMotiv_health());

        long insertId = database.insert("Motivations", null,
                values);
        Cursor cursor = database.query("Motivations",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return motivation;
    }
    public void updateMotivation (Motivations motivation){

        ContentValues values = new ContentValues();
        values.put("motiv_money", motivation.isMotiv_money());
        values.put("motiv_aesthetic", motivation.isMotiv_aesthetic());
        values.put("motiv_family", motivation.isMotiv_family());
        values.put("motiv_health", motivation.isMotiv_health());
        database.update("Motivations", values, "id =" + motivation.getId(), null);

    }
    public void deleteMotivation(Motivations motivation) {
        long id = motivation.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("Motivations", "id = " + id, null);
    }

    public Motivations getMotivations(){
        Motivations motivation = null;

        Cursor cursor = database.query("Motivations",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            motivation = cursorToComment(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return motivation;
    }


    public Motivations getMotivation(int id) {
        Motivations motivation = new Motivations();
        String[] args = new String[] {id+""};
        Cursor cursor = database.query("Motivations",
                allColumns, "id = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            motivation = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return motivation;
    }

    private Motivations cursorToComment(Cursor cursor) {
        Motivations motivation = new Motivations();
        motivation.setId(cursor.getInt(0));
        motivation.setMotiv_money(cursor.getInt(1) == 1);
        motivation.setMotiv_aesthetic(cursor.getInt(2) == 1);
        motivation.setMotiv_family(cursor.getInt(3) == 1);
        motivation.setMotiv_health(cursor.getInt(4) == 1);

        return motivation;
    }
}