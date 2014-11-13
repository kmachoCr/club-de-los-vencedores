package com.lecz.clubdelosvencedores.DatabaseManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lecz.clubdelosvencedores.objects.Notice;
import com.lecz.clubdelosvencedores.objects.PlanDetail;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NoticeDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id","title", "content", "summary","link", "url", "date"};

    public NoticeDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Notice createNotice(Notice notice) {
        ContentValues values = new ContentValues();
        values.put("title", notice.getTitle());
        values.put("content", notice.getContent());
        values.put("summary", notice.getSummary());
        values.put("link", notice.getLink());
        values.put("url", notice.getUrl());
        values.put("date", notice.getDate());
        long insertId = database.insert("Notice", null,
                values);
        Cursor cursor = database.query("Notice",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return notice;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
    public void updateNotice (Notice notice){

        ContentValues values = new ContentValues();
        values.put("title", notice.getTitle());
        values.put("content", notice.getContent());
        values.put("summary", notice.getSummary());
        values.put("link", notice.getLink());
        values.put("url", notice.getUrl());
        values.put("image", notice.convertBitmapToByteArray(notice.getImage()));
        values.put("date", notice.getDate());
        database.update("Notice", values, "id =" + notice.getId(), null);

    }
    public void deleteNotice(Notice notice) {
        long id = notice.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("Notice", "id = " + id, null);
    }

    public ArrayList<Notice> getNotices(){
        ArrayList<Notice> listNotices = new ArrayList<Notice>();

        Cursor cursor = database.query("Notice",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Notice notice = cursorToComment(cursor);
            listNotices.add(notice);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listNotices;
    }


    public Notice getNotice(String title) {
        Notice notice = null;
        String[] args = new String[] {title};
        Cursor cursor = database.query("Notice",
                allColumns, "title = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            notice = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return notice;
    }

    public Notice getNotice(int id) {
        Notice notice = new Notice();
        String[] args = new String[] {id+""};
        Cursor cursor = database.query("Notice",
                allColumns, "id = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            notice = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return notice;
    }

    private Notice cursorToComment(Cursor cursor) {
        Notice notice = new Notice();
        notice.setId(cursor.getInt(0));
        notice.setTitle(cursor.getString(1));
        notice.setContent(cursor.getString(2));
        notice.setSummary(cursor.getString(3));
        notice.setLink(cursor.getString(4));
        notice.setUrl(cursor.getString(5));
        notice.setDate(cursor.getLong(6));

        return notice;
    }
}