package com.bshlab.alumlist;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	
	private ListData data;
	
	
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		setContentView(R.layout.layout_detail);
		

		
		/*
		 * 
		 * 데이터 준비 
		 * 
		 */
		
		Intent intent = this.getIntent();
		int id = intent.getExtras().getInt("id");
		DBAdapter dbAdapter = new DBAdapter(this);
		
		dbAdapter.createDatabase();
		dbAdapter.open();
		
		data = new ListData();
		data = dbAdapter.getDetailInfo(id);
		
		dbAdapter.close();
		
		// DB Data -> Content
		
		
		ImageView ivPhoto = (ImageView)findViewById(R.id.iv_detail_photo);
		
		ivPhoto.setImageBitmap(getPhoto());
		
		
		TextView tvName = (TextView)findViewById(R.id.tv_name);
		tvName.setText(data.getName());
		
		
		Button btnClose = (Button)findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});
		
	}
	
	
	private Bitmap getPhoto(){
		Bitmap bmp = null;
		
		
		
		if(data.getPhoto() == null || ((byte[])data.getPhoto()).length == 0 || ((byte[])data.getPhoto()).length == 1){ // no photo
		
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.img_no_photo);
		}else{
			bmp = BitmapFactory.decodeByteArray(data.getPhoto(), 0, ((byte[])data.getPhoto()).length);
		}
		
		return bmp;
	}
	
	
	@Override
	public void onBackPressed(){
		finish();
	
	}

}
