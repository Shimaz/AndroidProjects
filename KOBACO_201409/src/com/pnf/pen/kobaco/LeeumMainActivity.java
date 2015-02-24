package com.pnf.pen.kobaco;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.pnf.bt.lib.PNFDefine;
import com.pnf.bt.lib.PenDataClass;
import com.pnf.pen.drawingview.DrawView;
import com.pnf.pen.popup.PenSleepView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class LeeumMainActivity extends Activity {

	private final byte ALERTVIEW_FAIL_LISTENING = 0x00;
	private final byte ALERTVIEW_APP_EXIT = 0x01;
	
	private final byte SHOWPOPUP_PEN_CONNECT = 0x00;
	private final byte SHOWPOPUP_PEN_DISCONNECT = 0x01;
	private final byte SHOWPOPUP_PEN_ALIVE = 0x02;
	
	private final int SEND_FILE_ACTIVITY = 100;
	private final int POPUP_ACTIVITY = 101;
	
	private RelativeLayout rlMain;
//	private LeeumDrawView ldv;
	private DrawView dv;
	
	private OSCBinder binder;
	

	private Button btnRestart;
	private Button btnSend;
	
//	private Button btnColor01;
//	private Button btnColor02;
//	private Button btnColor03;
//	private Button btnColor04;
//	private Button btnColor05;
//	private Button btnColor06;
//	private Button btnColor07;
	
	private Handler tHandler;
	private final int CHECK_TIME = 60;
	private int dTime = 0; 
	
	
	private boolean isDirty = false;
	private boolean isLoading = false;
	
//	private int imgKind;
	
	private int nowColor = 7;
	private int beforeColor = 7;
	
	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
	    @Override
	    public void onReceive(Context arg0, Intent intent) {
	      // TODO Auto-generated method stub
	      int level = intent.getIntExtra("level", 0);
	      int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);

	      android.util.Log.i("battery", "" + level);
	      
	      
	      setStatus(level, status);
	      
	    }
	  };
	
	
	private ArrayList<Button> arrColorButtons;
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		binder = (OSCBinder)getApplicationContext();
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
       
        
        setContentView(R.layout.leeum_main);
   
        rlMain = (RelativeLayout)findViewById(R.id.rl_leeum_main);

        
        rlMain.setMotionEventSplittingEnabled(false);
        
//        imgKind = getIntent().getExtras().getInt("imgKind");
        
        dv = new DrawView(this);
        rlMain.addView(dv);
        
        ImageView ivCover = new ImageView(this);
        ivCover.setBackgroundResource(R.drawable.img_draw_cover_moon);
        ivCover.setX(0);
        ivCover.setY(0);
        rlMain.addView(ivCover);
        
        arrColorButtons = new ArrayList<Button>();
        
        
        Button btnColor01 = new Button(this);
        btnColor01.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnColor01.setBackgroundResource(R.drawable.btn_big_1_normal);
        btnColor01.setX(0);
        btnColor01.setY(0);
        btnColor01.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dv.setColor(1);
				
				setColor(1);
			}
		});
        
        
        arrColorButtons.add(btnColor01);
        RelativeLayout rlSub01 = new RelativeLayout(this);
        rlSub01.setLayoutParams(new LayoutParams(163, 163));
        rlSub01.setX(97);
        rlSub01.setY(767);
        rlSub01.addView(btnColor01);
        
        rlMain.addView(rlSub01);
        
        Button btnColor02 = new Button(this);
        btnColor02.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnColor02.setBackgroundResource(R.drawable.btn_big_2_normal);
        btnColor02.setX(0);
        btnColor02.setY(0);
        
        
        
        btnColor02.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dv.setColor(2);
				setColor(2);
			}
		});
        
        arrColorButtons.add(btnColor02);
        
        RelativeLayout rlSub02 = new RelativeLayout(this);
        rlSub02.setLayoutParams(new LayoutParams(163, 163));
        rlSub02.setX(244);
        rlSub02.setY(767);
        rlSub02.addView(btnColor02);
        
        rlMain.addView(rlSub02);
        
        
        Button btnColor03 = new Button(this);
        btnColor03.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnColor03.setBackgroundResource(R.drawable.btn_big_3_normal);
        
        btnColor03.setX(0);
        btnColor03.setY(0);
        
        
        btnColor03.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dv.setColor(3);
				
				setColor(3);
			}
		});
        
        arrColorButtons.add(btnColor03);
        
        RelativeLayout rlSub03 = new RelativeLayout(this);
        rlSub03.setLayoutParams(new LayoutParams(163, 163));
        rlSub03.setX(392);
        rlSub03.setY(767);
        rlSub03.addView(btnColor03);
        
        rlMain.addView(rlSub03);
        
        
        
        
        Button btnColor04 = new Button(this);
        btnColor04.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnColor04.setBackgroundResource(R.drawable.btn_big_4_normal);
        
        btnColor04.setX(0);
        btnColor04.setY(0);
        
        
        btnColor04.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dv.setColor(4);
				
				setColor(4);
				
			}
		});
        
        arrColorButtons.add(btnColor04);
        
        RelativeLayout rlSub04 = new RelativeLayout(this);
        rlSub04.setLayoutParams(new LayoutParams(163, 163));
        rlSub04.setX(539);
        rlSub04.setY(767);
        rlSub04.addView(btnColor04);
        
        rlMain.addView(rlSub04);        
        
        Button btnColor05 = new Button(this);
        btnColor05.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnColor05.setBackgroundResource(R.drawable.btn_big_5_normal);
        
        btnColor05.setX(0);
        btnColor05.setY(0);
        
        
        btnColor05.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dv.setColor(5);
				
				setColor(5);
			}
		});
        
        arrColorButtons.add(btnColor05);
        
        RelativeLayout rlSub05 = new RelativeLayout(this);
        rlSub05.setLayoutParams(new LayoutParams(163, 163));
        rlSub05.setX(170);
        rlSub05.setY(898);
        rlSub05.addView(btnColor05);
        
        rlMain.addView(rlSub05);
        
        
        
        Button btnColor06 = new Button(this);
        btnColor06.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnColor06.setBackgroundResource(R.drawable.btn_big_6_normal);
        
        btnColor06.setX(0);
        btnColor06.setY(0);
        
        
        btnColor06.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dv.setColor(6);
				
				setColor(6);
				
			}
		});
        
        arrColorButtons.add(btnColor06);
        
        RelativeLayout rlSub06 = new RelativeLayout(this);
        rlSub06.setLayoutParams(new LayoutParams(163, 163));
        rlSub06.setX(318);
        rlSub06.setY(898);
        rlSub06.addView(btnColor06);
        
        rlMain.addView(rlSub06);        
        
        
        Button btnColor07 = new Button(this);
        btnColor07.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnColor07.setBackgroundResource(R.drawable.btn_big_7_normal);

        btnColor07.setX(0);
        btnColor07.setY(0);
        
        btnColor07.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				dv.setColor(7);
				
				setColor(7);
			}
		});
        
        arrColorButtons.add(btnColor07);
        
        RelativeLayout rlSub07 = new RelativeLayout(this);
        rlSub07.setLayoutParams(new LayoutParams(163, 163));
        rlSub07.setX(465);
        rlSub07.setY(898);
        rlSub07.addView(btnColor07);
        
        rlMain.addView(rlSub07);        
        
        
        scaleButtons();
        
        
        btnRestart = new Button(this);
        btnRestart.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnRestart.setBackgroundResource(R.drawable.btn_restart);
        btnRestart.setX(218);
        btnRestart.setY(1099);
        btnRestart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 전체 지우기  
				
				resetDefault();
				
			}
		});
        
        rlMain.addView(btnRestart);
        
        btnSend = new Button(this);
        btnSend.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnSend.setBackgroundResource(R.drawable.btn_send);
        btnSend.setX(359);
        btnSend.setY(1099);
        btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!isLoading){
					
					isLoading = true;
					
					if(isDirty){
						
						Bitmap bitmap;
						Bitmap resizedBmp;
						View v1 = getWindow().getDecorView().getRootView();
						v1.setDrawingCacheEnabled(true);
						bitmap = Bitmap.createBitmap(v1.getDrawingCache());
						v1.setDrawingCacheEnabled(false);
						
						resizedBmp = Bitmap.createBitmap(bitmap, 77, 73, 648, 648);
						
						String sdcard = Environment.getExternalStorageDirectory().toString();
						File dir = new File(sdcard + "/temp_image") ;
						dir.mkdirs();
						File tempFile = new File(dir, "temp.png");
						if(tempFile.exists()) tempFile.delete();
						try{
							FileOutputStream out = new FileOutputStream(tempFile);
							resizedBmp.compress(Bitmap.CompressFormat.PNG, 100, out);
							out.flush();
							out.close();
							
						} catch(Exception e){
							e.printStackTrace();
						}
						
						String strTmp = tempFile.getAbsolutePath();
						
						
						
						
						
						Intent intent = new Intent(LeeumMainActivity.this, SendFileActivity.class);
					
//						intent.putExtra("imgKind", imgKind);
						intent.putExtra("fp", strTmp);
						
						intent.putExtra("bgPath", fastblur(5));
						
						startActivityForResult(intent, SEND_FILE_ACTIVITY);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
						
					}else{
					
						Intent intent = new Intent(LeeumMainActivity.this, FirstStrokeActivity.class);
						
						intent.putExtra("bgPath", fastblur(5));
						
						startActivityForResult(intent, POPUP_ACTIVITY);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					}
					
					
					
					
				}
				
				
			}
		});
        
//        rlMain.addView(btnSend);
        
		
        // 타이머 
       
        tHandler = new Handler(){
        	@Override
        	public void handleMessage(Message msg){
        		if(dTime == CHECK_TIME){
        			
        			resetDefault();
        			
        		}else{
        			
        			dTime++;
        			android.util.Log.i("timer", "" +  dTime);
        			
        			tHandler.sendEmptyMessageDelayed(0, 1000); 
        			
        		}
        		
        	}
        };
        
        this.registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
        
        
        
	}
	
	
	private void setStatus(int ll, int st){
		
		WindowManager.LayoutParams layout = getWindow().getAttributes();
		
		if(st == BatteryManager.BATTERY_STATUS_CHARGING ||
				st == BatteryManager.BATTERY_STATUS_FULL || 
				st == BatteryManager.BATTERY_STATUS_NOT_CHARGING ||
				st == BatteryManager.BATTERY_PLUGGED_AC ||
				st == BatteryManager.BATTERY_PLUGGED_USB){
			
			layout.screenBrightness = 0.8f;
			getWindow().setAttributes(layout);
			
			binder.setPowered(true);
			
		}else if(st == BatteryManager.BATTERY_STATUS_DISCHARGING){
			
			layout.screenBrightness = 0.1f;
			getWindow().setAttributes(layout);
			
			binder.setPowered(false);
			
		}
		
	}
	
	private void scaleButtons(){
		
		Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
		
		for(int i = 0; i < arrColorButtons.size() -1 ; i++){
			
			Button btn = arrColorButtons.get(i);
			
			btn.startAnimation(scaleDown);
			
			
		}
		
		
	}
	
	private void setColor(int color){
		
		
		dTime = 0;
		
		if(color == nowColor) return;
		
		nowColor = color;
		
		if(nowColor > 0 && nowColor < 8){
			Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
			Button btnNow = arrColorButtons.get(nowColor -1);
			btnNow.startAnimation(scaleUp);
			
		}
		
		
		if(beforeColor > 0 && beforeColor < 8){
			Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
			Button btnBefore = arrColorButtons.get(beforeColor - 1);
			btnBefore.startAnimation(scaleDown);
		}
		
		beforeColor = nowColor;
		
		
		
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		
		if (requestCode == SEND_FILE_ACTIVITY){
			if(resultCode == RESULT_OK || resultCode == OSCBinder.RESULT_TIMER){
				
				
//				Intent intent = new Intent(LeeumMainActivity.this, SelectActivity.class);
//				startActivity(intent);
//				finish();
//				overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				
				/**
				 * 
				 * Reset to default
				 * 
				 */
				
				
				resetDefault();
//				
			}
		}else if(requestCode == POPUP_ACTIVITY){
			if(resultCode == OSCBinder.RESULT_TIMER){
				
//				Intent intent = new Intent(LeeumMainActivity.this, SelectActivity.class);
//				startActivity(intent);
//				finish();
//				overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				
				resetDefault();
				
			}
		}
		
		
	}
	
	private void resetDefault(){
		Intent intent = new Intent(LeeumMainActivity.this, LeeumMainActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
		
		
	}
	
	void showAlertView(int alertTag){
    	AlertDialog.Builder builder = null;
    	AlertDialog alert = null;
    	
    	builder = new AlertDialog.Builder(LeeumMainActivity.this);
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
				penSleepView = new PenSleepView(LeeumMainActivity.this);
				penSleepView.setOnDataListener(new PenSleepView.OnDataListener() {
					
					@Override
					public void onResultFinish() {
						closePenSleepView();
					}
				});
//				penSleepView.start();
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
		
//		if(!isHasFocus) return;
		
//		if (MainDefine.penController == null) {
//	        addDebugText("PenController is not set");
//	        return;
//	    }
		
		PointF pos = MainDefine.penController.GetCoordinatePostionXY(RawX,RawY);
		
//		android.util.Log.i("shimaz", "onPenEvent x: " + pos.x + " y: " + pos.y);
		
		PenDataClass penData = (PenDataClass)obj;
		
//		packetCnt++;
		
//		updatePacketCnt();
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
		
//		statusValueTextView.setText(""+what);
//		temperatureValueTextView.setText(""+penData.Pen_Temperature);
//		rawXValueTextView.setText(""+RawX);
//		rawYValueTextView.setText(""+RawY);
//		convXValueTextView.setText(""+pos.x);
//		convYValueTextView.setText(""+pos.y);
		
		switch(what)
		{
		case PNFDefine.PEN_DOWN:
//			downCnt++;
//			ldv.setBefore(pos.x, pos.y);
//			isDown = true;
			
			if(!isDirty) tHandler.sendEmptyMessage(0);
				
			
			isDirty = true;
			dTime = 0;
			dv.DoMouseDown(pos.x ,pos.y);
			break;
		case PNFDefine.PEN_MOVE:
			dTime = 0;
			dv.DoMouseDragged(pos.x ,pos.y);
			dv.invalidatePath();
			
//			moveCnt++;
//			ldv.drawSurface(pos.x, pos.y, isDown);
			
			break;
		case PNFDefine.PEN_UP:
			dTime = 0;
			dv.DoMouseUp(pos.x ,pos.y);
			dv.invalidatePath();
			
			break;
			
			
		case PNFDefine.PEN_HOVER:
			break;
		case PNFDefine.PEN_HOVER_DOWN:
			break;
		case PNFDefine.PEN_HOVER_MOVE:
			break;
			
		
		}
		
//		updateDrawCnt();
//		
//		if (beforeRawX == -1) {
//	        beforeRawX = RawX;
//	    }
//	    else {
//	        if (RawX-beforeRawX>1.0f) {
//	            errCntX++;
//	            updateErrCntX();
//	        }
//	    }
//	    if (beforeRawY == -1) {
//	        beforeRawY = RawY;
//	    }
//	    else {
//	        if (RawY-beforeRawY>1.0f) {
//	            errCntY++;
//	            updateErrCntY();
//	        }
//	    }
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
//		if(!isHasFocus) return;
		
		PenDataClass penData = (PenDataClass)obj;
		
		
		curPenAliveSec = penData.Pen_Alive;
		
//		aliveSecValueTextView.setText(""+penData.Pen_Alive);
//		sensorDisValueTextView.setText(""+penData.Pen_Us);
//		irGapValueTextView.setText(""+penData.Pen_Ir);
		
		android.util.Log.i("shimaz", "onPenEnvEvent x: " + RawX + " y: " + RawY);
		
		// Draw on Canvas
		
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
		android.util.Log.i("shimaz", "onMessageEvent x: " + RawX + " y: " + RawY);
		
		if(what == PNFDefine.PNF_MSG_FAIL_LISTENING){
			showAlertView(ALERTVIEW_FAIL_LISTENING);
			return;
		}else if(what == PNFDefine.PNF_MSG_CONNECTED){
			
			isFirstPenSleepOldDevice = false;
			
		}
		else if(what == PNFDefine.PNF_MSG_INVALID_PROTOCOL){
			return;
		}
		
		else if(what == PNFDefine.PNF_MSG_SESSION_CLOSED){
			penIdleTimerStop();
			isFirstPenSleepOldDevice = false;
	        closePenSleepView();
		}
		else if(what == PNFDefine.PNF_MSG_PEN_RMD_ERROR){
//				Toast.makeText(
//						getApplicationContext(),
//						MainDefine.getLangString(getApplicationContext() ,R.string.PEN_RMD_ERROR_MSG),
//						Toast.LENGTH_SHORT)
//					.show();
//				penErrorCnt = 0;
//			}
//			return;
		}
		else if(what == PNFDefine.PNF_MSG_FIRST_DATA_RECV){
//			addDebugText("PNF_MSG_FIRST_DATA_RECV");
		}
		else if(what == PNFDefine.GESTURE_CIRCLE_CLOCKWISE){
//			addDebugText("GESTURE_CIRCLE_CLOCKWISE");
			penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		else if(what == PNFDefine.GESTURE_CIRCLE_COUNTERCLOCKWISE){
//			addDebugText("GESTURE_CIRCLE_COUNTERCLOCKWISE");
			penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		else if(what == PNFDefine.GESTURE_CLICK){
//			addDebugText("GESTURE_CLICK");
	        penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		else if(what == PNFDefine.GESTURE_DOUBLECLICK){
//			addDebugText("GESTURE_DOUBLECLICK");
			penIdleTimerStop();
	        closePenSleepView();
			return;
		}
		
//		packetCnt++;
//	    updatePacketCnt();
//	    
//	    modelCodeValueTextView.setText(""+MainDefine.penController.getModelCode());
//		mcu1VerValueTextView.setText(""+MainDefine.penController.getMCU1());
//		mcu2VerValueTextView.setText(""+MainDefine.penController.getMCU2());
//		hwVerValueTextView.setText(""+MainDefine.penController.getHWVersion());
	}
	
	
	public String fastblur(int radius) {
        
		
		Bitmap tmpBmp; //  = sentBitmap.copy(sentBitmap.getConfig(), true);
		Bitmap bitmap;
		View v1 = getWindow().getDecorView().getRootView();
		v1.setDrawingCacheEnabled(true);
		tmpBmp = Bitmap.createBitmap(v1.getDrawingCache());
		
        v1.setDrawingCacheEnabled(false);
        
        
        bitmap = Bitmap.createScaledBitmap(tmpBmp, 400, 640, true);
        
        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        android.util.Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
            sir[1] = (p & 0x00ff00) >> 8;
        sir[2] = (p & 0x0000ff);
        rbs = r1 - Math.abs(i);
        rsum += sir[0] * rbs;
        gsum += sir[1] * rbs;
        bsum += sir[2] * rbs;
        if (i > 0) {
            rinsum += sir[0];
            ginsum += sir[1];
            binsum += sir[2];
        } else {
            routsum += sir[0];
            goutsum += sir[1];
            boutsum += sir[2];
        }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
            sir[1] = (p & 0x00ff00) >> 8;
            sir[2] = (p & 0x0000ff);

            rinsum += sir[0];
            ginsum += sir[1];
            binsum += sir[2];

            rsum += rinsum;
            gsum += ginsum;
            bsum += binsum;

            stackpointer = (stackpointer + 1) % div;
            sir = stack[(stackpointer) % div];

            routsum += sir[0];
            goutsum += sir[1];
            boutsum += sir[2];

            rinsum -= sir[0];
            ginsum -= sir[1];
            binsum -= sir[2];

            yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        android.util.Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        
        
        
        String sdcard = Environment.getExternalStorageDirectory().toString();
		File dir = new File(sdcard + "/temp_image") ;
		dir.mkdirs();
		File tempFile = new File(dir, "bg_temp.png");
		if(tempFile.exists()) tempFile.delete();
		try{
			FileOutputStream out = new FileOutputStream(tempFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		String strTmp = tempFile.getAbsolutePath();
        
        
        return (strTmp);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		
		MainDefine.penController.SetRetObj(penHandler);
		MainDefine.penController.SetRetObjForEnv(penEnvHandler);
		MainDefine.penController.SetRetObjForMsg(messageHandler);
		
		dTime = 0;
		if(isDirty){
			tHandler.sendEmptyMessage(0);
		}
		isLoading = false;
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		dTime = 0;
		tHandler.removeMessages(0);
	}
	
	@Override 
	protected void onDestroy(){
		super.onDestroy();
		
		dTime = CHECK_TIME + 1;
		tHandler.removeMessages(0);
		tHandler = null;
		
		penIdleTimerStop();
		
		unregisterReceiver(mBatInfoReceiver);
		
		
	}
	
	
	
	
	
}
