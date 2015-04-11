package com.bshlab.alumlist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

@SuppressLint("SdCardPath")
public class ABClass extends Application {

	public Integer[] bmList;
	private String filePath;
	
	
	public void onCreate(){
		
		super.onCreate();

		getBookMarkData();
		

	}
	
	
	
	public void saveBookMarkData(ArrayList<String> bml){
		
		String str = "";
		for(int i = 0; i < bml.size(); i++){
			if(i != 0) str += ",";
			str += bml.get(i);
		}
		
		File file = new File(filePath + "bmlist.csv");
		if(file.exists()) file.delete();
		
	    try {
	      
	       
	        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	        writer.write(str);
	        writer.close();
	      } catch (IOException e) {
	        android.util.Log.w("shimaz", e.getMessage(), e);
	   
	      }
		
		
	}
	
	public ArrayList<String> getBookMarkData(){
		File file;
		if(android.os.Build.VERSION.SDK_INT >= 17){
		       filePath = getApplicationInfo().dataDir + "/databases/";         
		    }
		    else
		    {
		       filePath = "/data/data/" + getPackageName() + "/databases/";
		    }
		
		file = new File(filePath + "bmlist.csv");
		
		if(file.exists()){
			String str = null;
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"), 8192);
				StringBuilder sb = new StringBuilder();

				
				str = br.readLine();
				
				br.close();
				sb.trimToSize();
				
				if(str != null){
					Object[] numbersAsString = str.split(",");
					bmList = new Integer[numbersAsString.length];
					
					for(int i = 0; i < bmList.length; i++){
						
						bmList[i] = Integer.parseInt((String)numbersAsString[i]);
					}
				}else{
					bmList = null;
				}
//		        bmList = Arrays.copyOf(numbersAsString, numbersAsString.length, Integer[].class);
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
		}
		
		ArrayList<String> retVal = new ArrayList<String>();
		
		if(bmList != null){
			for(int i = 0; i < bmList.length; i++){
				retVal.add("" + bmList[i]);
			}
		}
		return retVal;
	}
}
