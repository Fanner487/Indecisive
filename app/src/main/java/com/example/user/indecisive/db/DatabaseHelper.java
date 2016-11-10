package com.example.user.indecisive.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Eamon on 10/11/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    final String TAG = DatabaseHelper.class.getSimpleName();

    DatabaseHelper(Context context)
    {
        super(context, DBManager.DATABASE_NAME, null, DBManager.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
// The “Database_create” string below needs to contain the SQL
//            statement needed to create the dB
        Log.d(TAG, "In onCreate");
        db.execSQL(DBManager.DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
// If you want to change the structure of your database, e.g.
//            Add a new column to a table, the code will go head..
//            This method only triggers if the database version number has
//                increased

        Log.d(TAG, "In onUpgrade");
        db.execSQL(DBManager.DATABASE_CREATE);

    }
}
