package com.pnf.pen.timetestview;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pnf.pen.kobaco.MainDefine;
import com.pnf.pen.kobaco.R;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class TimeTestViewActivity extends Activity {
	protected final String TAG = "TimeTestViewActivity";

	TextView startTimeValueTextView;
	TextView endTimeValueTextView;
	TextView durationValueTextView;
	
	boolean isHasFocus = false;
	
	long startTimeValue;
	long endTimeValue;
	long durationTimeValue;

	@Override
	protected void onResume() {
		super.onResume();
		
		MainDefine.penController.SetRetObj(penHandler);
		MainDefine.penController.SetRetObjForEnv(null);
		MainDefine.penController.SetRetObjForMsg(null);
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
    public void onWindowFocusChanged(boolean hasFocus) {
    	isHasFocus = hasFocus;
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

		setContentView(R.layout.timetest);

		startTimeValueTextView = (TextView)findViewById(R.id.startTimeValueTextView);
		endTimeValueTextView = (TextView)findViewById(R.id.endTimeValueTextView);
		durationValueTextView = (TextView)findViewById(R.id.durationValueTextView);
	}
	

	public void startBtnClicked(View v){
		startTimeValueTextView.setText(GetCurrentTimeToString());
		startTimeValue = GetCurrentTimeToLong();
	}

	public void closeBtnClicked(View v){
		setResult(RESULT_CANCELED,null);
		finish();
	}
	
	public String GetCurrentTimeToString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
	}
	
	public long GetCurrentTimeToLong()
	{
		Date date = new Date();
		long retVal = date.getTime();
		retVal = (retVal/1000);
		return retVal;
	}
	
	protected Handler penHandler = new Handler() 
	{        
		@Override
		public void handleMessage(Message msg) 
		{
			onPenEvent(msg.what ,msg.arg1 ,msg.arg2 ,msg.obj);
		}
	};
	
	int temperatureCnt = 0;
	protected void onPenEvent(int what, int RawX, int RawY ,Object obj)
	{
		if(!isHasFocus) return;
		
		calcTime();
	}
	
	private void calcTime()
	{
		String startTime = startTimeValueTextView.getText().toString();
		
		if (startTime.isEmpty()) {
			endTimeValueTextView.setText("");
			durationValueTextView.setText("");
	        return;
	    }
		
		endTimeValueTextView.setText(GetCurrentTimeToString());
		endTimeValue = GetCurrentTimeToLong();
		
		long ti = endTimeValue - startTimeValue;
		int seconds = (int)(ti % 60);
		int minutes = (int)((ti / 60) % 60);
		int hours = (int)(ti / 3600);
		
		durationValueTextView.setText(String.format("%02d:%02d:%02d", hours ,minutes ,seconds));
	}
}
