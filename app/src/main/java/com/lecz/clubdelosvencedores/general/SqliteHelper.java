package com.lecz.clubdelosvencedores.general;

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
            + "cigarettes_day numeric not null"
            + ");";

    private static final String DATATABLE_PLAN_DETAIL = "create table Plan_detail ( "
            + "number_day numeric not null, "
            + "total_cigarettes numeric not null, "
            + "used_cigarettes numeric not null,"
            + "approved numeric not null,"
            + "current numeric not null"
            + ");";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATATABLE_USER);
        database.execSQL(DATATABLE_PLAN_DETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

}