package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.models.Category;
import com.example.myapplication.models.Item;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "enter_product";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_ITEM = "item";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_CATEGORY_ID= "category_id";
    private static final String KEY_NOTE = "note";

    private static final String TABLE_CATEGORY = "category";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_item_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT,%s TEXT, %s TEXT)", TABLE_ITEM, KEY_ID, KEY_NAME, KEY_PRICE, KEY_QUANTITY,KEY_CATEGORY_ID,KEY_NOTE);
        sqLiteDatabase.execSQL(create_item_table);
        String create_category_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT)", TABLE_CATEGORY, KEY_ID, KEY_NAME);
        sqLiteDatabase.execSQL(create_category_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_item_table = String.format("DROP TABLE IF EXISTS %s", TABLE_ITEM);
        sqLiteDatabase.execSQL(drop_item_table);
        String drop_category_table = String.format("DROP TABLE IF EXISTS %s", TABLE_CATEGORY);
        sqLiteDatabase.execSQL(drop_category_table);
        onCreate(sqLiteDatabase);
    }
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_PRICE, String.valueOf(item.getPrice()));
        values.put(KEY_QUANTITY, String.valueOf(item.getQuantity()));
        values.put(KEY_CATEGORY_ID, item.getCategoryId());
        values.put(KEY_NOTE, item.getNote());
        db.insert(TABLE_ITEM, null, values);
        db.close();
    }
    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, category.getName());
        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }
    public List<Item> getAllItems() {
        List<Item>  items = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Item item = new Item(String.valueOf(cursor.getInt(0)), cursor.getString(1), Float.valueOf(cursor.getString(2)), Integer.valueOf(cursor.getString(3)),cursor.getString(4),cursor.getString(5));
            items.add(item);
            cursor.moveToNext();
        }
        return items;
    }
    public List<Category> getAllCategory() {
        List<Category>  categories = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Category category = new Category(String.valueOf(cursor.getInt(0)), cursor.getString(1));
            category.setItems(getItemsByCategoryId(cursor.getInt(0)));
            categories.add(category);
            cursor.moveToNext();
        }
        return categories;
    }
    public List<Item> getItemsByCategoryId(int categoryId) {
        List<Item>  items = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ITEM +" WHERE "+ KEY_CATEGORY_ID + "=" +categoryId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Item item = new Item(String.valueOf(cursor.getInt(0)), cursor.getString(1), Float.valueOf(cursor.getString(2)), Integer.valueOf(cursor.getString(3)),cursor.getString(4),cursor.getString(5));
            items.add(item);
            cursor.moveToNext();
        }
        return items;
    }
    public Category getCategoryById(int categoryId) {
        List<Category>  categories = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CATEGORY +" WHERE "+ KEY_ID + "=" +categoryId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Category category = new Category(String.valueOf(cursor.getInt(0)), cursor.getString(1));
            category.setItems(getItemsByCategoryId(cursor.getInt(0)));
            categories.add(category);
            cursor.moveToNext();
        }
        if(categories.size() > 0){
            return categories.get(0);
        } else {
            return null;
        }
    }
    public Item getItemById(int itemId) {
        List<Item>  items = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ITEM +" WHERE "+ KEY_ID + "=" +itemId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Item item = new Item(String.valueOf(cursor.getInt(0)), cursor.getString(1), Float.valueOf(cursor.getString(2)), Integer.valueOf(cursor.getString(3)),cursor.getString(4),cursor.getString(5));
            items.add(item);
            cursor.moveToNext();
        }
        if(items.size() > 0){
            return items.get(0);
        } else {
            return null;
        }
    }
    public void deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEM, KEY_ID + " = ?", new String[] { String.valueOf(itemId) });
        db.close();
    }
    public void deleteCategory(int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_ID + " = ?", new String[] { String.valueOf(categoryId) });
        db.close();
    }
    public void updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_QUANTITY, String.valueOf(item.getQuantity()));
        values.put(KEY_PRICE, String.valueOf(item.getPrice()));
        values.put(KEY_NOTE, item.getNote());
        values.put(KEY_CATEGORY_ID, item.getCategoryId());
        db.update(TABLE_ITEM, values, KEY_ID + " = ?", new String[] { String.valueOf(item.getItemId()) });
        db.close();
    }
    public void updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, category.getName());
        db.update(TABLE_CATEGORY, values, KEY_ID + " = ?", new String[] { String.valueOf(category.getCategoryId()) });
        db.close();
    }
}
