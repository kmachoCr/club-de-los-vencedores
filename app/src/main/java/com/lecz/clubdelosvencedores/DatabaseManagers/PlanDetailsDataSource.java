package com.lecz.clubdelosvencedores.DatabaseManagers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lecz.clubdelosvencedores.objects.PlanDetail;
import com.lecz.clubdelosvencedores.objects.User;

import java.util.ArrayList;

public class PlanDetailsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id", "number_day","total_cigarettes","used_cigarettes","approved","current"};

    public PlanDetailsDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public PlanDetail createPlanDetail(PlanDetail planDetail) {
        ContentValues values = new ContentValues();
        values.put("number_day", planDetail.getNumber_day());
        values.put("total_cigarettes", planDetail.getTotal_cigarettes());
        values.put("used_cigarettes", planDetail.getUsed_cigarettes());
        values.put("approved", planDetail.isApproved());
        values.put("current", planDetail.isCurrent());
        long insertId = database.insert("PlanDetail", null,
                values);
        Cursor cursor = database.query("PlanDetail",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return planDetail;
    }

    public void updatePlanDetail (PlanDetail planDetail){

        ContentValues values = new ContentValues();
        values.put("number_day", planDetail.getNumber_day());
        values.put("total_cigarettes", planDetail.getTotal_cigarettes());
        values.put("used_cigarettes", planDetail.getUsed_cigarettes());
        values.put("approved", planDetail.isApproved());
        values.put("current", planDetail.isCurrent());
        database.update("PlanDetail", values, "id =" + planDetail.getId(), null);

    }
    public void deletePlanDetail(PlanDetail planDetail) {
        long id = planDetail.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("PlanDetail", "id = " + id, null);
    }

    public ArrayList<PlanDetail> getPlanDetails() {
        ArrayList<PlanDetail> planDetails = new ArrayList<PlanDetail>();

        Cursor cursor = database.query("PlanDetail",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PlanDetail planDetail = cursorToComment(cursor);
            planDetails.add(planDetail);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return planDetails;
    }

    public PlanDetail getCurrentPlanDetail() {
        PlanDetail planDetail = new PlanDetail();

        Cursor cursor = database.query("PlanDetail",
                allColumns, "current = 1", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            planDetail = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return planDetail;
    }

    public PlanDetail getPlanDetail(int id) {
        Cursor cursor = database.query("PlanDetail",
                allColumns, "id = " + id, null,
                null, null, null);
        if(cursor.getCount() > 0){

            PlanDetail planDetail = cursorToComment(cursor);
            cursor.close();

            return planDetail;
        }else{
            return null;
        }


    }

    private PlanDetail cursorToComment(Cursor cursor) {
        PlanDetail planDetail = new PlanDetail();
        planDetail.setId(cursor.getInt(0));
        planDetail.setNumber_day(cursor.getInt(1));
        planDetail.setTotal_cigarettes(cursor.getInt(2));
        planDetail.setUsed_cigarettes(cursor.getInt(3));
        planDetail.setApproved(cursor.getInt(4) != 0);
        planDetail.setCurrent(cursor.getInt(5) != 0);
        return planDetail;
    }
}