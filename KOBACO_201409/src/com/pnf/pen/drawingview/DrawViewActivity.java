package com.pnf.pen.drawingview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pnf.bt.lib.PNFDefine;
import com.pnf.bt.lib.PenDataClass;
import com.pnf.pen.kobaco.MainDefine;
import com.pnf.pen.kobaco.R;

@SuppressLint("NewApi")
public class DrawViewActivity extends Activity {
	protected final String TAG = "DrawViewActivity";
	
	ImageView drawViewBGImgView;
	DrawView drawView;
	

	
	boolean isHasFocus = false;
	
	@Override
	protected void onResume() {
		super.onResume();
		
		MainDefine.penController.SetRetObj(penHandler);
		MainDefine.penController.SetRetObjForEnv(null);
		MainDefine.penController.SetRetObjForMsg(messageHandler);
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
		Intent intent = new Intent();
		intent.putExtra("debug", debugStr);
		
		setResult(RESULT_OK,intent);
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
		
		setContentView(R.layout.drawview);
		
		drawViewBGImgView = (ImageView) findViewById(R.id.drawViewBGImgView);
		drawView = (DrawView) findViewById(R.id.drawView);
	}
	
	
	public void ClearAllBtnClicked(View v){
		drawView.btnEraser();
	}

	public void CloseBtnClicked(View v){
		Intent intent = new Intent();
		intent.putExtra("debug", debugStr);
		
		setResult(RESULT_OK,intent);
		finish();
	}
	
	protected Handler penHandler = new Handler() 
	{        
		@Override
		public void handleMessage(Message msg) 
		{
			onPenEvent(msg.what ,msg.arg1 ,msg.arg2 ,msg.obj);
		}
	};
	
	protected void onPenEvent(int what, int RawX, int RawY ,Object obj)
	{
		if(!isHasFocus) return;
		
		PointF pos = MainDefine.penController.GetCoordinatePostionXY(RawX,RawY);
		
		switch(what)
		{
		case PNFDefine.PEN_DOWN:
			drawView.DoMouseDown(pos.x, pos.y);
			break;
		case PNFDefine.PEN_MOVE:
			drawView.DoMouseDragged(pos.x, pos.y);
			drawView.invalidatePath();
			break;
		case PNFDefine.PEN_UP:
			drawView.DoMouseUp(pos.x ,pos.y);
			drawView.invalidatePath();
			break;
			
			
		case PNFDefine.PEN_HOVER:
			break;
		case PNFDefine.PEN_HOVER_DOWN:
			break;
		case PNFDefine.PEN_HOVER_MOVE:
			break;
			
		}
	}
	
	protected Handler penEnvHandler = new Handler() 
	{        
		@Override
		public void handleMessage(Message msg) 
		{
			onPenEnvEvent(msg.what ,msg.arg1 ,msg.arg2 ,msg.obj);
		}
	};
	
	protected void onPenEnvEvent(int what, int RawX, int RawY ,Object obj)
	{
		if(!isHasFocus) return;
		
		PenDataClass penData = (PenDataClass)obj;
		
	}
	
	protected Handler messageHandler = new Handler() 
	{        
		@Override
		public void handleMessage(Message msg) 
		{
			onMessageEvent(msg.what ,msg.arg1 ,msg.arg2 ,msg.obj);
		}
	};
	
	int penErrorCnt = 0;
	protected void onMessageEvent(int what, int RawX, int RawY ,Object obj)
	{
		if(!isHasFocus) return;
		
		if(what == PNFDefine.PNF_MSG_FAIL_LISTENING){
			return;
		}else if(what == PNFDefine.PNF_MSG_CONNECTED){
			addDebugText("PNF_MSG_CONNECTED");
			
			penErrorCnt = 0;
		}
		else if(what == PNFDefine.PNF_MSG_INVALID_PROTOCOL){
			return;
		}
		
		else if(what == PNFDefine.PNF_MSG_SESSION_CLOSED){
			addDebugText("PNF_MSG_SESSION_CLOSED");
		}
		else if(what == PNFDefine.PNF_MSG_PEN_RMD_ERROR){
			penErrorCnt++;
			if (penErrorCnt > 5) {
				Toast.makeText(
						getApplicationContext(),
						MainDefine.getLangString(getApplicationContext() ,R.string.PEN_RMD_ERROR_MSG),
						Toast.LENGTH_SHORT)
					.show();
				penErrorCnt = 0;
			}
			return;
		}
		else if(what == PNFDefine.PNF_MSG_FIRST_DATA_RECV){
			addDebugText("PNF_MSG_FIRST_DATA_RECV");
		}
		else if(what == PNFDefine.GESTURE_CIRCLE_CLOCKWISE){
			addDebugText("GESTURE_CIRCLE_CLOCKWISE");
			return;
		}
		else if(what == PNFDefine.GESTURE_CIRCLE_COUNTERCLOCKWISE){
			addDebugText("GESTURE_CIRCLE_COUNTERCLOCKWISE");
			return;
		}
		else if(what == PNFDefine.GESTURE_CLICK){
			addDebugText("GESTURE_CLICK");
			return;
		}
		else if(what == PNFDefine.GESTURE_DOUBLECLICK){
			addDebugText("GESTURE_DOUBLECLICK");
			return;
		}
	}
	
	String debugStr = "";
	void addDebugText(String text) {
    	String orgText = debugStr;
    	String inputText = "";
    	if(orgText.isEmpty()){
    		inputText = text;
    	}else{
    		inputText = orgText + "\n" + text;
    	}
    	debugStr = inputText;
    }
}
