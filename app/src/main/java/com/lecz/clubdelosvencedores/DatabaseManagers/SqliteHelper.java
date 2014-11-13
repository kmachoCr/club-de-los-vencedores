package com.lecz.clubdelosvencedores.DatabaseManagers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lecz.clubdelosvencedores.R;

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

    private static final String DATATABLE_NOTICE = "create table Notice ( id integer primary key autoincrement, "
            + "title text not null, "
            + "content text not null, "
            + "summary text not null, "
            + "link text not null, "
            + "url text not null, "
            + "image blob null, "
            + "date numeric not null"
            + ");";

    private static final String DATATABLE_ADVICE = "create table Advice ( id integer primary key autoincrement, "
            + "type text not null, "
            + "body text not null, "
            + "cat_genre numeric not null, "
            + "motiv_money numeric not null, "
            + "motiv_aesthetic numeric not null, "
            + "motiv_family numeric not null, "
            + "motiv_health numeric not null"
            + ");";

    private static final String DATATABLE_MOTIVATIONS = "create table Motivations ( id integer primary key autoincrement, "
            + "motiv_money numeric not null, "
            + "motiv_aesthetic numeric not null, "
            + "motiv_family numeric not null, "
            + "motiv_health numeric not null"
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
        database.execSQL(DATATABLE_NOTICE);
        database.execSQL(DATATABLE_ADVICE);
        database.execSQL(DATATABLE_MOTIVATIONS);
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 1', 'time', " + new Long(1000 * 60 * 60 * 2) + ", " + 0 + ", " + R.drawable.checkmark + ", " + "'Pasa un total de 2 horas sin fumar');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 2', 'time', " + new Long(1000 * 60 * 60 * 4) + ", " + 0 + ", " + R.drawable.checkmark + ", " + "'Pasa un total de 4 horas sin fumar');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 3', 'time', " + new Long(1000 * 60 * 60 * 8) + ", " + 0 + "," + R.drawable.checkmark + "," + "'Pasa un total de 8 horas sin fumar');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 4', 'time', " + new Long(1000 * 60 * 60 * 12) + ", " + 0 + "," + R.drawable.checkmark + "," + "'Pasa un total de 12 horas sin fumar');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 5', 'time', " + new Long(1000 * 60 * 60 * 24) + ", " + 0 + "," + R.drawable.checkmark + "," + "'Pasa un total de 1 día sin fumar');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 6', 'time', " + new Long(1000 * 60 * 60 * 48) + ", " + 0 + "," + R.drawable.checkmark + "," + "'Pasa un total de 2 días sin fumar');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 7', 'money', " + new Long(10000) + ", " + 0 + "," + R.drawable.checkmark + "," + "'Ahorra un total de 10000 colones');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 8', 'money', " + new Long(30000) + ", " + 0 + "," + R.drawable.checkmark + "," + "'Ahorra un total de 30000 colones');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'premio 9', 'money', " + new Long(50000) + ", " + 0 + "," + R.drawable.checkmark + "," + "'Ahorra un total de 50000 colones');");

        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 1', " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 2', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 3', " + 1 + ", " + 1 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 4', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 5', " + 1 + ", " + 1 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 6', " + 0 + ", " + 0 + ", " + 1 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 7', " + 1 + ", " + 1 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 8', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ( 'time', 'consejo 9', " + 0 + ", " + 1 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
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
        db.execSQL("DROP TABLE IF EXISTS Notice");
        db.execSQL("DROP TABLE IF EXISTS Advice");
        db.execSQL("DROP TABLE IF EXISTS Motivations");
        onCreate(db);
    }

}