package kr.tangomike.leeum.yhg.pad_a;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

	private Button btnProfile;
	private Button btnStory;
	private Button btnLang;
	private ScrollView scrl;
	
	private Handler mHandler;
	private long tCounter = 0;
	private long screenSaverOnTime = 60;
	private boolean isCounting = false;
	
	private boolean isKorean;
	
	
	private static final int PROFILE = 100;
	private static final int STORY = 101;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        
        isKorean = true;

        
        btnProfile = (Button)findViewById(R.id.btn_profile);
        btnProfile.setBackgroundResource(R.drawable.btn_profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContent(PROFILE, isKorean);
			}
		});
        
        btnStory = (Button)findViewById(R.id.btn_story);
        btnStory.setBackgroundResource(R.drawable.btn_story_kor);
        btnStory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContent(STORY, isKorean);
			}
		});
        
        btnLang = (Button)findViewById(R.id.btn_lang);
        btnLang.setBackgroundResource(R.drawable.btn_eng); 
        btnLang.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isKorean){
					isKorean = false;
					btnLang.setBackgroundResource(R.drawable.btn_kor);
					setContent(STORY, isKorean);
					
					
				}else{
					isKorean = true;
					btnLang.setBackgroundResource(R.drawable.btn_eng);
					setContent(STORY, isKorean);
				}
				
				
			}
		});
        
        scrl = (ScrollView)findViewById(R.id.scrl_content);
        scrl.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				tCounter = 0;
				
				return false;
			}
		});
        
        
        mHandler = new Handler() {
        	public void handleMessage(Message msg){
        		tCounter++;
        		

        		
        		

        			
        		if(tCounter <= screenSaverOnTime){

            		mHandler.sendEmptyMessageDelayed(0, 1000);
            		android.util.Log.i("tCounter", "" + tCounter);
            	
        		}else if(tCounter > screenSaverOnTime){
        			// TODO 
        			tCounter = 0;
        			mHandler.removeMessages(0);
        			
        			
        			// Run ScreenSaver Activity
        			isCounting = false;
        			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
        			finish();
        		}
        		
        		
        	}
        };
        
        mHandler.sendEmptyMessage(0);
        
        
		setContent(PROFILE, isKorean);
	}
	
	
	private void setContent(int tab, boolean lang){
		
		tCounter = 0;
		
		switch(tab){
			case PROFILE:
				
				btnLang.setAlpha(0.5f);
				btnLang.setEnabled(false);
				btnProfile.setBackgroundResource(R.drawable.btn_profile_reverse);
				if(lang){
					btnStory.setBackgroundResource(R.drawable.btn_story_kor);
				}else{
					btnStory.setBackgroundResource(R.drawable.btn_story_eng);
				}
				
				scrl.removeViews(0, scrl.getChildCount());
				
				
				
				LinearLayout llContent = new LinearLayout(getApplicationContext());
				llContent.setOrientation(LinearLayout.VERTICAL);
				llContent.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				
			
				
				int i = 1;
		        while(true){
		        	
		        	String ttt = "a_profile_text_" + i;
		        	android.util.Log.i("scrl", ttt);
		        	
		        	if(getResources().getIdentifier(ttt, "drawable", getPackageName()) != 0){
		        		ImageView iv = new ImageView(getApplicationContext());
		        		iv.setImageResource(getResources().getIdentifier(ttt, "drawable", getPackageName()));
		        		llContent.addView(iv);
		        		
		        		android.util.Log.i("content", ttt);
		        		
		        		i++;
		        	}else{
		        		break;
		        	}
		        	
		        	
		        }
		        
				scrl.addView(llContent);
				scrl.scrollTo(0, 0);
				scrl.requestLayout();
				
				
				break;
				
				
			case STORY:
				btnLang.setAlpha(1.0f);
				btnLang.setEnabled(true);
				btnProfile.setBackgroundResource(R.drawable.btn_profile);
				if(lang){
					btnStory.setBackgroundResource(R.drawable.btn_story_kor_reverse);
					
					
					scrl.removeViews(0, scrl.getChildCount());
					
					
					
					LinearLayout llContent2 = new LinearLayout(getApplicationContext());
					llContent2.setOrientation(LinearLayout.VERTICAL);
					llContent2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					
				
					
					int ii = 1;
			        while(true){
			        	
			        	String ttt = "a_story_text_kor_" + ii;
			        	android.util.Log.i("scrl", ttt);
			        	
			        	if(getResources().getIdentifier(ttt, "drawable", getPackageName()) != 0){
			        		ImageView iv = new ImageView(getApplicationContext());
			        		iv.setImageResource(getResources().getIdentifier(ttt, "drawable", getPackageName()));
			        		llContent2.addView(iv);
			        		
			        		android.util.Log.i("content", ttt);
			        		
			        		ii++;
			        	}else{
			        		break;
			        	}
			        	
			        	
			        }
			        
					scrl.addView(llContent2);
					scrl.scrollTo(0, 0);
					scrl.requestLayout();
					
					
				}else{
					btnStory.setBackgroundResource(R.drawable.btn_story_eng_reverse);
					
					
					scrl.removeViews(0, scrl.getChildCount());
					
					
					
					LinearLayout llContent2 = new LinearLayout(getApplicationContext());
					llContent2.setOrientation(LinearLayout.VERTICAL);
					llContent2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					
					scrl.removeAllViews();
				
					
					int ii = 1;
			        while(true){
			        	
			        	String ttt = "a_story_text_eng_" + ii;
			        	android.util.Log.i("scrl", ttt);
			        	
			        	if(getResources().getIdentifier(ttt, "drawable", getPackageName()) != 0){
			        		ImageView iv = new ImageView(getApplicationContext());
			        		iv.setImageResource(getResources().getIdentifier(ttt, "drawable", getPackageName()));
			        		llContent2.addView(iv);
			        		
			        		android.util.Log.i("content", ttt);
			        		
			        		ii++;
			        	}else{
			        		break;
			        	}
			        	
			        	
			        }
			        
			        scrl.addView(llContent2);
					scrl.scrollTo(0, 0);
					scrl.requestLayout();
					
				}
				
				
				break;
				
			default:
				break;
		}
		
	}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		mHandler.removeMessages(0);
		
		
	}
}
