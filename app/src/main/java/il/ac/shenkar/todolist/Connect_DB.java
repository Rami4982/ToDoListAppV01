package il.ac.shenkar.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rami on 07/12/13.
 */


public class Connect_DB extends SQLiteOpenHelper
{
    private static Connect_DB ourInstance = null;
    private Context context;
    private ArrayList<ItemDetails> itemsArr = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoLists_DB";// Database Name
    private static final String TABLE_TODOLIST = "ToDoLists_Tbl";// ToDoList table name
    // ToDoList Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_ACT_ST = "status";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOTIFICATION_DATE = "notification_date";
    private static final String KEY_LOCATION = "location";


    private Connect_DB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        itemsArr = new ArrayList<ItemDetails>();
    }

    @Override
    public void onCreate(SQLiteDatabase db)// Creating Tables
    {
        String CREATE_TODOLIST_TABLE = "CREATE TABLE " + TABLE_TODOLIST + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT ," + KEY_ACT_ST + " BOOLEAN ," + KEY_DESC + " TEXT," + KEY_DATE + " TEXT," + KEY_NOTIFICATION_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT" + ")";
        try
        {
            db.execSQL(CREATE_TODOLIST_TABLE);
        }
        catch ( SQLException e )
        {
            Toast.makeText(this.context, "cant CREATE TABLE  db ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)// Upgrading database
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOLIST);// Drop older table if existed
        onCreate(db);// Create tables again
    }

    public int getSize() { return itemsArr.size(); }

    public void addItem(ItemDetails objC)
    {
        SQLiteDatabase db = null;
        try
        {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, objC.getTitle()); // ItemDetails Name//
            values.put(KEY_ACT_ST, true); // ItemDetails Phone Number
            values.put(KEY_DESC, objC.getDescription()); // Event Description
//            values.put(KEY_DATE, ItemP.getCreationDateToString());  //Event Creation Time
//            values.put(KEY_NOTIFICATION_DATE, ItemP.getNotificationDateString());
//            values.put(KEY_LOCATION, ItemP.getLocation());
            db.insert(TABLE_TODOLIST, null, values);
            itemsArr.add(objC);
        }
        catch ( Exception e )
        {
            Toast.makeText(this.context, "Error with DB ADding", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally
        {
            db.close(); // Closing database connection
        }
    }

    public long getElmID(int position) { return this.getElm(position).getId(); }

    // Getting single itemDetails


    public void populateItemsArr()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_TODOLIST;//// Select All Query
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            cursor = db.rawQuery(selectQuery, null);
        }
        catch ( Exception e )
        {
            Toast.makeText(this.context, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        if ( cursor.moveToFirst() )
        {//        looping through all rows and adding to list
            do
            {
                ItemDetails itemDetails = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));//
                itemsArr.add(itemDetails);// Adding contact to list
            }
            while ( cursor.moveToNext() );
        }
        cursor.close();

    }

    public ArrayList<ItemDetails> getItems() { return itemsArr; }

    public ItemDetails getElm(int position) { return itemsArr.get(position); }

    public void deleteElm(int position)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Toast.makeText(this.context, " sis = " + id, 20).show();
        db.delete(TABLE_TODOLIST, KEY_ID + " = ?", new String[]{String.valueOf(this.getElmID(position))});
        itemsArr.remove(position);

        db.close();

    }

    // Updating single itemDetails
    public void editElm(ItemDetails objC)
    {

        SQLiteDatabase db = null;
        try
        {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, objC.getTitle());
            values.put(KEY_DESC, objC.getDescription());
            values.put(KEY_ACT_ST, objC.getStatus());

//        values.put(KEY_DATE, objC.getCreationDateToString());
//        values.put(KEY_NOTIFICATION_DATE, objC.getNotificationDateString());
//        values.put(KEY_LOCATION, objC.getLocation());
            // updating
            db.update(TABLE_TODOLIST, values, KEY_ID + " = ?", new String[]{String.valueOf(objC.getId())});
        }
        catch ( Exception e )
        {
            Toast.makeText(this.context, "Error with DB update", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally
        {
            db.close(); // Closing database connection
        }
    }

    public static synchronized Connect_DB getInstance(Context context)
    {
        if ( ourInstance == null )
            ourInstance = new Connect_DB(context);
        return ourInstance;
    }

}

//    public ArrayList<ItemDetails> getItems ()
//    {
//        return itemsArr;
//    }
//Toast.makeText(this.context, "is", Toast.LENGTH_LONG).show();
//public void deleteInstruction ()
//{
//    if (itemsArr.get(0).getName().equals("enter todo activities")) itemsArr.remove(0);
//
//}
//public int getSize ()
//{
//    String countQuery = "SELECT  * FROM " + TABLE_TODOLIST;
//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor cursor = null;
//    int count = 0;
//    try
//    {
//        cursor = db.rawQuery(countQuery, null);
//        count = cursor.getCount();
//
//    }
//    catch (Exception e)
//    {
//        e.printStackTrace();
//        Toast.makeText(this.context, "Error ", 10).show();
//
//    }
//    finally
//    {
//        cursor.close();
//    }
//    return count;
////        return itemsArr.size();
//}
//List<ItemDetails> contactList = new ArrayList<ItemDetails>();

//        String selectQuery = "SELECT  * FROM " + TABLE_TODOLIST;//// Select All Query
//        Cursor cursor = null;
//        SQLiteDatabase db = this.getWritableDatabase();
//        try
//        {
//            cursor = db.rawQuery(selectQuery, null);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(this.context, "cant do", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//        finally
//        {
//            cursor.close();
//        }
//        Toast.makeText(this.context, " sis = ", 20).show();
//
////        looping through all rows and adding to list
//        if (cursor.moveToFirst())
//        {
//            do
//            {
//                ItemDetails itemDetails = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));//
//                Toast.makeText(this.context, " sis = " + itemDetails, 20).show();
//                itemsArr.add(itemDetails);// Adding contact to list
//            }
//            while (cursor.moveToNext());
//        }
//        ItemDetails itemDetails = new ItemDetails(4, "ss", "ss", "ss");//
//        Toast.makeText(this.context, " sis = " + itemDetails, 20).show();
//        itemsArr.add(itemDetails);// Adding contact to list

//    public ItemDetails getElm (int id)
//    {
//        if (id == 0) id = 1;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        cursor = db.query(TABLE_TODOLIST, new String[]{KEY_ID , KEY_NAME , KEY_ACT_ST , KEY_PRICE}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) cursor.moveToFirst();
//
//        ItemDetails cur = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//        Toast.makeText(this.context, "is", Toast.LENGTH_LONG).show();
//        cursor.close();
//        return cur;
////        return itemsArr.get(id);
//    }
//public ItemDetails getElm (int id)
//{
//    if (id == 0) id = 1;
//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor cursor = null;
//    cursor = db.query(TABLE_TODOLIST, new String[]{KEY_ID , KEY_NAME , KEY_ACT_ST , KEY_PRICE}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//    if (cursor != null) cursor.moveToFirst();
//
//    ItemDetails cur = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//    Toast.makeText(this.context, "is", Toast.LENGTH_LONG).show();
//    cursor.close();
//    return cur;
////        return itemsArr.get(id);
//}
//Toast.makeText(this.context, " sis = " + itemDetails, 20).show();
//public ItemDetails getElmById(long id)
//{
//    SQLiteDatabase db = null;
//    String selectQuery = "SELECT  * FROM " + TABLE_TODOLIST + " where " + KEY_ID + " =0";//// Select All Query
//    Cursor cursor = null;
//    try
//    {
//        db = this.getReadableDatabase();
//        cursor = db.rawQuery(selectQuery, null);
//        cursor = db.query(TABLE_TODOLIST, new String[]{KEY_ID , KEY_NAME , KEY_ACT_ST , KEY_DESC}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) cursor.moveToFirst();
////
//        ItemDetails cur = new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//
////            cursor = db.rawQuery(selectQuery, null);
////            if (cursor != null)
////                cursor.moveToFirst();
//        // return new ItemDetails(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));//
//        return new ItemDetails(1, "ss", "ss", "ss");//
//
//    }
//    catch ( Exception e )
//    {
//        Toast.makeText(this.context, "Error with DB Retrieving", Toast.LENGTH_LONG).show();
//        e.printStackTrace();
//    }
//    finally
//    {
//        db.close(); // Closing database connection
//    }
//    return null;
//}