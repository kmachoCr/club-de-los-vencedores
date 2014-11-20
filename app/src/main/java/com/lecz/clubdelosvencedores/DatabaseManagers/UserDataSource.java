package com.lecz.clubdelosvencedores.DatabaseManagers;


        import java.util.ArrayList;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;

        import com.lecz.clubdelosvencedores.objects.User;

public class UserDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id", "name", "age", "genre", "days_without_smoking", "days_without_smoking_count", "plan_type", "cigarettes_no_smoked", "money_saved", "smoking", "cigarettes_day", "last_cigarette", "days_with_smoking"};

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
        values.put("last_cigarette", user.getLast_cigarette());
        values.put("days_with_smoking", user.getDays_with_smoking());

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
        values.put("last_cigarette", user.getLast_cigarette());
        values.put("days_with_smoking", user.getDays_with_smoking());
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

    public User getUser() {
        User user = null;
        Cursor cursor = database.query("User",
                allColumns, "id = 1", null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            user = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return user;


    }

    private User cursorToComment(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setAge(cursor.getInt(2));
        user.setGenre(cursor.getInt(3) != 0);
        user.setDays_without_smoking(cursor.getDouble(4));
        user.setDays_without_smoking_count(cursor.getDouble(5));
        user.setPlan_type(cursor.getInt(6));
        user.setCigarettes_no_smoked(cursor.getInt(7));
        user.setMoney_saved(cursor.getInt(8));
        user.setSmoking(cursor.getInt(9) != 0);
        user.setCigarettes_per_day(cursor.getInt(10));
        user.setLast_cigarette(cursor.getLong(11));
        user.setDays_with_smoking(cursor.getInt(12));
        return user;
    }
}