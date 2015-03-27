package com.bshlab.alumlist;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter 
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    public DBAdapter(Context context) 
    {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
    }

    public DBAdapter createDatabase() throws SQLException 
    {
        try 
        {
            mDbHelper.createDataBase();
        } 
        catch (IOException mIOException) 
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DBAdapter open() throws SQLException 
    {
        try 
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } 
        catch (SQLException mSQLException) 
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() 
    {
        mDbHelper.close();
    }

     public Cursor getTestData()
     {
         try
         {
             String sql ="SELECT * FROM alumlist";

             Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     
     public ArrayList<ListData> getAllList(){
    	 
    	 ArrayList<ListData> retList = new ArrayList<ListData>();
    	 
    	 String query = "SELECT * FROM alumlist";
    	 Cursor cur = mDb.rawQuery(query, null);
    	 int oldNote = 0;
    	 if(cur.moveToFirst()){
    		 do{
    			 ListData list = new ListData();
    			 
    			 int currentNote = cur.getInt(1);
    			 if(currentNote != oldNote){
    				 list.setIsHeader(true);
    				 
    				 list.setID(cur.getInt(0));
        			 list.setNotation(cur.getInt(1));
        			 list.setName(cur.getString(2));
        			 list.setMobile(cur.getString(3));
        			 list.setCompany(cur.getString(4));
        			 list.setEmail(cur.getString(7));
        			 retList.add(list);
        			 
        			 oldNote = currentNote;
        			 cur.moveToPrevious();
    			 }else{
    				 
    				 list.setIsHeader(false);
    				 
    				 list.setID(cur.getInt(0));
        			 list.setNotation(cur.getInt(1));
        			 list.setName(cur.getString(2));
        			 list.setMobile(cur.getString(3));
        			 list.setCompany(cur.getString(4));
        			 list.setEmail(cur.getString(7));
        			 retList.add(list);
        			 
        			 
    				 
    			 }
    			 
    			 
    		 } while (cur.moveToNext());
    		 
    	 }
    	 
    	 
    	 return retList;
     }
     
     public ArrayList<ListData> getSearchList(String keyword){
    	 ArrayList<ListData> retList = new ArrayList<ListData>();
    	 
    	 String query = "SELECT * FROM alumlist where name like " + "\"%" + keyword + "%\"" + " or company_name like " + "\"%" + keyword + "%\"" + " or company_address like " + "\"%" + keyword + "%\"" + " or home_address like " + "\"%" + keyword + "%\"";
    	 Cursor cur = mDb.rawQuery(query, null);
    	 int oldNote = 0;
    	 if(cur.moveToFirst()){
    		 do{
    			 ListData list = new ListData();
    			 
    			 int currentNote = cur.getInt(1);
    			 if(currentNote != oldNote){
    				 list.setIsHeader(true);
    				 
    				 list.setID(cur.getInt(0));
        			 list.setNotation(cur.getInt(1));
        			 list.setName(cur.getString(2));
        			 list.setMobile(cur.getString(3));
        			 list.setCompany(cur.getString(4));
        			 list.setEmail(cur.getString(7));
        			 retList.add(list);
        			 
        			 oldNote = currentNote;
        			 cur.moveToPrevious();
    			 }else{
    				 
    				 list.setIsHeader(false);
    				 
    				 list.setID(cur.getInt(0));
        			 list.setNotation(cur.getInt(1));
        			 list.setName(cur.getString(2));
        			 list.setMobile(cur.getString(3));
        			 list.setCompany(cur.getString(4));
        			 list.setEmail(cur.getString(7));
        			 retList.add(list);
        			 
        			 
    				 
    			 }
    			 
    			 
    		 } while (cur.moveToNext());
    		 
    	 }
    	 
    	 
    	 
    	 return retList;
    	 
     }
     
     
     
}