package com.example.user.indecisive.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.constants.ListType;

import java.util.ArrayList;

/**
 * Created by Eamon on 06/11/2016.
 *
 * Database operations
 */

public class DBManager {

    final String TAG = DBManager.class.getSimpleName();

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


    public DBManager(Context context)
    {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    //returns writable database
    public DBManager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //closes the database
    public void close()
    {
        DBHelper.close();
    }

    //insert single item from list in database
    private long insertItem(ItemChoice item) throws SQLException
    {

        ContentValues values = toContentValues(item);

        return db.insertOrThrow(DATABASE_TABLE, null, values);
    }

    //take all list items and insert them into database
    public void insertItems(ArrayList<ItemChoice> items){

        for(ItemChoice i: items){
            insertItem(i);
        }

    }

    //makes item objects from parameters
    //checks if list is unique
    //inserts list
    public boolean insertList(String listName, ArrayList<String> items, int isDrawer){

        boolean isUnique = true;

        ArrayList<ListChoice> listArray = getListNames();

        //list name is unique proceed with insert
        for(ListChoice l: listArray){

            if(listName.equals(l.getListName())){
                isUnique = false;
                break;
            }
        }

        if(isUnique){

            //make objects from list items
            ArrayList<ItemChoice> itemsArray = new ArrayList<>();

            for(int i = 0; i < items.size(); i++){

                itemsArray.add(new ItemChoice(items.get(i), listName, isDrawer));
            }

            insertItems(itemsArray);
        }

        return isUnique;
    }

    //get values from list
    public ArrayList<ItemChoice> getItemsFromList(String listName){

        Cursor c = db.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_ITEM,
                        KEY_LIST,
                        KEY_DRAWER},
                DBManager.KEY_LIST + " = ? ",
                new String[]{listName},
                null,
                null,
                null);

        return toItemChoices(c);
    }

    public ArrayList<ListChoice> getListNames(){

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


        return toListChoices(c);
    }

    public ArrayList<ListChoice> getListsOfType(ListType type){

        int listType;

        if(type.equals(ListType.PICKER_LIST)){
            listType = 0;
        }
        else{
            listType = 1;
        }

        Cursor c = db.query(
                true,
                DBManager.DATABASE_TABLE,
                new String[]{DBManager.KEY_LIST, KEY_DRAWER},
                DBManager.KEY_DRAWER + " = ?",
                new String[]{Integer.toString(listType)},
                null,
                null,
                null,
                null);

        return toListChoices(c);
    }

    public long deleteList(String listName){

        return db.delete(
                DATABASE_TABLE,
                KEY_LIST + " = ?",
                new String[]{listName});
    }

//    public long updateList(String)

    //puts item attributes to ContentValues
    public ContentValues toContentValues(ItemChoice item){

        ContentValues cv = new ContentValues();

        cv.put(DBManager.KEY_ITEM, item.getItem());
        cv.put(DBManager.KEY_LIST, item.getList());
        cv.put(DBManager.KEY_DRAWER, item.getIsDrawer());

        return cv;
    }

    //converts cursor rows to itemChoice objects
    public ArrayList<ItemChoice> toItemChoices(Cursor c){

        ArrayList<ItemChoice> items = new ArrayList<>();

        while(c.moveToNext()){

            items.add(new ItemChoice(
                    c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_ROWID)),
                    c.getString(c.getColumnIndexOrThrow(DBManager.KEY_ITEM)),
                    c.getString(c.getColumnIndexOrThrow(DBManager.KEY_LIST)),
                    c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_DRAWER))
            ));
        }

        return items;
    }

    //converts cursor rows to ListChoice objects
    public ArrayList<ListChoice> toListChoices(Cursor c){

        ArrayList<ListChoice> items = new ArrayList<>();

        while(c.moveToNext()){
            items.add(new ListChoice(
                    c.getString(c.getColumnIndexOrThrow(DBManager.KEY_LIST)),
                    c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_DRAWER))
            ));
        }

        return items;
    }

}
