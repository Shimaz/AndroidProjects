package com.bshlab.alumlist;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ListActivity extends Activity {
	private DBAdapter dbAdapter;
	private ArrayList<ListData> allList;
	private ListView lvList;
	
	
	private ListAdapterWithSection listAdapter;

//	private ListAdapter la;
	
	private ABClass abc;
	
	private InputMethodManager imm;
	
	private Runnable updateUI;
	
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
		
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		abc = (ABClass)getApplicationContext();
		String path = abc.getSSUFilePath();
		
		File file = new File(path +"ssu.cert");
		boolean isCertified = false;
		
		if(file.exists()) isCertified = true;
		
		if(isCertified){
			setContentView(R.layout.layout_list);
			
			/*
			 * Initailize DB 
			 */
			
			dbAdapter = new DBAdapter(this);
			
			dbAdapter.createDatabase();
			dbAdapter.open();
			
			allList = new ArrayList<ListData>();
			allList = dbAdapter.getAllList();
			
			dbAdapter.close();
			
			
			
			/*
			 * Initialize ListView
			 */
			

			lvList = (ListView)findViewById(R.id.lv_list_all);
			updateUI = new Runnable(){
				public void run(){
					ListActivity.this.listAdapter.notifyDataSetChanged();
				}
			};
			
			listAdapter = new ListAdapterWithSection(this, R.layout.list_row, R.layout.list_section, allList);
			lvList.setAdapter(listAdapter);
			
//			la = new ListAdapter(this, R.layout.list_row, allList);
//			lvList.setAdapter(la);
			
			Button btnSearch = (Button)findViewById(R.id.btn_search);
			btnSearch.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					EditText et = (EditText)findViewById(R.id.et_search);
					String keyword = et.getText().toString();
					
					
					imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
					
					updateList(keyword);
				}
			});
			
			
		}else{
			
			setContentView(R.layout.layout_list_no_cert);
			
			
		}
		
		
		
		
		
		
		/*
		 * menu button setup
		 */
		
		Button btnInfo = (Button)findViewById(R.id.btn_info);
		btnInfo.setOnClickListener(menuListener);
		
		Button btnBookmark = (Button)findViewById(R.id.btn_bookmark);
		btnBookmark.setOnClickListener(menuListener);
		
		Button btnSettings = (Button)findViewById(R.id.btn_settings);
		btnSettings.setOnClickListener(menuListener);
		

		
	
		
		
		
	}
	
	
	
	
	private void updateList(String str){
		
		
		
//		ArrayList<ListData> tmpList = new ArrayList<ListData>();
		
		
		allList = new ArrayList<ListData>();
		
		
		dbAdapter.open();
		allList = dbAdapter.getSearchList(str);
		dbAdapter.close();
		
		listAdapter = new ListAdapterWithSection(this, R.layout.list_row, R.layout.list_section, allList);
		lvList.setAdapter(listAdapter);
		

		updateUI.run();
		
//		listAdapter.notifyDataSetChanged();
		
		
	}
	
	
	
	
	/*
	 * Button Listener for menu buttons
	 */
	private OnClickListener menuListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			
				case R.id.btn_info:
					Intent intent = new Intent(ListActivity.this, InfoActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "info");
					break;
			
				case R.id.btn_bookmark:
					Intent intent2 = new Intent(ListActivity.this, BookmarkActivity.class);
					startActivity(intent2);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "bookmark");
					break;
					
				case R.id.btn_list:
					Intent intent3 = new Intent(ListActivity.this, ListActivity.class);
					startActivity(intent3);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "list");
					break;
					
				case R.id.btn_settings:
					Intent intent4 = new Intent(ListActivity.this, SettingsActivity.class);
					startActivity(intent4);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "settings");
					break;
				
				default:
					break;
					
			
			}
			
		}
	};
	

}
