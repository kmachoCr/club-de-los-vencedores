package com.lecz.clubdelosvencedores.DatabaseManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lecz.clubdelosvencedores.objects.Advice;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdviceDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id","type", "body", "cat_genre", "motiv_money", "motiv_aesthetic", "motiv_family", "motiv_health"};

    public AdviceDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Advice createAdvice(Advice advice) {
        ContentValues values = new ContentValues();
        values.put("type", advice.getType());
        values.put("body", advice.getBody());
        values.put("cat_genre", advice.isCat_genre());
        values.put("motiv_money", advice.isMotiv_money());
        values.put("motiv_aesthetic", advice.isMotiv_aesthetic());
        values.put("motiv_family", advice.isMotiv_family());
        values.put("motiv_health", advice.isMotiv_health());

        long insertId = database.insert("Advice", null,
                values);
        Cursor cursor = database.query("Advice",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return advice;
    }
    public void updateAdvice (Advice advice){

        ContentValues values = new ContentValues();
        values.put("type", advice.getType());
        values.put("body", advice.getBody());
        values.put("cat_genre", advice.isCat_genre());
        values.put("motiv_money", advice.isMotiv_money());
        values.put("motiv_aesthetic", advice.isMotiv_aesthetic());
        values.put("motiv_family", advice.isMotiv_family());
        values.put("motiv_health", advice.isMotiv_health());
        database.update("Advice", values, "id =" + advice.getId(), null);

    }
    public void deleteAdvice(Advice advice) {
        long id = advice.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("Advice", "id = " + id, null);
    }

    public ArrayList<Advice> getAdvices(){
        ArrayList<Advice> listAdvices = new ArrayList<Advice>();

        Cursor cursor = database.query("Advice",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Advice advice = cursorToComment(cursor);
            listAdvices.add(advice);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listAdvices;
    }


    public ArrayList<Advice> getAdvices(int genre, boolean money, boolean aesthetic, boolean family,  boolean health) {
        ArrayList<Advice> listAdvices = new ArrayList<Advice>();

        String query = "";

        if(money){
            query += "motiv_money = 1 or ";
        }
        if(aesthetic){
            query +="motiv_aesthetic = 1 or ";
        }
        if(family){
            query += "motiv_family = 1 or ";
        }
        if(health){
            query += "motiv_health = 1 or";
        }
        Cursor cursor;
        if(query.length() == 0){
            cursor = database.query("Advice",
                    allColumns, query, null, null, null, null);
        }else{
            query = query.substring(0, query.length()-3);
            cursor = database.query("Advice",
                    allColumns, null, null, null, null, null);
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Advice advice = cursorToComment(cursor);
            listAdvices.add(advice);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listAdvices;
    }

    public Advice getAdvice(int id) {
        Advice advice = new Advice();
        String[] args = new String[] {id+""};
        Cursor cursor = database.query("Advice",
                allColumns, "id = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            advice = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return advice;
    }

    private Advice cursorToComment(Cursor cursor) {
        Advice advice = new Advice();
        advice.setId(cursor.getInt(0));
        advice.setType(cursor.getString(1));
        advice.setBody(cursor.getString(2));
        advice.setCat_genre(cursor.getInt(3) == 1);
        advice.setMotiv_money(cursor.getInt(4) == 1);
        advice.setMotiv_aesthetic(cursor.getInt(5) == 1);
        advice.setMotiv_family(cursor.getInt(6) == 1);
        advice.setMotiv_health(cursor.getInt(7) == 1);

        return advice;
    }
}