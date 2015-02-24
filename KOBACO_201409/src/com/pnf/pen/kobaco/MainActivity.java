package com.pnf.pen.kobaco;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.pnf.bt.lib.PNFDefine;
import com.pnf.bt.lib.PenDataClass;
import com.pnf.pen.calibration.CalibrationPointActivity;
import com.pnf.pen.drawingview.DrawViewActivity;
import com.pnf.pen.popup.PenSleepView;
import com.pnf.pen.thresholdview.ThresholdViewActivity;
import com.pnf.pen.timetestview.TimeTestViewActivity;

@SuppressLint({ "NewApi", "HandlerLeak" }) 
public class MainActivity extends Activity {
	protected final String TAG = "MainActivity";
	
	private final byte REQUEST_DRAWVIEW = 0x00;
	private final byte REQUEST_CALIBRATIONVIEW = 0x01;
	private final byte REQUEST_TIMETESTVIEW = 0x02;
	private final byte REQUEST_THRESHOLDVIEW = 0x03;
	
	private final byte ALERTVIEW_FAIL_LISTENING = 0x00;
	private final byte ALERTVIEW_APP_EXIT = 0x01;
	
	private final byte SHOWPOPUP_PEN_CONNECT = 0x00;
	private final byte SHOWPOPUP_PEN_DISCONNECT = 0x01;
	private final byte SHOWPOPUP_PEN_ALIVE = 0x02;
	
	ScrollView writeLogScrollView;
	TextView writeLogTextView;

	TextView sensorDisValueTextView;
	TextView irGapValueTextView;

	TextView modelCodeValueTextView;
	TextView hwVerValueTextView;
	TextView mcu1VerValueTextView;
	TextView aliveSecValueTextView;
	TextView mcu2VerValueTextView;
	TextView temperatureValueTextView;
	TextView packetCountValueTextView;
	TextView errXCntValueTextView;
	TextView errYCntValueTextView;
	TextView pressureValueTextView;
	TextView curThresholdValueTextView;
	TextView statusValueTextView;
	TextView rawXValueTextView;
	TextView rawYValueTextView;
	TextView convXValueTextView;
	TextView convYValueTextView;
	
	TextView downCountValueTextView;
	TextView moveCountValueTextView;
	TextView upCountValueTextView;
	TextView totalCountValueTextView;
	TextView kbyteValueTextView;
	TextView byteValueTextView;
	
	boolean isHasFocus = false;
	int penErrorCnt = 0;
	int packetCnt = 0;
	int downCnt = 0;
	int moveCnt = 0;
	int upCnt = 0;
	int errCntX = 0;
	int errCntY = 0;
	int beforeRawX = -1;
	int beforeRawY = -1;
	
	@Override
	protected void onResume() {
		super.onResume();
		
		MainDefine.penController.SetRetObj(penHandler);
		MainDefine.penController.SetRetObjForEnv(penEnvHandler);
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
		showAlertView(ALERTVIEW_APP_EXIT);
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
		String debugStr = "";
		
		switch(requestCode)
		{
		case REQUEST_DRAWVIEW:
			if(isResultOK){
				debugStr = data.getExtras().getString("debug");
				addDebugText(debugStr);
			}
			break;
		case REQUEST_CALIBRATIONVIEW:
			if(isResultOK){
				debugStr = data.getExtras().getString("debug");
				addDebugText(debugStr);
			}
			break;
		case REQUEST_TIMETESTVIEW:
			break;
		case REQUEST_THRESHOLDVIEW:
			break;
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        writeLogScrollView = (ScrollView) findViewById(R.id.writeLogScrollView);
        writeLogTextView = (TextView) findViewById(R.id.writeLogTextView);

    	sensorDisValueTextView = (TextView) findViewById(R.id.sensorDisValueTextView);
    	irGapValueTextView = (TextView) findViewById(R.id.irGapValueTextView);

    	modelCodeValueTextView = (TextView) findViewById(R.id.modelCodeValueTextView);
    	hwVerValueTextView = (TextView) findViewById(R.id.hwVerValueTextView);
    	mcu1VerValueTextView = (TextView) findViewById(R.id.mcu1VerValueTextView);
    	aliveSecValueTextView = (TextView) findViewById(R.id.aliveSecValueTextView);
    	mcu2VerValueTextView = (TextView) findViewById(R.id.mcu2VerValueTextView);
    	temperatureValueTextView = (TextView) findViewById(R.id.temperatureValueTextView);
    	packetCountValueTextView = (TextView) findViewById(R.id.packetCountValueTextView);
    	errXCntValueTextView = (TextView) findViewById(R.id.errXCntValueTextView);
    	errYCntValueTextView = (TextView) findViewById(R.id.errYCntValueTextView);
    	pressureValueTextView = (TextView) findViewById(R.id.pressureValueTextView);
    	curThresholdValueTextView = (TextView) findViewById(R.id.curThresholdValueTextView);
    	statusValueTextView = (TextView) findViewById(R.id.statusValueTextView);
    	rawXValueTextView = (TextView) findViewById(R.id.rawXValueTextView);
    	rawYValueTextView = (TextView) findViewById(R.id.rawYValueTextView);
    	convXValueTextView = (TextView) findViewById(R.id.convXValueTextView);
    	convYValueTextView = (TextView) findViewById(R.id.convYValueTextView);
    	
    	downCountValueTextView = (TextView) findViewById(R.id.downCountValueTextView);
    	moveCountValueTextView = (TextView) findViewById(R.id.moveCountValueTextView);
    	upCountValueTextView = (TextView) findViewById(R.id.upCountValueTextView);
    	totalCountValueTextView = (TextView) findViewById(R.id.totalCountValueTextView);
    	kbyteValueTextView = (TextView) findViewById(R.id.kbyteValueTextView);
    	byteValueTextView = (TextView) findViewById(R.id.byteValueTextView);
    }
    
    /*
     * top 1 menu button
     */
    public void drawingBtnClicked(View v){
    	Intent intent = new Intent(MainActivity.this, DrawViewActivity.class);
		startActivityForResult(intent, REQUEST_DRAWVIEW);
    }
    public void calibrationBtnClicked(View v){
    	
    	if(MainDefine.penController.bExistCalibrationInfo){
    		addDebugText("calibration data exist");
    	}else{
    		addDebugText("calibration data not exist");
    	}
    	
    	if (MainDefine.penController.getModelCode() == 0) {
            addDebugText("smart pen");
        }
        else if (MainDefine.penController.getModelCode() == 1) {
        	addDebugText("lollol pen");
        }
        else if (MainDefine.penController.getModelCode() == 2) {
        	addDebugText("Equil");
        }
    	
//    	Intent intent = new Intent(MainActivity.this, Calib2PointActivity.class);
//		startActivityForResult(intent, REQUEST_CALIBRATIONVIEW);
    	
    	Intent intent = new Intent(MainActivity.this, CalibrationPointActivity.class);
		startActivityForResult(intent, REQUEST_CALIBRATIONVIEW);
    }
    public void stopPenBtnClicked(View v){
    	MainDefine.penController.stopPen();
    }
    public void restartPenBtnClicked(View v){
    	MainDefine.penController.restartPen();
    }
    
    /*
     * top 2 menu button
     */
    public void timeTestBtnClicked(View v){
    	Intent intent = new Intent(MainActivity.this, TimeTestViewActivity.class);
		startActivityForResult(intent, REQUEST_TIMETESTVIEW);
    }
    
    /*
     * top 3 menu button
     */
    public void clearLogBtnClicked(View v){
    	writeLogTextView.setText("");
    }
    
    public void thresholdBtnClicked(View v){
    	Intent intent = new Intent(MainActivity.this, ThresholdViewActivity.class);
		startActivityForResult(intent, REQUEST_THRESHOLDVIEW);
    }
    
    /*
     * bottom menu button
     */
    public void applyBtnClicked(View v){
    	
    }
    
    public void packetCountClearClicked(View v){
    	packetCnt = 0;
        errCntX = 0;
        errCntY = 0;
        beforeRawX = -1;
        beforeRawY = -1;
        
        updateErrCntX();
        updateErrCntY();
        updatePacketCnt();
    }
    
    public void countClearClicked(View v){
    	downCnt = 0;
        moveCnt= 0;
        upCnt = 0;
        
        updateDrawCnt();
    }
    
    void showAlertView(int alertTag){
    	AlertDialog.Builder builder = null;
    	AlertDialog alert = null;
    	
    	builder = new AlertDialog.Builder(MainActivity.this);
    	builder.setCancelable(false);
    	
    	switch(alertTag)
    	{
    	case ALERTVIEW_FAIL_LISTENING:
    		builder.setTitle(getResources().getString(R.string.FAIL_LISTENING_MSG));
        	builder.setPositiveButton(getResources().getString(R.string.COMMON_OK), new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			dialog.dismiss();
        		}
        	});
        	builder.setNegativeButton(getResources().getString(R.string.COMMON_CANCEL), new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			dialog.dismiss();
        		}
        	});
    		break;
    	case ALERTVIEW_APP_EXIT:
    		builder.setTitle(getResources().getString(R.string.QUIL_APP));
        	builder.setPositiveButton(getResources().getString(R.string.COMMON_OK), new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			dialog.dismiss();
        			
        			setResult(RESULT_OK ,null);
        			finish();
        		}
        	});
        	builder.setNegativeButton(getResources().getString(R.string.COMMON_CANCEL), new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {
        			dialog.dismiss();
        		}
        	});
    		break;
    	}
    	

    	alert = builder.create();
    	alert.show();
	}
    
    void updateErrCntX(){
    	errXCntValueTextView.setText(""+errCntX);
    }
    
    void updateErrCntY(){
    	errYCntValueTextView.setText(""+errCntY);
    }
    
    void updatePacketCnt(){
    	packetCountValueTextView.setText(""+packetCnt);
    }
    
    void updateDrawCnt(){
    	downCountValueTextView.setText(""+downCnt);
    	moveCountValueTextView.setText(""+moveCnt);
    	upCountValueTextView.setText(""+upCnt);
    	totalCountValueTextView.setText(""+(downCnt+moveCnt+upCnt));
    	
    	float byteData = 0;
        if (downCnt+moveCnt+upCnt == 0)
        	byteValueTextView.setText("0");
        else {
            byteValueTextView.setText(""+(((downCnt+moveCnt+upCnt)*6)+((downCnt+moveCnt+upCnt)/12)+1));
            byteData = ((downCnt+moveCnt+upCnt)*6)+((downCnt+moveCnt+upCnt)/12)+1;
        }
        kbyteValueTextView.setText(""+(byteData/1024.f));
    }
    
    void addDebugText(String text) {
    	String orgText = writeLogTextView.getText().toString();
    	String inputText = "";
    	if(orgText.isEmpty()){
    		inputText = text;
    	}else{
    		inputText = orgText + "\n" + text;
    	}
    	
    	writeLogTextView.setText(inputText);
    	writeLogScrollView.post(new Runnable() {       
    		@Override
    		public void run() {
    			writeLogScrollView.fullScroll(View.FOCUS_DOWN);              
    		}
    	});

    }
    
    protected Handler penPopupHandler = new Handler() 
	{        
		@Override
		public void handleMessage(Message msg) 
		{
			switch(msg.what)
			{
			case SHOWPOPUP_PEN_CONNECT:
				break;
			case SHOWPOPUP_PEN_DISCONNECT:
				break;
			case SHOWPOPUP_PEN_ALIVE:
				penSleepView = new PenSleepView(MainActivity.this);
				penSleepView.setOnDataListener(new PenSleepView.OnDataListener() {
					
					@Override
					public void onResultFinish() {
						closePenSleepView();
					}
				});
				penSleepView.start();
				break;
			}
		}
	};
	
	
	
    
    
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
		
		
		isRecvEnvDataFirst = true;
		penIdleTimerStop();
		closePenSleepView();
		
		if(!isHasFocus) return;
		
		if (MainDefine.penController == null) {
	        addDebugText("PenController is not set");
	        return;
	    }
		
		PointF pos = MainDefine.penController.GetCoordinatePostionXY(RawX,RawY);
		PenDataClass penData = (PenDataClass)obj;
		
		packetCnt++;
		
		updatePacketCnt();
		if(penData.Pen_Temperature <= 10){
			temperatureCnt++;
			if(temperatureCnt >= 1000){
				temperatureCnt = 0;
				Toast.makeText(
						getApplicationContext(),
						MainDefine.getLangString(getApplicationContext() ,R.string.PEN_TEMP_ERROR_MSG),
						Toast.LENGTH_SHORT)
					.show();
			}
		}else{
			temperatureCnt = 0;
		}
		
		statusValueTextView.setText(""+what);
		temperatureValueTextView.setText(""+penData.Pen_Temperature);
		rawXValueTextView.setText(""+RawX);
		rawYValueTextView.setText(""+RawY);
		convXValueTextView.setText(""+pos.x);
		convYValueTextView.setText(""+pos.y);
		
		switch(what)
		{
		case PNFDefine.PEN_DOWN:
			downCnt++;
			break;
		case PNFDefine.PEN_MOVE:
			moveCnt++;
			break;
		case PNFDefine.PEN_UP:
			upCnt++;
			break;
			
			
		case PNFDefine.PEN_HOVER:
			break;
		case PNFDefine.PEN_HOVER_DOWN:
			break;
		case PNFDefine.PEN_HOVER_MOVE:
			break;
			
		
		}
		
		updateDrawCnt();
		
		if (beforeRawX == -1) {
	        beforeRawX = RawX;
	    }
	    else {
	        if (RawX-beforeRawX>1.0f) {
	            errCntX++;
	            updateErrCntX();
	        }
	    }
	    if (beforeRawY == -1) {
	        beforeRawY = RawY;
	    }
	    else {
	        if (RawY-beforeRawY>1.0f) {
	            errCntY++;
	            updateErrCntY();
	        }
	    }
	}
	
	
	
	void penIdleTimerStop(){
		if(penAliveTimer != null){
			penAliveTimer.cancel();
			
			penAliveTimer = null;
		}
	}
	
	PenSleepView penSleepView;
	void showPenSleepView(){
		if(!MainDefine.penController.isPenMode()) return;
//		if (MainDefine.penController.getMCU1() < 2 || MainDefine.penController.getMCU2() < 2 || MainDefine.penController.getHWVersion() < 2) return;
		
		penPopupHandler.sendEmptyMessage(SHOWPOPUP_PEN_ALIVE);
	}
	
	void closePenSleepView(){
		if(penSleepView != null){
			penSleepView.finish();
			penSleepView = null;
		}
	}
	
	int savePenSleepRemainingTime;
	int savePenAliveSec;
	int curPenAliveSec;
	boolean isRecvEnvDataFirst = true;
	boolean isFirstPenSleepOldDevice = false;
	Timer penAliveTimer = null;
	
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
		
		
		curPenAliveSec = penData.Pen_Alive;
		
		aliveSecValueTextView.setText(""+penData.Pen_Alive);
//		sensorDisValueTextView.setText(""+penData.Pen_Us);
//		irGapValueTextView.setText(""+penData.Pen_Ir);
		
		
		if(MainDefine.penController.getMCU1() >= 2 && MainDefine.penController.getMCU2() >= 2 && MainDefine.penController.getHWVersion() >= 2){
			if (penData.Pen_Alive > 0) {
	            closePenSleepView();
	        }
		}
		
		if (isRecvEnvDataFirst) {
	        isRecvEnvDataFirst = false;
	        penIdleTimerStop();
	        
	        penAliveTimer = new Timer();
	        TimerTask penAliveTask = new TimerTask() {
				@Override
				public void run() {
					onTimerForPenAlive();
				}
			};
			penAliveTimer.schedule(penAliveTask, 1000*5 ,1000);
			
	        savePenSleepRemainingTime = (int) MainDefine.GetCurrentSec() + 550;
	        savePenAliveSec = 550;
	        curPenAliveSec = 550;
	    }
	}
	
	protected void onTimerForPenAlive(){
		int curTime = (int) MainDefine.GetCurrentSec();
		
		if(MainDefine.penController.getMCU1() >= 2 && MainDefine.penController.getMCU2() >= 2 && MainDefine.penController.getHWVersion() >= 2){
			if(curPenAliveSec <= 0)
			{
				showPenSleepView();
	            penIdleTimerStop();
				return;
			}
			
			
			if(curPenAliveSec != 0 && savePenAliveSec != curPenAliveSec){
				savePenAliveSec = curPenAliveSec;
				savePenSleepRemainingTime = (int) curTime+curPenAliveSec;
			}
		}else{
			if (!isFirstPenSleepOldDevice) {
	            isFirstPenSleepOldDevice = true;
	            savePenSleepRemainingTime = curTime - 10;
	        }
			
		}
		
		if(savePenSleepRemainingTime < curTime){
			showPenSleepView();
            penIdleTimerStop();
		}
	}
	
	protected Handler messageHandler = new Handler() 
	{        
		@Override
		public void handleMessage(Message msg) 
		{
			onMessageEvent(msg.what ,msg.arg1 ,msg.arg2 ,msg.obj);
		}
	};
	
	protected void onMessageEvent(int what, int RawX, int RawY ,Object obj)
	{
		if(!isHasFocus) return;
		
		if(what == PNFDefine.PNF_MSG_FAIL_LISTENING){
			showAlertView(ALERTVIEW_FAIL_LISTENING);
			return;
		}else if(what == PNFDefine.PNF_MSG_CONNECTED){
			addDebugText("PNF_MSG_CONNECTED");
			
			penErrorCnt = 0;
			isFirstPenSleepOldDevice = false;
			
		}
		else if(what == PNFDefine.PNF_MSG_INVALID_PROTOCOL){
			return;
		}
		
		else if(what == PNFDefine.PNF_MSG_SESSION_CLOSED){
			addDebugText("PNF_MSG_SESSION_CLOSED");
			penIdleTimerStop();
			isFirstPenSleepOldDevice = false;
	        closePenSleepView();
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
			penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		else if(what == PNFDefine.GESTURE_CIRCLE_COUNTERCLOCKWISE){
			addDebugText("GESTURE_CIRCLE_COUNTERCLOCKWISE");
			penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		else if(what == PNFDefine.GESTURE_CLICK){
			addDebugText("GESTURE_CLICK");
	        penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		else if(what == PNFDefine.GESTURE_DOUBLECLICK){
			addDebugText("GESTURE_DOUBLECLICK");
			penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		
		packetCnt++;
	    updatePacketCnt();
	    
	    modelCodeValueTextView.setText(""+MainDefine.penController.getModelCode());
		mcu1VerValueTextView.setText(""+MainDefine.penController.getMCU1());
		mcu2VerValueTextView.setText(""+MainDefine.penController.getMCU2());
		hwVerValueTextView.setText(""+MainDefine.penController.getHWVersion());
	}
	
}
