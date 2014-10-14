package com.lecz.clubdelosvencedores.general;


        import java.util.ArrayList;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;
        import android.widget.Toast;

        import com.lecz.clubdelosvencedores.objects.User;

public class UserDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id", "name", "age", "genre", "days_without_smoking", "days_without_smoking_count", "plan_type", "cigarettes_no_smoked", "money_saved", "smoking", "cigarettes_day"};

    public UserDataSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public User createUser(User user) {
        ContentValues values = new ContentValues();
        values.put("smoking", user.getSmoking());
        values.put("name", user.getName());
        values.put("age", user.getAge());
        values.put("genre", user.getGenre());
        values.put("days_without_smoking", user.getDays_without_smoking());
        values.put("days_without_smoking_count", user.getDays_without_smoking_count());
        values.put("plan_type", user.getPlan_type());
        values.put("cigarettes_day", user.getCigarettes_per_day());
        values.put("cigarettes_no_smoked", user.getCigarettes_no_smoked());
        values.put("money_saved", user.getMoney_saved());
        long insertId = database.insert("User", null,
                values);
        Cursor cursor = database.query("User",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return user;
    }

    public void updateUser (User user){


        ContentValues values = new ContentValues();
        values.put("smoking", user.getSmoking());
        values.put("name", user.getName());
        values.put("age", user.getAge());
        values.put("genre", user.getGenre());
        values.put("days_without_smoking", user.getDays_without_smoking());
        values.put("days_without_smoking_count", user.getDays_without_smoking_count());
        values.put("plan_type", user.getPlan_type());
        values.put("cigarettes_day", user.getCigarettes_per_day());
        values.put("cigarettes_no_smoked", user.getCigarettes_no_smoked());
        values.put("money_saved", user.getMoney_saved());
        database.update("User", values, "id =" + user.getId(), null);

    }
    public void deleteUser(User user) {
        long id = user.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("User", "id = " + id, null);
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        Cursor cursor = database.query("User",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToComment(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }

    public User getUser(int id) {
        Cursor cursor = database.query("User",
                allColumns, "id = " + id, null,
                null, null, null);
        if(cursor.getCount() > 0){

            User user = cursorToComment(cursor);
            cursor.close();

            return user;
        }else{
            return null;
        }


    }

    private User cursorToComment(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setAge(cursor.getInt(2));
        user.setGenre(cursor.getInt(3) != 0);
        user.setDays_without_smoking(cursor.getInt(4));
        user.setDays_without_smoking_count(cursor.getInt(5));
        user.setPlan_type(cursor.getInt(6));
        user.setCigarettes_no_smoked(cursor.getInt(7));
        user.setMoney_saved(cursor.getInt(8));
        user.setSmoking(cursor.getInt(9) != 0);
        user.setCigarettes_per_day(cursor.getInt(10));
        return user;
    }
}