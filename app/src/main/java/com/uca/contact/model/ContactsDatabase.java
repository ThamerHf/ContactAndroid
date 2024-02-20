package com.uca.contact.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uca.contact.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsDatabase extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "contacts_db";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    public ContactsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create contacts table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE CONTACTS (" +
                "TEL TEXT PRIMARY KEY" +
                ",NAME TEXT" +
                ",ADDRESS TEXT" +
                //",PHOTO BLOB" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS CONTACTS");

        // Create tables again
        onCreate(db);
    }

    // Method to fetch all contacts from the database
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM CONTACTS";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setTel(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setAddress(cursor.getString(2));
                //contact.setPhoto(cursor.getBlob(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        db.close();

        // Return contact list
        return contactList;
    }

    // Method to fetch one contact from the database by his telephone number
    public Contact getContactByTel(String tel) {
        Contact contact = null;
        // Select Query
        String selectQuery = "SELECT * FROM CONTACTS WHERE TEL = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {tel});

        if (cursor != null && cursor.moveToFirst()) {
            contact = new Contact();
            contact.setTel(cursor.getString(0));
            contact.setName(cursor.getString(1));
            contact.setAddress(cursor.getString(2));
            //contact.setPhoto(cursor.getBlob(3));
        }

        // Close cursor and database
        cursor.close();
        db.close();

        // Return contact
        return contact;
    }

    // Add a method to add a contact to the database
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TEL", contact.getTel());
        values.put("NAME", contact.getName());
        values.put("ADDRESS", contact.getAddress());
        //values.put("PHOTO", contact.getPhoto());

        // Inserting Row
        db.insert("CONTACTS", null, values);
        db.close(); // Closing database connection
    }

    // Add a method to update a contact in the database
    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TEL", contact.getTel());
        values.put("NAME", contact.getName());
        values.put("ADDRESS", contact.getAddress());
        //values.put("PHOTO", contact.getPhoto());

        // Updating Row
        db.update("CONTACTS", values, "TEL = ?", new String[]{contact.getTel()});
        db.close(); // Closing database connection
    }

    // Add a method to delete a contact from the database
    public void deleteContact(String tel) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Deleting Row
        db.delete("CONTACTS", "TEL = ?", new String[]{tel});
        db.close(); // Closing database connection
    }
}