package com.example.jiafang_jiang.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jiafang_jiang.todolist.model.TodoListItem;

import java.util.ArrayList;
import java.util.List;


public class TodoListDatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "todoListDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Info
    private static final String TABLE_TODO_ITEM = "todoItem";

    // Key Info
    private static final String KEY_TODO_ITEM_ID = "id";
    private static final String KEY_TODO_DETAIL_TEXT = "text";

    private static TodoListDatabaseHelper dbInstance;

    private TodoListDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized TodoListDatabaseHelper getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new TodoListDatabaseHelper(context);
        }
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_ITEM_TABLE =
                "CREATE TABLE " + TABLE_TODO_ITEM +
                "(" +
                    KEY_TODO_ITEM_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                    KEY_TODO_DETAIL_TEXT + " TEXT" +
                ")";
        db.execSQL(CREATE_TODO_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_ITEM);
            onCreate(db);
        }
    }

    public void addItem(TodoListItem item) {
        long itemId = -1;

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_DETAIL_TEXT, item.text);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            item.id = db.insertOrThrow(TABLE_TODO_ITEM, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("[ADD]", "Error while trying to add todo item to database");
        } finally {
            db.endTransaction();
        }
    }

    public void updateItem(TodoListItem item) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_DETAIL_TEXT, item.text);

            int res = db.update(
                    TABLE_TODO_ITEM,
                    values,
                    KEY_TODO_ITEM_ID + "= ?", new String[]{String.valueOf(item.id)});
            if (res >= 1) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("[UPDATE]", "Error updating todo item");
        } finally {
            db.endTransaction();
        }
    }

    public List<TodoListItem> getAllItems() {
        List<TodoListItem> items = new ArrayList<TodoListItem>();

        String ITEMS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABLE_TODO_ITEM);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TodoListItem newItem = new TodoListItem();
                    newItem.text = cursor.getString(cursor.getColumnIndex(KEY_TODO_DETAIL_TEXT));
                    newItem.id = cursor.getLong(cursor.getColumnIndex(KEY_TODO_ITEM_ID));
                    items.add(newItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("[GET]", "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    public void deleteItem(TodoListItem item) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_DETAIL_TEXT, item.text);

            int res = db.delete(
                    TABLE_TODO_ITEM,
                    KEY_TODO_ITEM_ID + "= ?", new String[]{String.valueOf(item.id)});
            if (res >= 1) {
                db.setTransactionSuccessful();
            } else {
                Log.d("[DELETE]", "Nothing is affected.");
            }
        } catch (Exception e) {
            Log.d("[DELETE]", "Error updating todo item");
        } finally {
            db.endTransaction();
        }
    }
}
