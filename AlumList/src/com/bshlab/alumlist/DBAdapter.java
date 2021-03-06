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
     
     
     public ListData getDetailInfo(int nID){
    	 ListData list = new ListData();
    	 
    	 String query = "SELECT * FROM alumlist where ID=" + nID;
    	 Cursor cur = mDb.rawQuery(query, null);
    	 cur.moveToFirst();
    	 list.setID(cur.getInt(0));
    	 list.setNotation(cur.getInt(1));
    	 list.setName(cur.getString(2));
    	 list.setMobile(cur.getString(3));
    	 list.setCompany(cur.getString(4));
    	 list.setCompanyAddress(cur.getString(5));
    	 list.setCompanyNo(cur.getString(6));
    	 list.setEmail(cur.getString(7));
    	 list.setHomeAddress(cur.getString(8));
    	 list.setHomeNumber(cur.getString(9));
    	 list.setPhoto(cur.getBlob(16));
    	 
    	 
    	 
    	 
    	 return list;
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
     
     
     public ArrayList<ListData> getBookmarkList(){
    	 ArrayList<ListData> retList = new ArrayList<ListData>();
    	 
    	 ABClass abc = (ABClass)mContext.getApplicationContext();
    	 ArrayList<String>bml = new ArrayList<String>();
    	 bml = abc.getBookMarkData();
    	 
    	 for(int i = 0; i < bml.size(); i++){
    		 String qq = "SELECT * FROM alumlist where ID="+bml.get(i);
    		 Cursor cc = mDb.rawQuery(qq, null);
    		 cc.moveToFirst();
    		 ListData dd = new ListData();
    		 dd.setID(cc.getInt(0));
			 dd.setNotation(cc.getInt(1));
			 dd.setName(cc.getString(2));
			 dd.setMobile(cc.getString(3));
			 dd.setCompany(cc.getString(4));
			 dd.setEmail(cc.getString(7));
			 retList.add(dd);
			 
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
     
     
     public boolean getCertification(int notation, String name, String pw){
    	 boolean retVal = false;
    	 
    	 
    	 String qq = "SELECT * FROM alumlist where notation=" + notation + " and name=\"" + name + "\" and pw1=\"" + pw.charAt(0) + "\" and pw2=\"" + pw.charAt(1) +"\" and pw3=\"" + pw.charAt(2) + "\" and pw4=\"" + pw.charAt(3) + "\" and pw5=\"" + pw.charAt(4) +"\" and pw6=\"" + pw.charAt(5) +"\"";    
		 Cursor cc = mDb.rawQuery(qq, null);
		 cc.moveToFirst();
		
		 if(cc.getCount() > 0) retVal = true;
    	 
    	 
    	 return retVal;
     }
     
     
     
}