package com.example.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
 
public class SqlHandler {
 
 public static final String DATABASE_NAME = "moveon_database.db";
 public static final int DATABASE_VERSION = 3;
 Context context;
 SQLiteDatabase sqlDatabase;
 SqlDbHelper dbHelper;
 
 public SqlHandler(Context context) {
 
  dbHelper = new SqlDbHelper(context, DATABASE_NAME, null,DATABASE_VERSION);
  sqlDatabase = dbHelper.getWritableDatabase();
 }
 
 public void executeQuery(String query) {
  try {
 
   if (sqlDatabase.isOpen()) {
    sqlDatabase.close();
   }
 
   sqlDatabase = dbHelper.getWritableDatabase();
   sqlDatabase.execSQL(query);
 
  } catch (Exception e) {
 
   Log.e("SqlHandler=============", "database error at line 34");
  }
 
 }
 
 public Cursor selectQuery(String query) {
  Cursor c = null;
  try {
 
   if (sqlDatabase.isOpen()) {
    sqlDatabase.close();
 
   }
   sqlDatabase = dbHelper.getWritableDatabase();
   c = sqlDatabase.rawQuery(query, null);
 
  } catch (Exception e) {
 
   Log.e("SqlHandler====================","Database error @ line 52");
 
  }
  return c;
 
 }
 
}