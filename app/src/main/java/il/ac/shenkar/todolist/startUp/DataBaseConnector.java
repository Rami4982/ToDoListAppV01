package il.ac.shenkar.todolist.startUp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import il.ac.shenkar.todolist.ItemDetails;

/**
 * Created by rami on 07/12/13.
 */


public class DataBaseConnector extends SQLiteOpenHelper
{
    private final Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoLists_DB";
    private static final String TABLE_TODOLIST = "ToDoLists_Tbl";

    // ToDoList Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_ACT_ST = "status";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOTIFICATION_DATE = "notification_date";
    private static final String KEY_LOCATION = "location";

    private List<ItemDetails> itemsDetails;

    DataBaseConnector(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        itemsDetails = new ArrayList<ItemDetails>();
    }

    @Override
    public void onCreate(SQLiteDatabase db)// Creating Tables
    {
        String CREATE_TODOLIST_TABLE =
                String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY,%s TEXT, %s BOOLEAN, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                        TABLE_TODOLIST, KEY_ID, KEY_NAME, KEY_ACT_ST, KEY_DESC, KEY_DATE, KEY_NOTIFICATION_DATE, KEY_LOCATION);
        try
        {
            db.execSQL(CREATE_TODOLIST_TABLE);
        }
        catch (SQLException e)
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

    public int getSize()
    {
        return itemsDetails.size();
    }

    public void addItem(ItemDetails objC)
    {
        SQLiteDatabase sqLiteDatabase = tryGetWritableDatabase();

        //noinspection TryFinallyCanBeTryWithResources
        try
        {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, objC.getTitle());
            values.put(KEY_DESC, objC.getDescription());
            values.put(KEY_ACT_ST, true);

            sqLiteDatabase.insert(TABLE_TODOLIST, null, values);
            itemsDetails.add(objC);
        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error with DB Adding", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally
        {
            sqLiteDatabase.close(); // Closing database connection
        }
    }

    public long getElmID(int position)
    {
        Optional<ItemDetails> itemDetailsOptional = getElm(position);

        return itemDetailsOptional.get().getId();
    }

    public void populateItemsArr()
    {
        itemsDetails = new ArrayList<ItemDetails>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODOLIST + " WHERE " + KEY_ACT_ST + " = 1";
        Cursor cursor = null;
        SQLiteDatabase db = tryGetWritableDatabase();
        try
        {
            cursor = db.rawQuery(selectQuery, null);
        }
        catch (Exception e)
        {
            Toast.makeText(this.context, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        if (cursor.moveToFirst())
        {//        looping through all rows and adding to list
            do
            {
                ItemDetails itemDetails = new ItemDetails(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) > 0 ? true : false, cursor.getString(3));//
                itemsDetails.add(itemDetails);// Adding contact to list
            }
            while (cursor.moveToNext());
        }
        cursor.close();

    }

    public List<ItemDetails> getItems()
    {
        Ordering<ItemDetails> ordering = Ordering.from(new Comparator<ItemDetails>()
        {
            @Override
            public int compare(ItemDetails itemDetails, ItemDetails itemDetails2)
            {
                return Time.compare(itemDetails.getCreationDate(), itemDetails2.getCreationDate());
            }
        });

        return ordering.sortedCopy(itemsDetails);
    }

    public Optional<ItemDetails> getElm(int position)
    {
        return Optional.of(itemsDetails.get(position));
    }

    public Optional<ItemDetails> getElmById(final int position)
    {
        ItemDetails itemDetails = Iterables.find(itemsDetails, new Predicate<ItemDetails>()
        {
            @Override
            public boolean apply(@Nullable ItemDetails input)
            {
                return input.getId() == position;
            }
        });

        return Optional.fromNullable(itemDetails);
    }

    public void deleteElm(int position)
    {
        SQLiteDatabase sqLiteDatabase = tryGetWritableDatabase();

        sqLiteDatabase.delete(TABLE_TODOLIST, KEY_ID + " = ?", new String[]{String.valueOf(this.getElmID(position))});
        itemsDetails.remove(position);

        sqLiteDatabase.close();

    }

    public void deleteElmId(int position)
    {
        try
        {
            deleteElm(itemsDetails.indexOf(getElmById(position).get()));
        }
        catch (Exception e){}
    }

    public void deleteAll()
    {
        SQLiteDatabase sqLiteDatabase = tryGetWritableDatabase();

        sqLiteDatabase.delete(TABLE_TODOLIST, "", new String[]{});
        itemsDetails.clear();

        sqLiteDatabase.close();
    }

    public void updateItemDetails(ItemDetails itemDetails)
    {
        SQLiteDatabase sqLiteDatabase = tryGetWritableDatabase();

        //noinspection TryFinallyCanBeTryWithResources
        try
        {
            ContentValues values = new ContentValues();

            values.put(KEY_NAME, itemDetails.getTitle());
            values.put(KEY_DESC, itemDetails.getDescription());
            values.put(KEY_ACT_ST, itemDetails.getStatus());

            Integer id = itemDetails.getId();
            String[] whereArgs = {id.toString()};

            sqLiteDatabase.update(TABLE_TODOLIST, values, KEY_ID + " = ?", whereArgs);
            populateItemsArr();
        }
        catch(Exception e)
        {
            Toast.makeText(this.context, "Error with DB update", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally
        {
            sqLiteDatabase.close();
        }
    }

    private SQLiteDatabase tryGetWritableDatabase()
    {
        return Optional.fromNullable(getWritableDatabase()).get();
    }
}