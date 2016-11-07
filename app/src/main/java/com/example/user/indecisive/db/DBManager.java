package com.example.user.indecisive.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Eamon on 06/11/2016.
 */

public class DBManager {
    static final String TAG = DBManager.class.getSimpleName();
    // These are the names of the columns the table will contain
    private static final String KEY_ROWID = "_id";

    public static final String KEY_TASKNAME = "name";
    public static final String KEY_TASKDESCRIPTION = "description";
    public static final String KEY_COMPLETESTATUS = "completestatus";

    private static final String DATABASE_NAME = "TaskList";
    private static final String DATABASE_TABLE = "Tasks";
    private static final int DATABASE_VERSION = 1;
    // This is the string containing the SQL database create statement
    private static final String DATABASE_CREATE =
//    "create table " + DATABASE_TABLE +
//            " (" + KEY_ROWID + " integer primary key autoincrement, " +
//            KEY_FIRSTNAME + " text not null, " +
//            KEY_SURNAME + " text not null, " +
//            KEY_CITY + " text not null);";


            "create table " + DATABASE_TABLE +
                    " ( " +
                    KEY_ROWID + " integer primary key autoincrement, " +
                    KEY_TASKNAME + " text not null, " +
                    KEY_TASKDESCRIPTION + " text not null, " +
                    KEY_COMPLETESTATUS + " integer not null" +
                    ");";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    // constructor for your class
    public DBManager(Context ctx)
    {
// Context is a way that Android transfers info about Activities and apps.
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

//    This is the helper class that will create the dB if it doesn’t exist and
//    upgrades it if the structure has changed. It needs a constructor, an
//    onCreate() method and an onUpgrade() method

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        // constructor for your dB helper class. This code is standard. You’ve set
//        up the parameter values for the constructor already…database name,etc
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
// The “Database_create” string below needs to contain the SQL
//            statement needed to create the dB
            db.execSQL(DATABASE_CREATE);
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
            db.execSQL(DATABASE_CREATE);

        }
    } // end of the help class

    // from here on, include whatever methods will be used to access or change data
//    in the database
    //---opens the database--- any activity that uses the dB will need to do this
    public DBManager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database--- any activity that uses the dB will need to do this
    public void close()
    {
        DBHelper.close();
    }

    //---insert a person into the database---
//    public long insertTask(String name, String description, long status)
//    {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(KEY_TASKNAME, name);
//        initialValues.put(KEY_TASKDESCRIPTION, description);
//        initialValues.put(KEY_COMPLETESTATUS, status);
//        return db.insert(DATABASE_TABLE, null, initialValues);
//    }
//    //---deletes a particular person---
//    public boolean deleteTAsk(long rowId)
//    {
//// delete statement. If any rows deleted (i.e. >0), returns true
//        return db.delete(DATABASE_TABLE, KEY_ROWID +
//                "=" + rowId, null) > 0;
//    }
//    //---retrieves all the rows---
//    public Cursor getAllTasks()
//    {
//        return db.query(DATABASE_TABLE, new String[] {
//                        KEY_ROWID,
//                        KEY_TASKNAME,
//                        KEY_TASKDESCRIPTION,
//                        KEY_COMPLETESTATUS},
//                null,
//                null,
//                null,
//                null,
//                null);
//    }
//    //---retrieves a particular row---
//    public Cursor getTask(long rowId) throws SQLException
//    {
//        Cursor mCursor =
//                db.query(true, DATABASE_TABLE, new String[] {
//                                KEY_ROWID,
//                                KEY_TASKNAME,
//                                KEY_TASKDESCRIPTION,
//                                KEY_COMPLETESTATUS
//                        },
//                        KEY_ROWID + "=" + rowId,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;
//    }
//    //---updates a person---
//    public boolean updateTask(long rowId, String taskName,
//                              String taskDescription, long completeStatus)
//    {
//        ContentValues args = new ContentValues();
//        args.put(KEY_TASKNAME, taskName);
//        args.put(KEY_TASKDESCRIPTION, taskDescription);
//        args.put(KEY_COMPLETESTATUS, completeStatus);
//        return db.update(DATABASE_TABLE, args,
//                KEY_ROWID + "=" + rowId, null) > 0;
//    }
}
