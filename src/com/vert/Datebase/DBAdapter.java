package com.vert.Datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	private static final String DB_NAME = "RateInfo.db";
	private static final String DB_TABLE = "RateInfo";
	private static final int DB_VERSION = 1;
	 
	public static final String KEY_ID = "_id";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_RATE = "rate";
	public static final String KEY_DAY = "day";
	public static final String KEY_TIME = "time";
	
	private static SQLiteDatabase db;
	private final Context context;
	private DBOpenHelper dbOpenHelper;
	public static int flag = 0;
	
	public DBAdapter(Context _context) {
	    context = _context;
	  }

	  /** Close the database */
	  public void close() {
		  if (db != null){
			  db.close();
			  db = null;
		  }
		}

	  /** Open the database */
	  public void open() throws SQLiteException {  
		  dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
		  try {
			  db = dbOpenHelper.getWritableDatabase();
		  }
		  catch (SQLiteException ex) {
			  db = dbOpenHelper.getReadableDatabase();
		  }	  
		}
	  
	
	  public static long insert(SaveRate saveRate) {
	    ContentValues newValues = new ContentValues();
	    
	    queryAllData(saveRate);
	    if(saveRate.Rate == 0 || saveRate.Day.length() < 5 || saveRate.Time.length() < 5)
	    	return -1;
	    else if (flag == 1) {
	    	flag = 0;
	    	return -1;	    	
	    }
	    else {	  
	    	newValues.put(KEY_COUNTRY, saveRate.Country);
	    	newValues.put(KEY_RATE, saveRate.Rate);
	    	newValues.put(KEY_DAY, saveRate.Day);
	    	newValues.put(KEY_TIME, saveRate.Time);
	    
	    	return db.insert(DB_TABLE, null, newValues);
	    }
	  }

	  public static SaveRate[] queryAllData(SaveRate saveRate) {  
		  Cursor results = db.query(DB_TABLE, new String[] {KEY_ID, KEY_COUNTRY, KEY_RATE, KEY_DAY, KEY_TIME}, 
				  null, null, null, null, null, null);
		  return ConvertToRate(results, saveRate);   
	  }
	  
	  private static SaveRate[] ConvertToRate(Cursor cursor, SaveRate saveRate){
		  
			  int resultCounts = cursor.getCount();
			  SaveRate[] rates = new SaveRate[resultCounts];
		try {
			  if (resultCounts == 0 || !cursor.moveToFirst()){
				  return null;
			  }
			 
			  for (int i = 0; i<resultCounts; i++){
				  rates[i] = new SaveRate();
				  rates[i].ID = cursor.getInt(0);
				  rates[i].Country = cursor.getString(cursor.getColumnIndex(KEY_COUNTRY));
				  rates[i].Rate = cursor.getDouble(cursor.getColumnIndex(KEY_RATE));
				  rates[i].Day = cursor.getString(cursor.getColumnIndex(KEY_DAY));
				  rates[i].Time = cursor.getString(cursor.getColumnIndex(KEY_TIME));
			  
				  if(rates[i].Country.equals(saveRate.Country) && rates[i].Day.equals(saveRate.Day) && rates[i].Time.equals(saveRate.Time)) {
					  flag = 1;
				  }
			  
				  cursor.moveToNext();
			  }
			  
		  }catch(Exception e) {
			  e.printStackTrace();
		  }finally {
			  if(cursor != null) {
				  cursor.close();
			  }
		  }
		  return rates; 
	  }
	  
	  public SaveRate[] queryAllData() {  
		  Cursor results =  db.query(DB_TABLE, new String[] {KEY_ID, KEY_COUNTRY, KEY_RATE, KEY_DAY, KEY_TIME}, 
				  null, null, null, null, null, null);
		  return ConvertToRate(results);   
	  }
	  
	  private static SaveRate[] ConvertToRate(Cursor cursor){
		  int resultCounts = cursor.getCount();
		  SaveRate[] rates = new SaveRate[resultCounts];
		  try {
			  if (resultCounts == 0 || !cursor.moveToFirst()){
				  return null;
			  }
		  
			  for (int i = 0; i<resultCounts; i++){
				  rates[i] = new SaveRate();
				  rates[i].ID = cursor.getInt(0);
				  rates[i].Country = cursor.getString(cursor.getColumnIndex(KEY_COUNTRY));
				  rates[i].Rate = cursor.getDouble(cursor.getColumnIndex(KEY_RATE));
				  rates[i].Day = cursor.getString(cursor.getColumnIndex(KEY_DAY));
				  rates[i].Time = cursor.getString(cursor.getColumnIndex(KEY_TIME));
			  
				  cursor.moveToNext();
			  }	  
		  }catch(Exception e) {
			  e.printStackTrace();
		  }finally {
			  if(cursor != null) {
				  cursor.close();
			  }
		  }
		  return rates; 
	  }
	  
	  public static SaveRate[] queryOneData(String country) {  
		  int id = -1;
		  Cursor inputs =  db.query(DB_TABLE, new String[] {KEY_ID, KEY_COUNTRY, KEY_RATE, KEY_DAY, KEY_TIME}, 
				  null, null, null, null, null, null);
		  id = GetID(inputs, country);  
		  if(id != -1) {
			  Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_COUNTRY, KEY_RATE, KEY_DAY, KEY_TIME}, 
					  KEY_ID + "=" + id, null, null, null, null, null);
			  return ConvertToRate(results);
		  } else 
			  return null;
		  
	  }
	  
	  private static int GetID(Cursor cursor, String country){
		  
		  int id = -1;
		  int resultCounts = cursor.getCount();
		  SaveRate[] rates = new SaveRate[resultCounts];
	try {
		  if (resultCounts == 0 || !cursor.moveToFirst()){
			  return -1;
		  }
		 
		  for (int i = 0; i<resultCounts; i++){
			  rates[i] = new SaveRate();
			  rates[i].ID = cursor.getInt(0);
			  rates[i].Country = cursor.getString(cursor.getColumnIndex(KEY_COUNTRY));
			  rates[i].Rate = cursor.getDouble(cursor.getColumnIndex(KEY_RATE));
			  rates[i].Day = cursor.getString(cursor.getColumnIndex(KEY_DAY));
			  rates[i].Time = cursor.getString(cursor.getColumnIndex(KEY_TIME));
		  
			  if(rates[i].Country.equals(country) && rates[i].ID > id) {			
				  id = rates[i].ID;
			  }
		  
			  cursor.moveToNext();
		  }
		  
	  }catch(Exception e) {
		  e.printStackTrace();
	  }finally {
		  if(cursor != null) {
			  cursor.close();
		  }
	  }
	  return id; 
  }
	  
		/** 静态Helper类，用于建立、更新和打开数据库*/
	  private static class DBOpenHelper extends SQLiteOpenHelper {

		  public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
		    super(context, name, factory, version);
		  }

		  private static final String DB_CREATE = "create table " + 
				  DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
		    KEY_COUNTRY+ " text not null, " + KEY_RATE+ " double, " + KEY_DAY + " string, " + KEY_TIME + " string);";

		  @Override
		  public void onCreate(SQLiteDatabase _db) {
		    _db.execSQL(DB_CREATE);
		  }

		  @Override
		  public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {		    
		    _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		    onCreate(_db);
		  }
		}
	}