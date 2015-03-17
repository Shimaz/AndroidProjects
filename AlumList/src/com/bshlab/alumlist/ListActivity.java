package com.bshlab.alumlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {
	private DBAdapter dbAdapter;
	private ArrayList<ListData> allList;
	private ListView lvList;
	
	private ListAdapter listAdapter;
	
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
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
//		final Runnable updateUI = new Runnable(){
//			public void run(){
//				ListActivity.this.listAdapter.notifyDataSetChanged();
//			}
//		};
		
		
		listAdapter = new ListAdapter(this, R.layout.list_row, allList);
		
		lvList.setAdapter(listAdapter);
		
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
	
	/*
	 * ListView Adapter  
	 */
	
	public class ListAdapter extends ArrayAdapter<ListData>{
		
		private ArrayList<ListData> arrList;
		private Context context;
		private int rowID;
		
		public ListAdapter(Context context, int rID, ArrayList<ListData> arrList){
			super(context, rID, arrList);
			this.context = context;
			this.arrList = arrList;
			this.rowID = rID;
		}
		
		
	
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View v = convertView;
			if(v == null){
				LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(this.rowID, null);
				
			}
			
			final ListData data = arrList.get(position);
			if(data != null){
				TextView tvName = (TextView)v.findViewById(R.id.tv_row_name);
				TextView tvCompany = (TextView)v.findViewById(R.id.tv_row_company);
				Button btnCall = (Button)v.findViewById(R.id.btn_list_call);
				Button btnEmail = (Button)v.findViewById(R.id.btn_list_emaill);
				if(tvName != null) tvName.setText(data.getName());
				if(tvCompany != null) tvCompany.setText(data.getCompany());
				btnEmail.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 /* Create the Intent */
						final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

						/* Fill it with Data */
						emailIntent.setType("text/plain");
						emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{data.getEmail()});
						emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "제목");
						emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "내용");

						/* Send it off to the Activity-Chooser */
						context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
						
						
					}
				});
				
				btnCall.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent callIntent = new Intent(Intent.ACTION_DIAL);
					    callIntent.setData(Uri.parse("tel:" + data.getMobile()));
					    
					    
					    
					    startActivity(callIntent);
						
					}
				});
				
			}
			
			
			return v;
		}
	
	
	}
	

}
