package kr.tangomike.leeumshop201405;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity {

	private ShopData shopdata;
	private static final int MODE_SCR = 1000;
	private static final int MODE_USER = 1001;
	private int Mode = MODE_USER;
	
	private ImageView ivLogo;
	private ImageView rlMenu;
	
	
	private Button btn01;
	private Button btn02;
	private Button btn03;
	private Button btn04;
	
	private boolean isScreenSaverMode = false;
	
	private Handler mHandler;
	private long tCounter = 27;
//	private long baCounter = 0;
	private long screenSaverOnTime = 30;
	
	
	private boolean isStarting = true;
	
	
	private ImageView bg01;
	private ImageView bg02;
	private ImageView bg03;
	private ImageView bg04;
	private ImageView bg05;
	
	private Animation ba01;
	private Animation ba02;
	private Animation ba03;
	private Animation ba04;
	private Animation ba05;
	
	
	
	
	private Handler baHandler;
	/**
	 * TODO: 
	 * splash activity -> Data 수집 activity (각 화면당 자료의 static array를 생성 해당 activity가 선언 될 때 사용. 
	 * 신뢰성 확보를 위해 각 array는 csv로 저장되거나 parity check을 실행.  
	 * 
	 * (데모 때 까지는 고정 컨텐츠를 사용. 해당 부분은 Home Activity에서 array를 넘기는 것으로 대체. 
	 * Home Activity는 Public Array로 각 화면의 데이터를 가지고 있음. )
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        shopdata = (ShopData)getApplicationContext();
        
        rlMenu = (ImageView)findViewById(R.id.iv_menu_bg);
        
        


        
        
        
        
        /**
         * Temporary Contents Initialization
         */
        {

        	
        	ivLogo = (ImageView)findViewById(R.id.iv_logo);
  		
        	Animation aniHide = AnimationUtils.loadAnimation(this, R.anim.main_logo_down);
            ivLogo.startAnimation(aniHide);
        	
        	
        	btn01 = (Button)findViewById(R.id.btn_new);
        	btn02 = (Button)findViewById(R.id.btn_promotion);
        	btn03 = (Button)findViewById(R.id.btn_sale);
        	btn04 = (Button)findViewById(R.id.btn_all);
        	
        	
        	btn01.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(Mode == MODE_USER){
						Intent intent = new Intent(HomeActivity.this, NewArrivalsActivity.class);
						intent.putExtra("fromHome", true);
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					}else{
						hideLogo();
						mHandler.sendEmptyMessage(0);
					}
					
				}
			});
        	
        	
        	btn02.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Mode == MODE_USER){
						Intent intent = new Intent(HomeActivity.this, RecommandActivity.class);
						intent.putExtra("fromHome", true);
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					}else{
						hideLogo();
						mHandler.sendEmptyMessage(0);
					}
				}
			});
        	
        	btn03.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					if(Mode == MODE_USER){
						Intent intent = new Intent(HomeActivity.this, SaleActivity.class);
						intent.putExtra("fromHome", true);
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					}else{
						hideLogo();
						mHandler.sendEmptyMessage(0);
					}
					
					
					
					
				}
			});
        	
        	btn04.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Mode == MODE_USER){
						Intent intent = new Intent(HomeActivity.this, ArtistActivity.class);
						intent.putExtra("fromHome", true);
						intent.putExtra("fromMain", true);
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
						
					}else{
						hideLogo();
						mHandler.sendEmptyMessage(0);
					}
					
					
				}
			});
        	
        }
        
        mHandler = new Handler() {
        	public void handleMessage(Message msg){
        		tCounter++;
        		

        		android.util.Log.i("timer", ""+tCounter);
        		

        			
        		if(tCounter <= screenSaverOnTime){

            		mHandler.sendEmptyMessageDelayed(0, 1000);
            	
        		}else if(tCounter > screenSaverOnTime){
        			// TODO 
        			isScreenSaverMode = true;
        			tCounter = 0;
        			mHandler.removeMessages(0);
        			hideMenu();
        			Mode = MODE_SCR;
        			// Run ScreenSaver Activity
        			
        		}
        		
        		
        	}
        };
        
        
        bg01 = (ImageView)findViewById(R.id.iv_bg01);
		bg02 = (ImageView)findViewById(R.id.iv_bg02);
		bg03 = (ImageView)findViewById(R.id.iv_bg03);
		bg04 = (ImageView)findViewById(R.id.iv_bg04);
		bg05 = (ImageView)findViewById(R.id.iv_bg05);
		
		bg01.setImageURI(Uri.parse(shopdata.getDataPath(ShopData.DATA_BG, 0)));
		bg02.setImageURI(Uri.parse(shopdata.getDataPath(ShopData.DATA_BG, 1)));
		bg03.setImageURI(Uri.parse(shopdata.getDataPath(ShopData.DATA_BG, 2)));
		bg04.setImageURI(Uri.parse(shopdata.getDataPath(ShopData.DATA_BG, 3)));
		bg05.setImageURI(Uri.parse(shopdata.getDataPath(ShopData.DATA_BG, 4)));
		
		
		
		
		ba01 = getAnimation(Integer.parseInt(shopdata.getDataPath(ShopData.DATA_BG_ANI, 0)));
		ba02 = getAnimation(Integer.parseInt(shopdata.getDataPath(ShopData.DATA_BG_ANI, 1)));
		ba03 = getAnimation(Integer.parseInt(shopdata.getDataPath(ShopData.DATA_BG_ANI, 2)));
		ba04 = getAnimation(Integer.parseInt(shopdata.getDataPath(ShopData.DATA_BG_ANI, 3)));
		ba05 = getAnimation(Integer.parseInt(shopdata.getDataPath(ShopData.DATA_BG_ANI, 4)));
		
		
		
//		ba01 = AnimationUtils.loadAnimation(this, R.anim.home_bg_left);
//		ba02 = AnimationUtils.loadAnimation(this, R.anim.home_bg_left);
//		ba03 = AnimationUtils.loadAnimation(this, R.anim.home_bg_zoom);
//		ba04 = AnimationUtils.loadAnimation(this, R.anim.home_bg_shrink);
//		ba05 = AnimationUtils.loadAnimation(this, R.anim.home_bg_shrink);
		
		ba01.setStartOffset(0);
		ba02.setStartOffset(9000);
		ba03.setStartOffset(18000);
		ba04.setStartOffset(27000);
		ba05.setStartOffset(36000);
        
		
		baHandler = new Handler() {
        	public void handleMessage(Message msg){
//        		baCounter++;
        		baHandler.sendEmptyMessageDelayed(0, 46000);
//        		int tmp  = (int)(baCounter % 5);
        		
//        		startBackgroundAnimation(tmp);
        		
        		bg01.startAnimation(ba01);
        		bg02.startAnimation(ba02);
        		bg03.startAnimation(ba03);
        		bg04.startAnimation(ba04);
        		bg05.startAnimation(ba05);
            	
        			
        		
        		
        	}
        };
        
       
        
        
	}
	
	private Animation getAnimation(int kind){
		Animation ani;
		switch(kind){
			default:	
			case 1:
				
				ani = AnimationUtils.loadAnimation(this, R.anim.home_bg_zoom);
				break;
				
			case 2:
				
				ani = AnimationUtils.loadAnimation(this, R.anim.home_bg_shrink);
				break;
				
				
			case 3:
				ani = AnimationUtils.loadAnimation(this, R.anim.home_bg_left);
				break;
				
				
			case 4:
				ani = AnimationUtils.loadAnimation(this, R.anim.home_bg_up);
				break;
				
				
			case 5:
				ani = AnimationUtils.loadAnimation(this, R.anim.home_bg_down);
				break;
				
		}
		
		
		return ani;
	}
	
	
	
	
	
	private void showLogo(){
	
		Animation aniShow = AnimationUtils.loadAnimation(this, R.anim.main_logo_up);
		ivLogo.startAnimation(aniShow);
		
	}
	
	private void hideLogo(){
		
			
		Animation aniHide = AnimationUtils.loadAnimation(this, R.anim.main_logo_down);
		aniHide.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				showMenu();
				Mode = MODE_USER;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
		ivLogo.startAnimation(aniHide);
		
		
	}
	
	private void showMenu(){
		
		Animation ani01 = AnimationUtils.loadAnimation(this, R.anim.main_btn_01_up);
		Animation ani02 = AnimationUtils.loadAnimation(this, R.anim.main_btn_02_up);
		Animation ani03 = AnimationUtils.loadAnimation(this, R.anim.main_btn_03_up);
		Animation ani04 = AnimationUtils.loadAnimation(this, R.anim.main_btn_04_up);
		Animation aniMenu = AnimationUtils.loadAnimation(this, R.anim.main_menu_bg_up);
		
		rlMenu.startAnimation(aniMenu);
		btn01.startAnimation(ani01);
		btn02.startAnimation(ani02);
		btn03.startAnimation(ani03);
		btn04.startAnimation(ani04);
		
		
		
		
	}
	
	private void hideMenu(){
		
		Animation ani01 = AnimationUtils.loadAnimation(this, R.anim.main_btn_01_down);
		Animation ani02 = AnimationUtils.loadAnimation(this, R.anim.main_btn_02_down);
		Animation ani03 = AnimationUtils.loadAnimation(this, R.anim.main_btn_03_down);
		Animation ani04 = AnimationUtils.loadAnimation(this, R.anim.main_btn_04_down);
		Animation aniMenu = AnimationUtils.loadAnimation(this, R.anim.main_menu_bg_down);
		
		aniMenu.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				showLogo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		btn01.startAnimation(ani01);
		btn02.startAnimation(ani02);
		btn03.startAnimation(ani03);
		btn04.startAnimation(ani04);
		rlMenu.startAnimation(aniMenu);
			

	}
	
	
	@Override
	public void onBackPressed(){
		
		
	}
	@Override
	public void onResume(){
		super.onResume();
		
		if(isStarting){
			isScreenSaverMode = true;
			tCounter = 0;
			mHandler.removeMessages(0);
			hideMenu();
			Mode = MODE_SCR;
			isStarting = false;
			
			RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl_start);
			Animation ani = AnimationUtils.loadAnimation(this, R.anim.fade_out_delay);
			rl.startAnimation(ani);
			rl.setEnabled(false);
			
		}else{
			showMenu();
			mHandler.sendEmptyMessage(0);
			
		}
		
		
		baHandler.sendEmptyMessage(0);
		
	}
	

	
	@Override
	public void onPause(){
		super.onPause();
		
		// Reset Animation if is screensavermode  
		tCounter = 0;
		 mHandler.removeMessages(0);
		 baHandler.removeMessages(0);
	}
	
	@Override 
    public boolean onTouchEvent(MotionEvent event){
		
		 tCounter = 0;
		
	
		
		if(isScreenSaverMode){
			isScreenSaverMode = false;
			hideLogo();
			
			 mHandler.sendEmptyMessage(0);
			
		}
		
		
		
    	return super.onTouchEvent(event);
    }

}
