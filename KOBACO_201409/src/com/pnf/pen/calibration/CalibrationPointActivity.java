package com.pnf.pen.calibration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pnf.bt.lib.PNFDefine;
import com.pnf.bt.lib.PenDataClass;
import com.pnf.pen.kobaco.MainDefine;
import com.pnf.pen.kobaco.R;

@SuppressLint("NewApi") 
public class CalibrationPointActivity extends Activity
{
	protected final String TAG = "CalibrationPointActivity";
	
//	private final int nMaxDrawNumber = 2;
//	private final int nMaxPointNumber = 4;
	private int nCounter = 0;
	PointF	m_posCoordinate[] = null;
	PointF	m_posRestultPoint[] = null;
	PointF	m_posDrawCoordinate[] = null;
	
	ImageView[] caliPointImgView;
	ImageView caliShowPopup;
	
	Button calibRetryBtn;
	Button calibCloseBtn;
	
	int[] PointIdx;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.calibration);
		
		caliPointImgView = new ImageView[9];
		
		for(int i=0;i<9;i++){
			String imgStr = "caliPoint"+(i+1)+"ImgView";
			caliPointImgView[i] = (ImageView) findViewById(ResourcesIdNameToId(getApplicationContext(), imgStr));
		}
		
		caliShowPopup = (ImageView)findViewById(R.id.caliShowPopup);
		
		calibRetryBtn = (Button)findViewById(R.id.calibRetryBtn);
		
		calibCloseBtn = (Button)findViewById(R.id.calibCloseBtn);
		
		initData();
	}
	
	void initData(){
		m_posCoordinate = new PointF[9];
    	m_posRestultPoint = new PointF[9];
    	for(int i=0;i<9;i++){
    		m_posRestultPoint[i] = new PointF();
    	}
    	
		switch(MainDefine.penController.getProjectiveLevel())
		{
		case 4:
			PointIdx = new int[]{
					0 ,8
			};
			
			m_posCoordinate[0] = new PointF(0, 0);
	    	m_posCoordinate[1] = new PointF(MainDefine.iDisGetWidth, 0);
	    	m_posCoordinate[2] = new PointF(MainDefine.iDisGetWidth, MainDefine.iDisGetHeight);
	    	m_posCoordinate[3] = new PointF(0, MainDefine.iDisGetHeight);
	    	
	    	m_posCoordinate[4] = new PointF();
	    	m_posCoordinate[5] = new PointF();
	    	m_posCoordinate[6] = new PointF();
	    	m_posCoordinate[7] = new PointF();
	    	m_posCoordinate[8] = new PointF();
	    	
			break;
		case 9:
			PointIdx = new int[]{
					0 ,1 ,2 ,
					3 ,4 ,5 ,
					6 ,7 ,8
			};
			
			m_posCoordinate[0] = new PointF(0,0);
			m_posCoordinate[1] = new PointF(0,MainDefine.iDisGetHeight/2); 
			m_posCoordinate[2] = new PointF(0,MainDefine.iDisGetHeight);
			
			m_posCoordinate[3] = new PointF(MainDefine.iDisGetWidth/2,MainDefine.iDisGetHeight);
			m_posCoordinate[4] = new PointF(MainDefine.iDisGetWidth/2,MainDefine.iDisGetHeight/2); 
			m_posCoordinate[5] = new PointF(MainDefine.iDisGetWidth/2,0);
			
			m_posCoordinate[6] = new PointF(MainDefine.iDisGetWidth,0);
			m_posCoordinate[7] = new PointF(MainDefine.iDisGetWidth,MainDefine.iDisGetHeight/2); 
			m_posCoordinate[8] = new PointF(MainDefine.iDisGetWidth,MainDefine.iDisGetHeight);
			break;
		}
		
		
    	
    	
	}
		
	@Override
	protected void onResume()
	{
		super.onResume();
		
		MainDefine.penController.SetRetObj(penHandler);
		MainDefine.penController.SetRetObjForEnv(null);
		MainDefine.penController.SetRetObjForMsg(messageHandler);
	}


	@Override
	protected void onPause() 
	{
		super.onPause();
	}
	
	@Override
	protected void onStart(){//�≫떚鍮꾪떚���쒖떆瑜�以묐떒�덉쓣 ��遺덈┛��
    	super.onStart();
    	
    	
    }
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		
	}
	
	
	
	@Override
	public void onBackPressed() 
	{
//		
		
		Intent intent = new Intent();
		intent.putExtra("debug", debugStr);
		
		setResult(RESULT_OK,intent);
		finish();
		return;
	}

    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	isHasFocus = hasFocus;
    	
    	if(m_posDrawCoordinate == null) initCoordinateData();
    }
    
    public void initCoordinateData(){
    	m_posDrawCoordinate = new PointF[9];
    	for(int i=0;i<9;i++){
    		Rect rectGlobal = getGlobalVisibleRect(caliPointImgView[i]);
    		m_posDrawCoordinate[i] = new PointF(rectGlobal.centerX(), rectGlobal.centerY());
    		
    	}
    	
    	caliShowPopup.setVisibility(View.GONE);
    	reSetCalibration();
	}
    
    public void reSetCalibration(){
    	nCounter = 0;
    	for(int i=0;i<9;i++){
    		if(i == 0){
    			caliPointImgView[i].setSelected(false);
    			caliPointImgView[i].setVisibility(View.VISIBLE);
    		}else{
    			caliPointImgView[i].setVisibility(View.GONE);
    		}
    	}
    }
    
    
    public void reTryBtnClicked(View v){
    	reSetCalibration();
    }
    
    public void exitBtnClicked(View v){
    	Intent intent = new Intent();
		intent.putExtra("debug", debugStr);
		
		setResult(RESULT_OK,intent);
		finish();
    }
    
    public void test1BtnClicked(View v){
    	onPenEvent(PNFDefine.PEN_DOWN, 0, 0 ,new PenDataClass());
    }
    
    public void test2BtnClicked(View v){
    	onPenEvent(PNFDefine.PEN_UP, 0, 0 ,new PenDataClass());
    }
    
    Animation coordinateAni = null;
    Animation popupAni = null;
    boolean isAnimation = false;
    void setCaliPoint(int position){
    	final int moveIdx = position;
    	final int beforeIdx = PointIdx[moveIdx-1];
    	final int nextIdx = moveIdx<PointIdx.length?PointIdx[moveIdx]:PointIdx[PointIdx.length-1];
    	
    	if(moveIdx < PointIdx.length){
	    	coordinateAni = new TranslateAnimation(
	    			0,
	    			m_posDrawCoordinate[nextIdx].x-m_posDrawCoordinate[beforeIdx].x,
	    			0,
	    			m_posDrawCoordinate[nextIdx].y-m_posDrawCoordinate[beforeIdx].y);
	
	    	coordinateAni.setDuration(300);
	    	coordinateAni.setFillAfter(false);
	
	    	caliPointImgView[beforeIdx].startAnimation(coordinateAni);
	    	coordinateAni.setAnimationListener(new AnimationListener() {
	    		@Override
	    		public void onAnimationStart(Animation animation) {
	    			
	    		}
	
	    		@Override
	    		public void onAnimationRepeat(Animation animation) {
	    		}
	
	    		@Override
	    		public void onAnimationEnd(Animation animation) {
	    			
	    			coordinateAni.cancel();
	    			caliPointImgView[beforeIdx].clearAnimation();
	    			
	    			caliPointImgView[beforeIdx].setVisibility(View.GONE);
	    			
	    			caliPointImgView[nextIdx].setSelected(false);
	    			caliPointImgView[nextIdx].setVisibility(View.VISIBLE);
	    			
	    			
	    		}
	    	});
    	}else{
    		caliPointImgView[beforeIdx].setVisibility(View.GONE);
    	}
    	
    	String imgStr = "calibration_count_0"+moveIdx;
    	
    	caliShowPopup.setVisibility(View.VISIBLE);
    	caliShowPopup.setImageResource(ResourcesImgNameToId(getApplicationContext(), imgStr));
    	popupAni = AnimationUtils.loadAnimation(this, R.anim.zoom_out_alpha_fast);
		caliShowPopup.startAnimation(popupAni);
		popupAni.setAnimationListener(new AnimationListener() {
    		@Override
    		public void onAnimationStart(Animation animation) {
    			isAnimation = true;
    		}

    		@Override
    		public void onAnimationRepeat(Animation animation) {
    		}

    		@Override
    		public void onAnimationEnd(Animation animation) {
    			isAnimation = false;
    			
    			caliShowPopup.setVisibility(View.GONE);
    			
    			if(moveIdx == PointIdx.length){
    				showAlertView();
    				
    			}
    		}
    	});
    }
    
    void showAlertView(){
		AlertDialog.Builder builder = null;
		AlertDialog alert = null;
		
		builder = new AlertDialog.Builder(CalibrationPointActivity.this);
		builder.setCancelable(false);
		
		builder.setTitle(getResources().getString(R.string.CALIBRATION_TITLE));
		builder.setPositiveButton(getResources().getString(R.string.COMMON_SAVE), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				MainDefine.penController.setCalibrationData(m_posCoordinate ,0 ,m_posRestultPoint);
				
				Intent intent = new Intent();
				intent.putExtra("debug", debugStr);
				
				setResult(RESULT_OK,intent);
				finish();
			}
		});
		builder.setNeutralButton(getResources().getString(R.string.COMMON_RETRY), new DialogInterface.OnClickListener()//다시 설정 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				reSetCalibration();
				
			}
		});
		builder.setNegativeButton(getResources().getString(R.string.COMMON_CANCEL), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				Intent intent = new Intent();
				intent.putExtra("debug", debugStr);
				
				setResult(RESULT_OK,intent);
				finish();
			}
		});
		
		
		

		alert = builder.create();
		alert.show();
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
		if(isAnimation) return;
		
		PenDataClass penData = (PenDataClass)obj;
		
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
		
		if(what == PNFDefine.PEN_DOWN || what == PNFDefine.PEN_MOVE)
		{
			if(!caliPointImgView[PointIdx[nCounter]].isSelected()){
				caliPointImgView[PointIdx[nCounter]].setSelected(true);
			}
			
		}else if(what == PNFDefine.PEN_UP)
		{
			if(nCounter < PointIdx.length)							
			{
				caliPointImgView[PointIdx[nCounter]].setSelected(false);
				
				switch(MainDefine.penController.getProjectiveLevel())
				{
				case 4:
					if(nCounter == 0){
						m_posRestultPoint[0] = new PointF(RawX ,RawY);
					}else{
						m_posRestultPoint[2] = new PointF(RawX ,RawY);
						
						m_posRestultPoint[1] = new PointF(m_posRestultPoint[2].x ,m_posRestultPoint[0].y);
						m_posRestultPoint[3] = new PointF(m_posRestultPoint[0].x ,m_posRestultPoint[2].y);
					}
					break;
				case 9:
					m_posRestultPoint[PointIdx[nCounter]] = new PointF(RawX ,RawY);
					
					break;
				}
				
				
				nCounter++;
				setCaliPoint(nCounter);
			}
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
	
	boolean isHasFocus = false;
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
	
	public Rect getGlobalVisibleRect(View view){
		Rect r = new Rect();
		view.getGlobalVisibleRect(r);
		return r;
	}
	
	public int ResourcesIdNameToId(Context context ,String imgName){
		return context.getResources().getIdentifier(imgName, "id", context.getPackageName());
	}
	
	public int ResourcesImgNameToId(Context context ,String idName){
		if(idName.contains(".png")){
			idName = idName.substring(0, idName.lastIndexOf("."));
		}
		return context.getResources().getIdentifier(idName, "drawable", context.getPackageName());
	}

}