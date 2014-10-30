package com.lecz.clubdelosvencedores.DatabaseManagers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lecz.clubdelosvencedores.objects.Achievement;
import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;

public class AchievementDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id", "title", "type", "amount", "completed"};

    public AchievementDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Achievement createAchievement(Achievement achievement) {
        ContentValues values = new ContentValues();
        values.put("title", achievement.getTitle());
        values.put("type", achievement.getType());
        values.put("amount", achievement.getAmount());
        values.put("completed", achievement.isCompleted());
        long insertId = database.insert("Achievement", null,
                values);
        Cursor cursor = database.query("Achievement",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return achievement;
    }

    public void updateAchievement(Achievement achievement){
        ContentValues values = new ContentValues();
        values.put("title", achievement.getTitle());
        values.put("type", achievement.getType());
        values.put("amount", achievement.getAmount());
        values.put("completed", achievement.isCompleted());
        database.update("Achievement", values, "id =" + achievement.getId(), null);

    }
    public void deleteAchievement(Achievement achievement) {
        long id = achievement.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("Achievement", "id = " + id, null);
    }

    public ArrayList<Achievement> getAchievements() {
        ArrayList<Achievement> users = new ArrayList<Achievement>();
        String[] args = new String[] {"0"};
        Cursor cursor = database.query("Achievement",
                allColumns, "completed = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Achievement achievement = cursorToComment(cursor);
            users.add(achievement);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }

    public ArrayList<Achievement> getAllAchievements() {
        ArrayList<Achievement> users = new ArrayList<Achievement>();
        String[] args = new String[] {"0"};
        Cursor cursor = database.query("Achievement",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Achievement achievement = cursorToComment(cursor);
            users.add(achievement);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }

    public Achievement getAchievement(int id) {
        Achievement achievement = new Achievement();
        String[] args = new String[] {id+""};
        Cursor cursor = database.query("Achievement",
                allColumns, "id = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            achievement = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return achievement;
    }

    private Achievement cursorToComment(Cursor cursor) {
        Achievement achievement = new Achievement();
        achievement.setId(cursor.getInt(0));
        achievement.setTitle(cursor.getString(1));
        achievement.setType(cursor.getString(2));
        achievement.setAmount(cursor.getLong(3));
        achievement.setCompleted(cursor.getInt(4) != 0);
        return achievement;
    }
}