package com.golemtron.currencycalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taha Rushain on 11/23/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "countryManager";

    // Country table name
    private static final String TABLE_COUNTRY = "country";

    // Country Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_COUNTRY_POSTFIX = "country_postfix";
    private static final String KEY_DOLLAR_EQUIVALENT = "dollar_equivalent";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_COUNTRY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_COUNTRY_POSTFIX + " TEXT,"
                + KEY_DOLLAR_EQUIVALENT+" REAL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);

        // Create tables again
        onCreate(db);
    }
    public void addCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, country.getName());
        values.put(KEY_COUNTRY_POSTFIX, country.getCurrencyPostfix());
        values.put(KEY_DOLLAR_EQUIVALENT, country.getDollarEquivalent());

        // Inserting Row
        db.insert(TABLE_COUNTRY, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Country
    public Country getCountry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COUNTRY, new String[] { KEY_ID,
                        KEY_NAME, KEY_COUNTRY_POSTFIX,KEY_DOLLAR_EQUIVALENT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Country country = new Country(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getFloat(3));
        // return country
        return country;
    }

    // Getting All Countries
    public List<Country> getAllCountries() {
        List<Country> countryList = new ArrayList<Country>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COUNTRY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Country country = new Country();
                country.setId(Integer.parseInt(cursor.getString(0)));
                country.setName(cursor.getString(1));
                country.setCurrencyPostfix(cursor.getString(2));
                country.setDollarEquivalent(cursor.getFloat(3));
                // Adding contact to list
                countryList.add(country);
            } while (cursor.moveToNext());
        }

        // return contact list
        return countryList;
    }

    // Getting Country Count
    public int getCountryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COUNTRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Updating single Country
    public int updateCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, country.getName());
        values.put(KEY_COUNTRY_POSTFIX, country.getCurrencyPostfix());
        values.put(KEY_DOLLAR_EQUIVALENT, country.getDollarEquivalent());

        // updating row
        return db.update(TABLE_COUNTRY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(country.getId()) });
    }

    // Deleting single Country
    public void deleteCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COUNTRY, KEY_ID + " = ?",
                new String[] { String.valueOf(country.getId()) });
        db.close();
    }
}
