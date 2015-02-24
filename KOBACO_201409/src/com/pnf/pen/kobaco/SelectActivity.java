package com.pnf.pen.kobaco;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SelectActivity extends Activity {

	private RelativeLayout rlMain;
	private Button btn01;
	private Button btn02;
	private Button btn03;
	
	private OSCBinder oscBinder;
	
	private boolean isLoading = false;
	
	@Override 
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		StrictMode.enableDefaults();
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      
		oscBinder = (OSCBinder)getApplicationContext();
		
		
		/**
		 * 
		 *  Layout and Buttons 
		 * 
		 */
		rlMain = new RelativeLayout(this);
		rlMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		rlMain.setBackgroundResource(R.drawable.back_select_page);
		rlMain.setMotionEventSplittingEnabled(false);
		
		setContentView(rlMain);
		
		btn01 = new Button(this);
		btn02 = new Button(this);
		btn03 = new Button(this);
		
		
		btn01.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		btn02.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		btn03.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		

		btn01.setBackgroundResource(R.drawable.btn_top);
		btn02.setBackgroundResource(R.drawable.btn_middle);
		btn03.setBackgroundResource(R.drawable.btn_bottom);

		btn01.setX(0);
		btn02.setX(0);
		btn03.setX(0);
		
		btn01.setY(512);
		btn02.setY(768);
		btn03.setY(1024);
		
		
		btn01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openDrawActivity(0);
			}
		});
		
		
		btn02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openDrawActivity(1);
				
				
			}
		});
		
		
		btn03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openDrawActivity(2);
			}
		});
		
		
		
		rlMain.addView(btn01);
		rlMain.addView(btn02);
		rlMain.addView(btn03);
		
		

		
	
	}
	
	public void openDrawActivity(int imgKind){
		
		if(!isLoading){
			
			isLoading = true;
			
			oscBinder.sendOSCData(imgKind, 0);
			
			Intent intent = new Intent(SelectActivity.this, DetailActivity.class);
			intent.putExtra("imgKind", imgKind);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);		
			
		}
	}

	
}
