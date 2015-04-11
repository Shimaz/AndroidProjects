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
import java.util.Arrays;


import android.annotation.SuppressLint;
import android.app.Application;

@SuppressLint("SdCardPath")
public class ABClass extends Application {

	public Integer[] bmList;
	private String filePath;
	
	public void onCreate(){
		
		super.onCreate();
		
		
		
		File file;
		if(android.os.Build.VERSION.SDK_INT >= 17){
		       filePath = getApplicationInfo().dataDir + "/databases/";         
		    }
		    else
		    {
		       filePath = "/data/data/" + getPackageName() + "/databases/";
		    }
		
		file = new File(filePath + "bmlist.csv");
		
		makeTestData();
		
		
		
		if(file.exists()){
			String str = null;
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"), 8192);
				StringBuilder sb = new StringBuilder();

				
				str = br.readLine();
				
				br.close();
				sb.trimToSize();
				
				Object[] numbersAsString = str.split(",");
				bmList = new Integer[numbersAsString.length];
				
				for(int i = 0; i < bmList.length; i++){
					
					bmList[i] = Integer.parseInt((String)numbersAsString[i]);
				}
				
//		        bmList = Arrays.copyOf(numbersAsString, numbersAsString.length, Integer[].class);
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
		}
		
		
		
		
	}
	
	
	private void makeTestData(){
		ArrayList<String>testData = new ArrayList<String>();
		testData.add("1");
		testData.add("55");
		testData.add("195");
		testData.add("478");
		testData.add("999");
		
		saveBookMarkData(testData);
		
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
		ArrayList<String> retVal = new ArrayList<String>();
		for(int i = 0; i < bmList.length; i++){
			retVal.add("" + bmList[i]);
		}
		return retVal;
	}
}
