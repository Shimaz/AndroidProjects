package com.pnf.pen.kobaco;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.pnf.bt.lib.PNFDefine;
import com.pnf.bt.lib.PNFPenController;

@SuppressLint("NewApi") 
public class BaseActivity extends Activity {
	protected final String TAG = "BaseActivity";
	
	private final String SYSTEM_DIALOG_REASON_KEY = "reason";
	private final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
	private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
	
	
	
	private final byte REQUEST_MAIN = 0X00;
	
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

		unregisterReceiver(homeKeyReceiver);
	}

	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
	}

	@Override
	public void onBackPressed()
	{
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		boolean isResultOK = resultCode == Activity.RESULT_OK?true:false;
		switch(requestCode)
		{
		case REQUEST_MAIN:
			if(isResultOK){
				new Thread(new Runnable() {
					public void run() {
						runOnUiThread(new Runnable(){
							@Override
							public void run() {
								
								System.exit(0);
							}
						});
					}
				}).start();
			}
			break;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.baseview);
		
		/*
    	 * LCD 크기 셋팅
    	 */
		Point LCDSize = new Point();
		((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(LCDSize);
		MainDefine.iDisGetWidth = (int)(800 * 1.6f); //LCDSize.x;
		MainDefine.iDisGetHeight =(int)(1280 * 1.6f); //LCDSize.y;
    	
    	/*
    	 * 홈키 리시버 셋팅
    	 */
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(homeKeyReceiver, intentFilter);
		
		/*
    	 * PNFBtLib 셋팅
    	 */
    	MainDefine.penController = new PNFPenController(getApplicationContext());
    	MainDefine.penController.setDefaultModelCode(PNFDefine.Equil);
    	MainDefine.penController.setConnectDelay(false);
    	MainDefine.penController.setProjectiveLevel(4);
    	MainDefine.penController.fixStationPosition(PNFDefine.DIRECTION_TOP);
    	MainDefine.penController.setCalibration(MainDefine.iDisGetWidth ,MainDefine.iDisGetHeight);
    	MainDefine.penController.startPen();
    	
//		Intent introIntent = new Intent(BaseActivity.this, MainActivity.class);
    	Intent introIntent = new Intent(BaseActivity.this, LeeumMainActivity.class);
//    	Intent introIntent = new Intent(BaseActivity.this, SelectActivity.class);
		startActivityForResult(introIntent, REQUEST_MAIN);
	}

	private BroadcastReceiver homeKeyReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
				String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
				if(reason != null){
					if(reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)){
						MainDefine.penController.DisconnectPen();
					}else if(reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)){
						MainDefine.penController.DisconnectPen();
					}
				}
			}
		}
	};
}
