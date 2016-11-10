package com.example.user.indecisive.db;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.business.ListChoice;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Eamon on 06/11/2016.
 */

public class DBManager {
    static final String TAG = DBManager.class.getSimpleName();

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // These are the names of the columns the table will contain
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ITEM = "item";
    public static final String KEY_LIST = "list";
    public static final String KEY_DRAWER = "drawer";

    public static final String DATABASE_NAME = "IndecisiveDB";
    public static final String DATABASE_TABLE = "items";
    public static final int DATABASE_VERSION = 2;

    //SQL create statement on OnCreate and onUpgrade
    public static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE +
                    " ( " +
                    KEY_ROWID + " integer primary key autoincrement, " +
                    KEY_ITEM + " text not null, " +
                    KEY_LIST + " text not null, " +
                    KEY_DRAWER + " integer not null" +
                    ");";


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
    public long insertItem(ItemChoice item) throws SQLException
    {

        ContentValues values = toContentValues(item);

        return db.insertOrThrow(DATABASE_TABLE, null, values);
    }

    public void insertItems(ArrayList<ItemChoice> items) throws SQLException
    {
        for(ItemChoice i: items){
            insertItem(i);
        }

    }

    public void insertList(String listName, ArrayList<String> items, int isDrawer){

        ArrayList<ItemChoice> itemsArray = new ArrayList<>();

        for(int i = 0; i < items.size(); i++){

            itemsArray.add(new ItemChoice(items.get(i), listName, isDrawer));
        }

        insertItems(itemsArray);
    }


    //---deletes a particular person---
    public boolean deleteTAsk(long rowId)
    {
// delete statement. If any rows deleted (i.e. >0), returns true
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    public void getItemsFromList(){

    }

    public ArrayList<ListChoice> getListNames(){

        ArrayList<ListChoice> list = new ArrayList<>();

        Cursor c = db.query(
                true,
                DBManager.DATABASE_TABLE,
                new String[]{DBManager.KEY_LIST, KEY_DRAWER},
                null,
                null,
                null,
                null,
                null,
                null);

        while(c.moveToNext()){
            list.add(new ListChoice(
                    c.getString(c.getColumnIndexOrThrow(DBManager.KEY_LIST)),
                    c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_DRAWER))
                    ));
        }

        return list;
    }

    //---retrieves all the rows---
    public Cursor getAllItems()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_ITEM,
                        KEY_LIST,
                        KEY_DRAWER},
                null,
                null,
                null,
                null,
                null);
    }

    public ContentValues toContentValues(ItemChoice item){
        ContentValues cv = new ContentValues();

        cv.put(DBManager.KEY_ITEM, item.getItem());
        cv.put(DBManager.KEY_LIST, item.getList());
        cv.put(DBManager.KEY_DRAWER, item.getDrawer());

        return cv;
    }
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
