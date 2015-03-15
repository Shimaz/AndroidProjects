package com.bshlab.alumlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	
	
	//The Android's default system path of your application database.
	 private static String DB_PATH = "/data/data/com.bshlab.alumlist/databases/";
	 //private static String DB_PATH = "context.getApplicationInfo().dataDir/databases/";

	// Data Base Name.
	 private static final String DATABASE_NAME = "ssu_health_db";
	 // Data Base Version.
	 private static final int DATABASE_VERSION = 1;
	 // Table Names of Data Base.
	 static final String TABLE_Name = "alumlist";

	public Context context;
	private static SQLiteDatabase sqliteDB;
	
	public DBHelper(Context ctx, String name, CursorFactory factory, int version){
		super(ctx, name, factory, version);
		this.context = ctx;;
	}
	
	public DBHelper(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = ctx;;
	}
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		String sql = "drop table if exits student";
		db.execSQL(sql);
		
	}

}
