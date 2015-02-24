package com.pnf.pen.kobaco;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressLint("HandlerLeak")
public class FirstStrokeActivity extends Activity {
	
	private Handler tHandler;
	private final int CHECK_TIME = 30;
	private int dTime = 0;
	
	private boolean isLoading = false;
	
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		RelativeLayout rlMain = new RelativeLayout(this);
		rlMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rlMain.setMotionEventSplittingEnabled(false);

		
		
		String path = getIntent().getExtras().getString("bgPath");
		

        BitmapDrawable dbp = new BitmapDrawable(path);
        
        rlMain.setBackgroundDrawable(dbp);
  
        
        
        
		setContentView(rlMain);

		
		ImageView iv = new ImageView(this);
		iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		iv.setX(0);
		iv.setY(0);
		iv.setBackgroundResource(R.drawable.img_back_first_stroke);
		
		rlMain.addView(iv);
		
		
		Button btnClose = new Button(this);
		btnClose.setBackgroundResource(R.drawable.btn_popup_close);
		btnClose.setX(355);
		btnClose.setY(633);
		btnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 타이머 처리시 여기서 setResult
				
				if(!isLoading){
					isLoading = true;
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short); 
					
				}
				
				
			}
		});
		
		rlMain.addView(btnClose);
		
		
		tHandler = new Handler(){
        	@Override
        	public void handleMessage(Message msg){
        		if(dTime == CHECK_TIME){
        			
        			setResult(OSCBinder.RESULT_TIMER);
        			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
        			finish();
        			
        		}else{
        			
        			dTime++;
        			
        			tHandler.sendEmptyMessageDelayed(0, 1000);
        			
        		}
        		
        	}
        };
        
        tHandler.sendEmptyMessage(0);
		
		
		
		
	}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		tHandler.removeMessages(0);
		tHandler = null;
	}

}
