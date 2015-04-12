package com.bshlab.alumlist;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	
	private ABClass abc;
	
	
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
		setContentView(R.layout.layout_settings);
		abc = (ABClass)getApplicationContext();
		
		Button btnCert = (Button)findViewById(R.id.btn_cert);
		btnCert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(SettingsActivity.this, CertifyActivity.class);
				startActivity(intent);
				
				
			}
		});
		
		
		
		/*
		 * menu button setup
		 */
		
		Button btnInfo = (Button)findViewById(R.id.btn_info);
		btnInfo.setOnClickListener(menuListener);
		
		Button btnList = (Button)findViewById(R.id.btn_list);
		btnList.setOnClickListener(menuListener);
		
		Button btnBookmark = (Button)findViewById(R.id.btn_bookmark);
		btnBookmark.setOnClickListener(menuListener);
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
					Intent intent = new Intent(SettingsActivity.this, InfoActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "info");
					break;
			
				case R.id.btn_bookmark:
					Intent intent2 = new Intent(SettingsActivity.this, BookmarkActivity.class);
					startActivity(intent2);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "bookmark");
					break;
					
				case R.id.btn_list:
					Intent intent3 = new Intent(SettingsActivity.this, ListActivity.class);
					startActivity(intent3);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "list");
					break;
					
				case R.id.btn_settings:
					Intent intent4 = new Intent(SettingsActivity.this, SettingsActivity.class);
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
		
		String path = abc.getSSUFilePath();
		
		File file = new File(path +"ssu.cert");
		boolean isCertified = false;
		
		if(file.exists()) isCertified = true;
		
		if(isCertified){
			TextView tv = (TextView)findViewById(R.id.tv_cert_don);
			tv.setVisibility(View.VISIBLE);
			
			
			LinearLayout ll = (LinearLayout)findViewById(R.id.ll_no_cert);
			ll.setVisibility(View.GONE);
//			Button btn = (Button)findViewById(R.id.btn_cert);
//			btn.setVisibility(View.GONE);
			
		}else{
			TextView tv = (TextView)findViewById(R.id.tv_cert_don);
			tv.setVisibility(View.GONE);
			
			
			LinearLayout ll = (LinearLayout)findViewById(R.id.ll_no_cert);
			ll.setVisibility(View.VISIBLE);
//			Button btn = (Button)findViewById(R.id.btn_cert);
//			btn.setVisibility(View.VISIBLE);
			
		}
		
		
	}

}
