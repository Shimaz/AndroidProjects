package com.bshlab.alumlist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	
	
	//The Android's default system path of your application database.
	 private static String DB_PATH; //= "/data/data/com.bshlab.alumlist/databases/";
	 //private static String DB_PATH = "context.getApplicationInfo().dataDir/databases/";

	// Data Base Name.
//	 private static final String DATABASE_NAME = "ssu_health_db.sqlite";
	 private static final String DATABASE_NAME = "ssu_health_pw.sqlite";
	 
	 // Data Base Version.
	 private static final int DATABASE_VERSION = 1;
	 // Table Names of Data Base.
	 static final String TABLE_Name = "alumlist";

	public Context context;
	private SQLiteDatabase sqliteDB;
	
	public DBHelper(Context ctx, String name, CursorFactory factory, int version){
		super(ctx, name, factory, version);
		this.context = ctx;;
	}
	
	public DBHelper(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		
	    if(android.os.Build.VERSION.SDK_INT >= 17){
	       DB_PATH = ctx.getApplicationInfo().dataDir + "/databases/";         
	    }
	    else
	    {
	       DB_PATH = "/data/data/" + ctx.getPackageName() + "/databases/";
	    }
		
		this.context = ctx;;
	}
	
	
	public void createDataBase() throws IOException
	{
	    //If database not exists copy it from the assets

	    boolean mDataBaseExist = checkDataBase();
	    if(!mDataBaseExist)
	    {
	        this.getReadableDatabase();
	        this.close();
	        try 
	        {
	            //Copy the database from assests
	            copyDataBase();
	            android.util.Log.e("shimaz", "createDatabase database created");
	        } 
	        catch (IOException mIOException) 
	        {
	            throw new Error("ErrorCopyingDataBase" + mIOException.toString());
	        }
	    }
	}
	//Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        InputStream mInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DATABASE_NAME;
        //Log.v("mPath", mPath);
        sqliteDB = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return sqliteDB != null;
    }

    @Override
    public synchronized void close() 
    {
        if(sqliteDB != null)
            sqliteDB.close();
        super.close();
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
