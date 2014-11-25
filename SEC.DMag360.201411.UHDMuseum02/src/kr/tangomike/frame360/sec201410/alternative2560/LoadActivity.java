package kr.tangomike.frame360.sec201410.alternative2560;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

@SuppressLint("HandlerLeak")
public class LoadActivity extends Activity {
	
	private int oscPort;
	private String oscIP;
	private int imgKind;
	private Handler mHandler;
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		Intent intent = this.getIntent();
		oscPort = intent.getExtras().getInt("framePort");
		oscIP = intent.getExtras().getString("frameIP");
		imgKind = intent.getExtras().getInt("imgKind");
		
		
		
	
		FrameLayout fl = new FrameLayout(this);
		fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		
		ImageView iv = new ImageView(this);
		iv.setBackgroundResource(R.drawable.loading);
		iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		iv.setX(572);
		iv.setY(1232);
		fl.addView(iv);
		
		this.setContentView(fl);
		
		
		
		mHandler = new Handler() {
        	public void handleMessage(Message msg){
        	
        	openNewActivity(imgKind);	
        		
        	}
        };
		
        
        mHandler.sendEmptyMessageDelayed(0, 1500);
		
	}
	
	

	private void openNewActivity(int i){
		
		if(i == 1){
			
			Intent intent = new Intent(LoadActivity.this, MainActivity01.class);
			intent.putExtra("framePort", oscPort);
			intent.putExtra("frameIP", oscIP);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
			finish();
			
			
		}else{
			Intent intent = new Intent(LoadActivity.this, MainActivity02.class);
			intent.putExtra("framePort", oscPort);
			intent.putExtra("frameIP", oscIP);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
			finish();
		}
	
		
		
	
	}
	

}
