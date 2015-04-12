package com.bshlab.alumlist;


import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BookmarkActivity extends Activity {
	private DBAdapter dbAdapter;
	private ArrayList<ListData> bookmarkedList;
	private ListView lvList;
	
	private ListAdapter listAdapter;

	private Runnable updateUI;
	
	private ABClass abc;
	
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
		
		abc = (ABClass)getApplicationContext();
		
		String path = abc.getSSUFilePath();
		
		File file = new File(path +"ssu.cert");
		boolean isCertified = false;
		
		if(file.exists()) isCertified = true;
		if(isCertified){
			setContentView(R.layout.layout_bookmark);
			
			
			/*
			 * Initailize DB 
			 */
			
			dbAdapter = new DBAdapter(this);
			
			dbAdapter.createDatabase();
			dbAdapter.open();
			
			bookmarkedList = new ArrayList<ListData>();
			bookmarkedList = dbAdapter.getBookmarkList();
			
			dbAdapter.close();
			
			lvList = (ListView)findViewById(R.id.lv_list_bookmark);
			if(bookmarkedList.size() == 0){
				lvList.setVisibility(View.GONE);
			}else{
				TextView tvNodata = (TextView)findViewById(R.id.tv_no_data);
				tvNodata.setVisibility(View.GONE);
			}
			
			
			updateUI = new Runnable(){
				public void run(){
					BookmarkActivity.this.listAdapter.notifyDataSetChanged();
				}
			};
			
			listAdapter = new ListAdapter(this, R.layout.list_row, bookmarkedList);
			lvList.setAdapter(listAdapter);
			
			
			
			
		}else{
			
			
			setContentView(R.layout.layout_bookmark_no_cert);
			
		}
		
		
		/*
		 * menu button setup
		 */
		
		Button btnInfo = (Button)findViewById(R.id.btn_info);
		btnInfo.setOnClickListener(menuListener);
		
		Button btnList = (Button)findViewById(R.id.btn_list);
		btnList.setOnClickListener(menuListener);
		
		Button btnSettings = (Button)findViewById(R.id.btn_settings);
		btnSettings.setOnClickListener(menuListener);
	
		
		
		
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
					Intent intent = new Intent(BookmarkActivity.this, InfoActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "info");
					break;
			
				case R.id.btn_bookmark:
					Intent intent2 = new Intent(BookmarkActivity.this, BookmarkActivity.class);
					startActivity(intent2);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "bookmark");
					break;
					
				case R.id.btn_list:
					Intent intent3 = new Intent(BookmarkActivity.this, ListActivity.class);
					startActivity(intent3);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "list");
					break;
					
				case R.id.btn_settings:
					Intent intent4 = new Intent(BookmarkActivity.this, SettingsActivity.class);
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
	
	@Override
	public void onResume(){
		super.onResume();
		
		SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
		boolean isCertified = settings.getBoolean("cert", false);
		if(isCertified){
			dbAdapter.open();
			
			bookmarkedList = new ArrayList<ListData>();
			bookmarkedList = dbAdapter.getBookmarkList();
			
			dbAdapter.close();
			
			listAdapter = new ListAdapter(this, R.layout.list_row, bookmarkedList);
			lvList.setAdapter(listAdapter);
	
			if(bookmarkedList.size() == 0){
				lvList.setVisibility(View.GONE);
				TextView tvNodata = (TextView)findViewById(R.id.tv_no_data);
				tvNodata.setVisibility(View.VISIBLE);
				
			}else{
				TextView tvNodata = (TextView)findViewById(R.id.tv_no_data);
				tvNodata.setVisibility(View.GONE);
				lvList.setVisibility(View.VISIBLE);
			}
			
			updateUI.run();
		}
		
	}
	

}
