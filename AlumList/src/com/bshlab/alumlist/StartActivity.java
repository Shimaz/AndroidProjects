package com.bshlab.alumlist;

/*
 * 
 *  Splash Screen Activity
 *  BSH Lab
 * 
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class StartActivity extends Activity {

	private Animation fade;
	private ImageView ivLogo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_start);
		
		// 애니메이션 처리 후 바로 이동 
		fade = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);
		fade.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(StartActivity.this, InfoActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		
		ivLogo = (ImageView)findViewById(R.id.iv_logo);
		ivLogo.setBackgroundResource(R.drawable.img_intro);
		ivLogo.startAnimation(fade);
		
		
		
		
		
		
		
		
	}
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.start, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
