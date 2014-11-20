package com.lecz.clubdelosvencedores.DatabaseManagers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lecz.clubdelosvencedores.objects.Activity;

import java.util.ArrayList;

public class ActivityDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id", "title", "content", "type", "image", "date"};

    public ActivityDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Activity createActivity(Activity activity) {
        ContentValues values = new ContentValues();
        values.put("title", activity.getTitle());
        values.put("content", activity.getContent());
        values.put("type", activity.getType());
        values.put("image", activity.getImage());
        values.put("date", activity.getDate());
        long insertId = database.insert("Activity", null,
                values);
        Cursor cursor = database.query("Activity",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return activity;
    }

    public void updateActivity(Activity activity){
        ContentValues values = new ContentValues();
        values.put("title", activity.getTitle());
        values.put("content", activity.getContent());
        values.put("type", activity.getType());
        values.put("image", activity.getImage());
        values.put("date", activity.getDate());
        database.update("Activity", values, "id =" + activity.getId(), null);

    }
    public void deleteActivity(Activity activity) {
        long id = activity.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("Activity", "id = " + id, null);
    }

    public ArrayList<Activity> getActivities() {
        ArrayList<Activity> users = new ArrayList<Activity>();
        Cursor cursor = database.query("Activity",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Activity activity = cursorToComment(cursor);
            users.add(activity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }

    public Activity getActivity(int id) {
        Activity activity = new Activity();
        String[] args = new String[] {id+""};
        Cursor cursor = database.query("Activity",
                allColumns, "id = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            activity = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return activity;
    }

    private Activity cursorToComment(Cursor cursor) {
        Activity activity = new Activity();
        activity.setId(cursor.getInt(0));
        activity.setTitle(cursor.getString(1));
        activity.setContent(cursor.getString(2));
        activity.setType(cursor.getString(3));
        activity.setImage(cursor.getInt(4));
        activity.setDate(cursor.getLong(5));
        return activity;
    }
}