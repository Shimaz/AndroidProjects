package com.bshlab.alumlist;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity {
	
	private ListData data;
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		setContentView(R.layout.layout_detail);
		
		
		Intent intent = this.getIntent();
		
		int id = intent.getExtras().getInt("id");
		
		
		DBAdapter dbAdapter = new DBAdapter(this);
		
		dbAdapter.createDatabase();
		dbAdapter.open();
		
		data = new ListData();
		data = dbAdapter.getDetailInfo(id);
		
		dbAdapter.close();
		
		
		
		
		TextView tv = (TextView)findViewById(R.id.tv_test);
		tv.setText("" + id);
		
		Button btnClose = (Button)findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});
		
	}
	
	
	@Override
	public void onBackPressed(){
		finish();
	
	}

}
