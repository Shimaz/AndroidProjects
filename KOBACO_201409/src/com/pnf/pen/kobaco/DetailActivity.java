package com.pnf.pen.kobaco;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

@SuppressLint("HandlerLeak")
public class DetailActivity extends Activity {
	
	private int imgKind;
	private Handler tHandler;
	private final int CHECK_TIME = 60;
	private int dTime = 0;
	
	
	private boolean isLoading = false;
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		RelativeLayout rlMain = new RelativeLayout(this);
		rlMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rlMain.setMotionEventSplittingEnabled(false);
		setContentView(rlMain);
		
		imgKind = getIntent().getExtras().getInt("imgKind");
		
		Button btnOK = new Button(this);
		btnOK.setBackgroundResource(R.drawable.btn_select_confirm);
		btnOK.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		btnOK.setX(358);
		btnOK.setY(0);
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!isLoading){
					
					isLoading = true;
					
					Intent intent = new Intent(DetailActivity.this, LeeumMainActivity.class);
					intent.putExtra("imgKind", imgKind);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);		
					
					
				}
				
			}
		});
		
		rlMain.addView(btnOK);
		
		
		Button btnCancel = new Button(this);
		
		btnCancel.setBackgroundResource(R.drawable.btn_select_cancel);
		btnCancel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		btnCancel.setX(217);
		btnCancel.setY(0);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!isLoading){
				
					isLoading = false;
					
					Intent intent = new Intent(DetailActivity.this, SelectActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					
				}
				
				
				
				
			}
		});
		
		
		rlMain.addView(btnCancel);
		
		
		
		
		
		switch(imgKind){
			default:
			case 0:
				rlMain.setBackgroundResource(R.drawable.back_select_top);
				btnOK.setY(935);
				btnCancel.setY(935);
				
				break;
				
			case 1:
				rlMain.setBackgroundResource(R.drawable.back_select_middle);
				btnOK.setY(993);
				btnCancel.setY(993);
				
				break;
				
			case 2:
				rlMain.setBackgroundResource(R.drawable.back_select_bottom);
				btnOK.setY(993);
				btnCancel.setY(993);
				break;
		}
		
		
		tHandler = new Handler(){
        	@Override
        	public void handleMessage(Message msg){
        		if(dTime == CHECK_TIME){
        			
        			Intent intent = new Intent(DetailActivity.this, SelectActivity.class);
    				startActivity(intent);
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
