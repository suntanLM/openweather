package com.example.tantan.openweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanTan on 10/16/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "CITY";
    private final static String TABLE_NAME = "ADDED_CITY";
    private Context context;
    private SQLiteDatabase database;

    public DatabaseHelper(Context mContext)
    {
        super(mContext,DB_NAME,null,1);
        File db = mContext.getApplicationContext().getDatabasePath(DB_NAME);
        if(!db.exists())
        {
            database=mContext.openOrCreateDatabase(DB_NAME,Context.MODE_PRIVATE,null);
            String createTableQuery = "CREATE TABLE `ADDED_CITY` (" +
                    "`ID`TEXT NOT NULL PRIMARY KEY," +
                    "`NAME`TEXT," +
                    "`COUNTRY`TEXT" +
                    ");";
            database.execSQL(createTableQuery);
        }
        this.context=mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //open database
    public void openDatabase()
    {
        String dbPath = context.getDatabasePath(DB_NAME).getPath();
        if(database!=null && database.isOpen())
        {
            return;
        }
        database = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    //close database
    public void closeDatabase()
    {
        if(database!=null)
        {
            database.close();
        }
    }


    //insert,delete

    public void insertCity(String id, String name, String country)
    {
        openDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", id);
            contentValues.put("NAME", name);
            contentValues.put("COUNTRY", country);
            long result = database.insert(TABLE_NAME, null, contentValues);
            if (result != 1) {
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeDatabase();
        }
    }


    public void deleteCity(String id)
    {
        openDatabase();
        String delquery = "DELETE FROM "+TABLE_NAME + " WHERE ID="+id;
        database.execSQL(delquery);
    }

    public List<City> getCity()
    {
        List<City>  listCity = new ArrayList<City>();
        openDatabase();
        try{
            String query = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = database.rawQuery(query,null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                while(!cursor.isAfterLast())
                {
                    String strID=cursor.getString(0);
                    String strName=cursor.getString(1);
                    String strCountry=cursor.getString(2);
                    City city = new City(strID,strName,strCountry);
                    listCity.add(city);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeDatabase();
        }
        return listCity;
    }
}
