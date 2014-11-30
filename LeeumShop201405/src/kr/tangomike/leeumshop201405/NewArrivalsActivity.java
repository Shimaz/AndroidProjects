package kr.tangomike.leeumshop201405;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import android.widget.Toast;

@SuppressLint({ "DefaultLocale", "HandlerLeak" })
public class NewArrivalsActivity extends Activity {
	
	private ShopData shopdata; 
	
	private ViewPager mPager;
	private CameraPagerAdapter adapter;
	private Handler mHandler;
	private long tCounter = 0;
	private long screenSaverOnTime = ShopData.SCREEN_SAVER_TIME;
	
	private Button btnPrev;
	private Button btnNext;
	private Button btnThumb;
	private Button btnDetail;
	
	private TextView tvDetail;
	private TextView tvNum;
	
	private ScrollView scrlDetail;
	
	private Animation fadeOut;
	private Animation fadeIn;
	private Animation rotate;
	private Animation rotateReverse;
	
	
	private RelativeLayout rlNewArrival;
	
	
	private Button btn01;
	private Button btn02;
	private Button btn03;
	private Button btn04;
	
	private Button btnLang;
	
	private boolean isDetailTextOn = false;
	private boolean isStarting = true;
	private boolean isChanging = false;
	private boolean isKorean = true;
	
//	private int nowPage = 0;
//	private int beforePage;
	
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
		setContentView(R.layout.layout_new_arrival);
		
		shopdata = (ShopData)getApplicationContext();
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        tvNum = (TextView)findViewById(R.id.tv_page_num);
        tvNum.setTextColor(Color.BLACK);
		tvNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, 26);
		tvNum.setSingleLine(false);
		tvNum.setLineSpacing(0, 1.3f);
		tvNum.setFocusable(false);
		tvNum.setFocusableInTouchMode(false);
        
        rlNewArrival = (RelativeLayout)findViewById(R.id.rl_new_arrival);
       
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_short);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_short);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotateReverse = AnimationUtils.loadAnimation(this, R.anim.rotate_reverse);
        tvDetail = (TextView)findViewById(R.id.tv_detail);
        
        scrlDetail = (ScrollView)findViewById(R.id.scrl_detail);
        
        
        
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				btnLang.setAlpha(0);
				
			}
		});
        
        /**
         * Temporary Contents Initialization
         */
        {
        	btn01 = new Button(this);
        	btn02 = new Button(this);
        	btn03 = new Button(this);
        	btn04 = new Button(this);
        	
        	btn01.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	btn02.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	btn03.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	btn04.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	
        	btn01.setBackgroundResource(R.drawable.btn_main_new);
        	btn02.setBackgroundResource(R.drawable.btn_main_promotion_reverse);
        	btn03.setBackgroundResource(R.drawable.btn_main_sale_reverse);
        	btn04.setBackgroundResource(R.drawable.btn_main_all_reverse);
        	
        	
        	btn01.setX(250);
        	btn02.setX(828);
        	btn03.setX(1366);
        	btn04.setX(1998);
        	
        	btn01.setY(1400);
        	btn02.setY(1400);
        	btn03.setY(1400);
        	btn04.setY(1400);
        	
        	rlNewArrival.addView(btn01);
        	rlNewArrival.addView(btn02);
        	rlNewArrival.addView(btn03);
        	rlNewArrival.addView(btn04);
        	
        	btn01.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
//					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					
					mPager.setCurrentItem(0);
					
				}
			});
        	
        	
        	btn02.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!isChanging){
						isChanging = true;
						Intent intent = new Intent(NewArrivalsActivity.this, RecommandActivity.class);
						intent.putExtra("fromHome", false);
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
						finish();
						
					}
					
					
					
				}
			});
        	
        	
        	btn03.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!isChanging){
						isChanging = false;
						Intent intent = new Intent(NewArrivalsActivity.this, SaleActivity.class);
						intent.putExtra("fromHome", false);
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
						finish();
					}
					
					
					
				}
			});
        	
        	
        	btn04.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!isChanging){
						isChanging = true;
						Intent intent = new Intent(NewArrivalsActivity.this, ArtistActivity.class);
						intent.putExtra("fromHome", false);
						intent.putExtra("fromMain", true);
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
						finish();
						
					}
					
					
				}
			});
        	

			btnPrev = (Button)findViewById(R.id.btn_left);
			btnNext = (Button)findViewById(R.id.btn_right);
			
			
			
			btnNext.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					tCounter = 0;
					if(mPager.getCurrentItem()+1 != adapter.getCount()){
						
						mPager.setCurrentItem(mPager.getCurrentItem() + 1);
					}else{
//						Toast toast = Toast.makeText(getApplicationContext(), ShopData.LAST_PAGE, Toast.LENGTH_SHORT);
//						toast.show();
					}
					
					
					if(isDetailTextOn){
						showDetailText();
					}
					
				}
			});
			
			
			btnPrev.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					tCounter = 0;
					
					if(mPager.getCurrentItem() != 0){
						
						mPager.setCurrentItem(mPager.getCurrentItem() - 1);
						
					}else{
//						Toast toast = Toast.makeText(getApplicationContext(), ShopData.FIRST_PAGE, Toast.LENGTH_SHORT);
//						toast.show();
					}
					if(isDetailTextOn){
						showDetailText();
					}
					
				}
			});
			
			btnPrev.setAlpha(0.0f);
			
			
			
			
			btnThumb = (Button)findViewById(R.id.btn_thumb);
			
			btnThumb.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					tCounter = 0;
					mHandler.removeMessages(0);
					
					Intent intent = new Intent(NewArrivalsActivity.this, ThumbNailsActivity.class);
					intent.putExtra("category", ShopData.DATA_NEW);
					
					startActivityForResult(intent, ShopData.DATA_NEW);
					
					overridePendingTransition(R.anim.slide_in, R.anim.fade_out_short);

				}
			});
			
			btnDetail = (Button)findViewById(R.id.btn_detail);
			
			btnDetail.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					tCounter = 0;
					
						
					showDetailText();
						
						
						
					
				}
			});
			
			
			btnLang = (Button)findViewById(R.id.btn_language);
			
			btnLang.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(isDetailTextOn&&!isKorean){
						
						btnLang.setBackgroundResource(R.drawable.btn_kor);
						isKorean = true;
						
						
						
						File file = new File(shopdata.getDataPath(ShopData.DATA_NEW, mPager.getCurrentItem()) + "k.txt");
						if(file.exists()){
							String str = null;
							try {
								BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"), 8192);
								StringBuilder sb = new StringBuilder();
								while((str = br.readLine()) != null){
									sb.append(str);
									 sb.append("\n");
								}
								br.close();
								sb.trimToSize();
								
								String detail = sb.toString();
								
								tvDetail.setText(detail);
								
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}else{
							tvDetail.setText(ShopData.NO_DESC_KOR);
						}
						
						
					}else if(isDetailTextOn&&isKorean){
						
						btnLang.setBackgroundResource(R.drawable.btn_eng);
						isKorean = false;
						
						
						
						File file = new File(shopdata.getDataPath(ShopData.DATA_NEW, mPager.getCurrentItem()) + "e.txt");
						if(file.exists()){
							String str = null;
							try {
								BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"), 8192);
								StringBuilder sb = new StringBuilder();
								while((str = br.readLine()) != null){
									sb.append(str);
									 sb.append("\n");
								}
								br.close();
								sb.trimToSize();
								
								String detail = sb.toString();
								
								tvDetail.setText(detail);
								
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}else{
							tvDetail.setText(ShopData.NO_DESC_ENG);
						}
						
						
					}
				
					
					scrlDetail.setScrollX(0);
					
				}
			});
			
			
		}
        
        
        if(getIntent().getExtras().getBoolean("fromHome")){
        	
        	Animation leftArrow = AnimationUtils.loadAnimation(this, R.anim.arrow_left_in);
        	Animation rightArrow = AnimationUtils.loadAnimation(this, R.anim.arrow_right_in);
        	
        	btnPrev.startAnimation(leftArrow);
        	btnNext.startAnimation(rightArrow);
        	
        	android.util.Log.i("intent", "from start");
        }
        
       
        
        
       
       
       
        adapter = new CameraPagerAdapter();
		mPager = (ViewPager)findViewById(R.id.pager_new_arrival);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
	
		
		
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// TODO: 여기서 변경합시다. TextView던 뭐던 하나 놓고. 
				
				if(mPager.getCurrentItem() == 0){
					
					btnPrev.setAlpha(0.0f);
					
				}else if(mPager.getCurrentItem() + 1 == adapter.getCount()){
					
					btnNext.setAlpha(0.0f);
					
				}else{
					btnPrev.setAlpha(1.0f);
					btnNext.setAlpha(1.0f);
				}
				String str = String.format("%03d / %03d", mPager.getCurrentItem() + 1, adapter.getCount());
				tvNum.setText(str);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				tCounter = 0;
				if(!isStarting){
					btnDetail.setAlpha(0);
				}
				isStarting = false;
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
				if(arg0 == ViewPager.SCROLL_STATE_IDLE){
					
					btnDetail.setAlpha(1.0f);
					scrlDetail.setAlpha(1.0f);
					btnDetail.startAnimation(fadeIn);
					isKorean = true;
					btnLang.setBackgroundResource(R.drawable.btn_kor);
					
//					nowPage = mPager.getCurrentItem();
//					
//					if(nowPage == adapter.getCount()-1 && nowPage == beforePage)  { // Last Page
//						Toast toast = Toast.makeText(getApplicationContext(), ShopData.LAST_PAGE, Toast.LENGTH_SHORT);
//						toast.show();
//					}
//					
//					
//					if(nowPage == 0 && nowPage == beforePage){ // firstPage
//						Toast toast = Toast.makeText(getApplicationContext(), ShopData.FIRST_PAGE, Toast.LENGTH_SHORT);
//						toast.show();
//					}
//					
//					beforePage = nowPage;
					
					
				}else if(arg0 == ViewPager.SCROLL_STATE_DRAGGING && isDetailTextOn){
					
					//showDetailText();
					
					btnDetail.setRotation(0);
					btnDetail.setAlpha(0);
					scrlDetail.setAlpha(0);
					tvDetail.setAlpha(0);
					isDetailTextOn = false;
					btnLang.setAlpha(0);
					
				}
				
			}
		});
		
		String str = String.format("%03d / %03d", mPager.getCurrentItem()+1, adapter.getCount());
		tvNum.setText(str);
		
		
		
		
		
		
		
		
		
		

		
        mHandler = new Handler() {
        	public void handleMessage(Message msg){
        		tCounter++;
        		

        		android.util.Log.i("timer", ""+tCounter);
        		

        			
        		if(tCounter <= screenSaverOnTime){

            		mHandler.sendEmptyMessageDelayed(0, 1000);
            	
        		}else if(tCounter > screenSaverOnTime){
        			// TODO 
        			tCounter = 0;
        			mHandler.removeMessages(0);
        			finish();
        			
        			// Run ScreenSaver Activity
        			
        		}
        		
        		
        	}
        };
        
        mHandler.sendEmptyMessage(0);
		
	}
	
	private void showDetailText(){
		if(isDetailTextOn){
			isDetailTextOn = false;
			
			tvDetail.startAnimation(fadeOut);
			btnLang.startAnimation(fadeOut);
			btnDetail.startAnimation(rotateReverse);
			
			scrlDetail.startAnimation(fadeOut);
			
			
		}else{
			
			btnDetail.startAnimation(rotate);
			
			isDetailTextOn = true;
			
			tvDetail.setTextColor(Color.BLACK);
			tvDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, ShopData.FONT_SIZE);
			tvDetail.setSingleLine(false);
			tvDetail.setLineSpacing(0, 1.3f);
			tvDetail.setFocusable(false);
			tvDetail.setFocusableInTouchMode(false);

			File file;
			if(isKorean){
				file = new File(shopdata.getDataPath(ShopData.DATA_NEW, mPager.getCurrentItem()) + "k.txt");
			}else{
				file = new File(shopdata.getDataPath(ShopData.DATA_NEW, mPager.getCurrentItem()) + "e.txt");
			}
			
			if(file.exists()){
				String str = null;
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"), 8192);
					StringBuilder sb = new StringBuilder();
					while((str = br.readLine()) != null){
						sb.append(str);
						 sb.append("\n");
					}
					br.close();
					sb.trimToSize();
					
					String detail = sb.toString();
					
					tvDetail.setText(detail);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				if(isKorean){
					tvDetail.setText(ShopData.NO_DESC_KOR);
				}else{
					tvDetail.setText(ShopData.NO_DESC_ENG);
				}
				
			}			
			
			
			tvDetail.setAlpha(1.0f);
			tvDetail.startAnimation(fadeIn);
			btnLang.setAlpha(1.0f);
			btnLang.startAnimation(fadeIn);
			
			scrlDetail.setScrollY(0);
			scrlDetail.setAlpha(1.0f);
			scrlDetail.startAnimation(fadeIn);
		}
	}
	
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(requestCode == ShopData.DATA_NEW){
			
			mHandler.sendEmptyMessage(0);
			
			if(resultCode == RESULT_OK){
				
				int tmp = data.getExtras().getInt("pNumber");
				
				mPager.setCurrentItem(tmp);
				
				if(isDetailTextOn){
//					showDetailText();
					tvDetail.setAlpha(0);
					scrlDetail.setAlpha(0);
					btnLang.setAlpha(0);
					isDetailTextOn = false;
					btnDetail.startAnimation(rotateReverse);
					
				}
				
				
			}
		}
		
	}
	
	
	@Override 
    public boolean onTouchEvent(MotionEvent event){
    	
		tCounter = 0;
		
    	return super.onTouchEvent(event);
    }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		mHandler.removeMessages(0);
	}
	
	
	@Override
	public void onBackPressed(){
		if(!isChanging){
			isChanging = true;
			finish();
			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
		}
		
	}
	

	/**
	 * 
	 * @author shimaz
	 * TODO: PageAdapter for ViewPager
	 *
	 */
	@SuppressLint("DefaultLocale")
	public class CameraPagerAdapter extends PagerAdapter{

		
		
		
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			
			return shopdata.getDataCount(ShopData.DATA_NEW);
			
			
		}
		
		@SuppressLint("DefaultLocale")
		public Object instantiateItem(View collection, int position) {

			
	        ImageView imgview = new ImageView(getBaseContext());
	        String tmp = shopdata.getDataPath(ShopData.DATA_NEW, position);
	        
	        imgview.setImageURI(Uri.parse(tmp+".jpg"));
	        
	        ((ViewPager)collection).addView(imgview, 0);

	      
	        
	        
	        return imgview;
	    }
		

		
	    @Override
	    public void destroyItem(View arg0, int arg1, Object arg2) {
	        ((ViewPager) arg0).removeView((View) arg2);

	    }

	    @Override
	    public boolean isViewFromObject(View arg0, Object arg1) {
	        
	    	return arg0 == ((View) arg1);
	        

	    }

	    @Override
	    public Parcelable saveState() {
	        return null;
	    }

		
	}

}
