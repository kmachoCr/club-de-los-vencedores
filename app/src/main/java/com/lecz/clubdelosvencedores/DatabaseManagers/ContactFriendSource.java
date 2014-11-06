package com.lecz.clubdelosvencedores.DatabaseManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lecz.clubdelosvencedores.objects.Contact;
import com.lecz.clubdelosvencedores.objects.PlanDetail;

import java.util.ArrayList;

public class ContactFriendSource {

    // Database fields
    private SQLiteDatabase database;
    private SqliteHelper dbHelper;
    private String[] allColumns = {
            "id","contact_id", "name", "phone_number"};

    public ContactFriendSource(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Contact createContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put("contact_id", contact.getContact_id());
        values.put("name", contact.getName());
        values.put("phone_number", contact.getPhone());
        long insertId = database.insert("Contact", null,
                values);
        Cursor cursor = database.query("Contact",
                allColumns, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return contact;
    }

    public void updateContact (Contact contact){

        ContentValues values = new ContentValues();
        values.put("contact_id", contact.getContact_id());
        values.put("name", contact.getName());
        values.put("phone_number", contact.getPhone());
        database.update("Contact", values, "id =" + contact.getId(), null);

    }
    public void deleteContact(Contact contact) {
        long id = contact.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete("Contact", "id = " + id, null);
    }

    public ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        Cursor cursor = database.query("Contact",
                allColumns, null, null, null, null, "name DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = cursorToComment(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return contacts;
    }


    public Contact getPlanDetail(int id) {
        Contact contact = new Contact();
        String[] args = new String[] {id+""};
        Cursor cursor = database.query("Contact",
                allColumns, "id = ?", args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contact = cursorToComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return contact;
    }

    private Contact cursorToComment(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getInt(0));
        contact.setContact_id(cursor.getInt(1));
        contact.setName(cursor.getString(2));
        contact.setPhone(cursor.getString(3));
        return contact;
    }
}