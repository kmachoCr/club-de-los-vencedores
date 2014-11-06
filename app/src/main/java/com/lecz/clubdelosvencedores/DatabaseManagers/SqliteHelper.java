package com.lecz.clubdelosvencedores.DatabaseManagers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "club_vencedores.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATATABLE_USER = "create table User ( id integer primary key autoincrement, "
            + "name text not null, "
            + "age numeric not null, "
            + "genre numeric not null,"
            + "days_without_smoking numeric not null,"
            + "days_without_smoking_count numeric not null, "
            + "plan_type numeric not null, "
            + "cigarettes_no_smoked numeric not null, "
            + "money_saved numeric not null, "
            + "smoking numeric not null, "
            + "cigarettes_day numeric not null, "
            + "last_cigarette numeric not null"
            + ");";

    private static final String DATATABLE_PLAN_DETAIL = "create table PlanDetail ( id integer primary key autoincrement, "
            + "number_day numeric not null, "
            + "total_cigarettes numeric not null, "
            + "used_cigarettes numeric not null,"
            + "approved numeric not null,"
            + "current numeric not null,"
            + "date numeric not null,"
            + "completed numeric not null"
            + ");";

    private static final String DATATABLE_ACHIEVEMENTS = "create table Achievement ( id integer primary key autoincrement, "
            + "title text not null, "
            + "type text not null, "
            + "amount numeric not null, "
            + "completed numeric not null,"
            + "image numeric not null,"
            + "description text not null"
            + ");";

    private static final String DATATABLE_CONTACTFRIEND = "create table Contact ( id integer primary key autoincrement, "
            + "contact_id numeric not null, "
            + "name text not null, "
            + "phone_number text not null"
            + ");";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATATABLE_USER);
        database.execSQL(DATATABLE_PLAN_DETAIL);
        database.execSQL(DATATABLE_ACHIEVEMENTS);
        database.execSQL(DATATABLE_CONTACTFRIEND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS PlanDetail");
        db.execSQL("DROP TABLE IF EXISTS Achievement");
        db.execSQL("DROP TABLE IF EXISTS Contact");
        onCreate(db);
    }

}