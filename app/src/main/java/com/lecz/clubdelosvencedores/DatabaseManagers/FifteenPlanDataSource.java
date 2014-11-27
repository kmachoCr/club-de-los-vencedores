package com.lecz.clubdelosvencedores.DatabaseManagers;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lecz.clubdelosvencedores.objects.ConfigPlan;

import java.util.ArrayList;

public class FifteenPlanDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id", "number_cigarettes", "day1", "day2", "day3", "day4", "day5", "day6", "day7", "day8", "day9", "day10", "day11", "day12", "day13", "day14", "day15"};

    public FifteenPlanDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public ConfigPlan getFifteenPlanByCigarettes(String count_cigarettes) {
        ConfigPlan configPlag = new ConfigPlan();
        String[] args = new String[] {count_cigarettes};
        Cursor cursor = database.query("fifteenDaysPlan",
                allColumns, "number_cigarettes = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            configPlag = cursorToComment(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return configPlag;
    }

    public ArrayList<ConfigPlan> getAllFifteenPlan() {
        ArrayList<ConfigPlan> configPlans = new ArrayList<ConfigPlan>();
        Cursor cursor = database.query("fifteenDaysPlan",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ConfigPlan configPlan = cursorToComment(cursor);
            configPlans.add(configPlan);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return configPlans;
    }

    private ConfigPlan cursorToComment(Cursor cursor) {
        ConfigPlan configPlag = new ConfigPlan();
        configPlag.setId(cursor.getInt(0));
        configPlag.setNumber_cigarettes(cursor.getString(1));
        configPlag.getDayConfig().add(cursor.getInt(2));
        configPlag.getDayConfig().add(cursor.getInt(3));
        configPlag.getDayConfig().add(cursor.getInt(4));
        configPlag.getDayConfig().add(cursor.getInt(5));
        configPlag.getDayConfig().add(cursor.getInt(6));
        configPlag.getDayConfig().add(cursor.getInt(7));
        configPlag.getDayConfig().add(cursor.getInt(8));
        configPlag.getDayConfig().add(cursor.getInt(9));
        configPlag.getDayConfig().add(cursor.getInt(10));
        configPlag.getDayConfig().add(cursor.getInt(11));
        configPlag.getDayConfig().add(cursor.getInt(12));
        configPlag.getDayConfig().add(cursor.getInt(13));
        configPlag.getDayConfig().add(cursor.getInt(14));
        configPlag.getDayConfig().add(cursor.getInt(15));
        configPlag.getDayConfig().add(cursor.getInt(16));
        configPlag.setType_plan("fifteen");

        return configPlag;
    }
}