package com.bshlab.alumlist;


import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	
	private ListData data;
	
	private ABClass abc;

	private ArrayList<String>BMList;

	private boolean isBookmarked;
	
	
	
	private Button btnBookmark;
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		setContentView(R.layout.layout_detail);
		abc = (ABClass)getApplicationContext();

		/*
		 * 
		 * 북마크 데이터 준비 
		 * 
		 */
		
		
		BMList = new ArrayList<String>();
		BMList = abc.getBookMarkData();
		
		isBookmarked = false;
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
		
		
		// 이름, 기수
		TextView tvName = (TextView)findViewById(R.id.tv_name);
		tvName.setText(data.getName());
		
		TextView tvNotation = (TextView)findViewById(R.id.tv_notation);
		tvNotation.setText(data.getNotation()+"기");
		
		
		// 핸드폰, 클릭시 통화
		TextView tvCP = (TextView)findViewById(R.id.tv_cp_number);
		tvCP.setText(data.getMobile());
		tvCP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!(data.getMobile()=="" || data.getMobile()==" ")){
				
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
				    callIntent.setData(Uri.parse("tel:" + data.getMobile()));
				    startActivity(callIntent);
				}
			    
			    
			    
				
			}
		});
		
		
		// 회사정보
		TextView tvOccu = (TextView)findViewById(R.id.tv_occupation);
		tvOccu.setText(data.getCompany());
		

		// Email
		TextView tvEmail = (TextView)findViewById(R.id.tv_email);
		tvEmail.setTag(data.getEmail());
		tvEmail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

				/* Fill it with Data */
				emailIntent.setType("text/plain");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{data.getEmail()});
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "제목");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "내용");

				/* Send it off to the Activity-Chooser */
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
				
			}
		});
		
		// 회사 주소, 회사 전화번호
		TextView tvCompanyAddress = (TextView)findViewById(R.id.tv_company_address);
		tvCompanyAddress.setText(data.getCompanyAddress());
		
		TextView tvCompanyPhone = (TextView)findViewById(R.id.tv_company_number);
		tvCompanyPhone.setTag(data.getCompanyNo());
		tvCompanyPhone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(!(data.getCompanyNo() == "" || data.getCompanyNo() == " ")){
					
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
				    callIntent.setData(Uri.parse("tel:" + data.getCompanyNo()));
				    startActivity(callIntent);
				}
			    
			    
				
			}
		});		
		
		
		// 집 주소, 집 전화번호 
		
		TextView tvHomeAddress = (TextView)findViewById(R.id.tv_home_address);
		tvHomeAddress.setText(data.getHomeAddress());
		
		TextView tvHomeNumber = (TextView)findViewById(R.id.tv_home_numberl);
		tvHomeNumber.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!(data.getHomeNumber() == "" || data.getHomeNumber() == " ")){
					
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
				    callIntent.setData(Uri.parse("tel:" + data.getHomeNumber()));
				    startActivity(callIntent);
				
				}
				
			}
		});
		
		btnBookmark = (Button)findViewById(R.id.btn_bookmark);
		btnBookmark.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				setBookmark();
				
			}
		});
		
		
		if(BMList.contains(""+data.getID())){
			btnBookmark.setBackgroundResource(R.drawable.btn_bookmark_on);
			isBookmarked = true;
		}
		
		
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
	
	
	
	private void setBookmark(){
	
		if(isBookmarked){
			
			BMList.remove(""+data.getID());
			
			btnBookmark.setBackgroundResource(R.drawable.btn_bookmark_off);
			isBookmarked = false;
		}else{
			
			BMList.add(""+data.getID());
			btnBookmark.setBackgroundResource(R.drawable.btn_bookmark_on);
			isBookmarked = true;
		}
		
		abc.saveBookMarkData(BMList);		
	}
	
	
//	private boolean checkBookmark(){
//		boolean retVal = false;
//		
//	
//		
//		return retVal;
//	}
	
	@Override
	public void onBackPressed(){
		finish();
	
	}

}
