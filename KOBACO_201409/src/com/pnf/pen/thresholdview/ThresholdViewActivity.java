package com.pnf.pen.thresholdview;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pnf.pen.kobaco.R;

public class ThresholdViewActivity extends Activity {
	TextView thresholdSeekBarValueTextView;
	SeekBar thresholdSeekBar;
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
	}
	
	@Override
	public void onBackPressed()
	{
		setResult(RESULT_CANCELED,null);
		finish();
		return;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if(keyCode == KeyEvent.KEYCODE_MENU){
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.threshold);
		
		thresholdSeekBarValueTextView = (TextView)findViewById(R.id.thresholdSeekBarValueTextView);
		thresholdSeekBar = (SeekBar)findViewById(R.id.thresholdSeekBar);
	}
	
	public void backBtnClicked(View v){
		setResult(RESULT_CANCELED,null);
		finish();
	}
	
	public void clearBtnClicked(View v){
		
	}
}
