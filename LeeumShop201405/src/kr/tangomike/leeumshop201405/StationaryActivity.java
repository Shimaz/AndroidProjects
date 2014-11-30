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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
//import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class StationaryActivity extends Activity{

	private ShopData shopdata; 
	private ViewPager mPager;
	private StationaryPagerAdapter adapter;

	private Handler mHandler;
	private long tCounter = 0;
	private long screenSaverOnTime = ShopData.SCREEN_SAVER_TIME;
	
	private Button btnPrev;
	private Button btnNext;
	private Button btnThumb;
	private Button btnDetail;
	private Button btnHome;
	private Button btnLang;
	
	private TextView tvDetail;
	private TextView tvNum;

	private ScrollView scrlDetail;
	
	private Button btnArtist;
	private Button btnLiving;
	private Button btnStationary;
	private Button btnCrafts;
	private Button btnPrints;
	
	private Animation fadeOut;
	private Animation fadeIn;
	private Animation rotate;
	private Animation rotateReverse;
	
	private boolean isDetailTextOn = false;
	private boolean isChanging = false;
	private boolean isKorean = true;

//	private int nowPage = 0;
//	private int beforePage;
	
	
	@SuppressLint({ "HandlerLeak", "DefaultLocale" })
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		setContentView(R.layout.layout_stationary);
		
		
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
        
        
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_short);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_short);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotateReverse = AnimationUtils.loadAnimation(this, R.anim.rotate_reverse);
        
        
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
        
        
        tvDetail = (TextView)findViewById(R.id.tv_detail);
        
        
        
        
        scrlDetail = (ScrollView)findViewById(R.id.scrl_detail);

        
        btnArtist = (Button)findViewById(R.id.btn_artist);
        btnLiving = (Button)findViewById(R.id.btn_living);
        btnStationary = (Button)findViewById(R.id.btn_stationary);
        btnCrafts = (Button)findViewById(R.id.btn_crafts);
        btnPrints = (Button)findViewById(R.id.btn_prints);
        btnHome = (Button)findViewById(R.id.btn_home);
        
        
        btnHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if(!isChanging){
					isChanging = true;
					outAnimation();
				}
				
				
			}
		});
        
        
        btnArtist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isChanging){
					isChanging = true;
					Intent intent = new Intent(StationaryActivity.this, ArtistActivity.class);
					intent.putExtra("fromHome", false);
					intent.putExtra("fromMain", false);
					startActivity(intent);
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					finish();
				}
				
				
			}
		});
        
        btnLiving.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isChanging){
					isChanging = true;
					Intent intent = new Intent(StationaryActivity.this, LivingActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					finish();
				}
				
				
			}
		});
        
        btnStationary.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				mPager.setCurrentItem(0);
				
			}
		});
        
        btnCrafts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isChanging){
					isChanging = true;
					Intent intent = new Intent(StationaryActivity.this, CraftsActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					finish();
					
				}
				
				
			}
		});
        
        btnPrints.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isChanging){
					isChanging = true;
					Intent intent = new Intent(StationaryActivity.this, PrintsActivity.class);
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
					
//					Toast toast = Toast.makeText(getApplicationContext(), ShopData.LAST_PAGE, Toast.LENGTH_SHORT);
//					toast.show();
					
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
					
//					Toast toast = Toast.makeText(getApplicationContext(), ShopData.FIRST_PAGE, Toast.LENGTH_SHORT);
//					toast.show();
					
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
				
				Intent intent = new Intent(StationaryActivity.this, ThumbNailsActivity.class);
				intent.putExtra("category", ShopData.DATA_STATIONARY);
				
				startActivityForResult(intent, ShopData.DATA_STATIONARY);
				
				overridePendingTransition(R.anim.slide_in	, R.anim.fade_out_short);

			}
		});
		
		btnDetail = (Button)findViewById(R.id.btn_detail);
		
		btnDetail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tCounter = 0;
				
				if(mPager.getCurrentItem() != 0){
					
					showDetailText();
					
					
					
				}
				
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
					
					
					
					File file = new File(shopdata.getDataPath(ShopData.DATA_STATIONARY, mPager.getCurrentItem()) + "k.txt");
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
					
					
					
					File file = new File(shopdata.getDataPath(ShopData.DATA_STATIONARY, mPager.getCurrentItem()) + "e.txt");
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
				
				scrlDetail.setScrollY(0);
			
				
			}
		});
		
		
        
        adapter = new StationaryPagerAdapter();
		mPager = (ViewPager)findViewById(R.id.pager_stationary);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		
		
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@SuppressLint("DefaultLocale")
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
				
				String str = String.format("%03d / %03d", mPager.getCurrentItem(), adapter.getCount() - 1);
				tvNum.setText(str);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				tCounter = 0;
				
				btnDetail.setAlpha(0);
				
//				isStarting = false;
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
				if(arg0 == ViewPager.SCROLL_STATE_IDLE){
					
					if(mPager.getCurrentItem() != 0){
						btnDetail.setAlpha(1.0f);
						btnDetail.startAnimation(fadeIn);
						isKorean = true;
						btnLang.setBackgroundResource(R.drawable.btn_kor);
					}
					
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
					tvDetail.setAlpha(0);
					isDetailTextOn = false;
					btnLang.setAlpha(0);
					scrlDetail.setAlpha(0);
					
				}
				
			}
		});
		
		String str = String.format("%03d / %03d", mPager.getCurrentItem(), adapter.getCount() - 1);
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
        			
        			outAnimation();
        			
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
				file = new File(shopdata.getDataPath(ShopData.DATA_STATIONARY, mPager.getCurrentItem()) + "k.txt");
			}else{
				file = new File(shopdata.getDataPath(ShopData.DATA_STATIONARY, mPager.getCurrentItem()) + "e.txt");
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
			
			scrlDetail.setScrollY(0);
			scrlDetail.setAlpha(1.0f);
			scrlDetail.startAnimation(fadeIn);
			
			btnLang.setAlpha(1.0f);
			btnLang.startAnimation(fadeIn);
			
		}
	}

	
	
		
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		if(requestCode == ShopData.DATA_STATIONARY){
			
			mHandler.sendEmptyMessage(0);
			
			if(resultCode == RESULT_OK){
				
				int tmp = data.getExtras().getInt("pNumber");
				
				mPager.setCurrentItem(tmp);
				
				if(isDetailTextOn){
					tvDetail.setAlpha(0);
					btnLang.setAlpha(0);
					isDetailTextOn = false;
//					btnDetail.setRotation(0);
					btnDetail.startAnimation(rotateReverse);
					scrlDetail.setAlpha(0);
				}
				
				
			}
		}
		
	}
	
	
	private void outAnimation(){
		
		Animation out01 = AnimationUtils.loadAnimation(this, R.anim.sub_out_01);
		Animation out02 = AnimationUtils.loadAnimation(this, R.anim.sub_out_02);
		Animation out03 = AnimationUtils.loadAnimation(this, R.anim.sub_out_03);
		Animation out04 = AnimationUtils.loadAnimation(this, R.anim.sub_out_04);
		Animation out05 = AnimationUtils.loadAnimation(this, R.anim.sub_out_05);
		Animation out06 = AnimationUtils.loadAnimation(this, R.anim.sub_out_06);
		
		
		out06.setAnimationListener(new AnimationListener (){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				finish();
				overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		ImageView ivLine = (ImageView)findViewById(R.id.iv_sub_line);
		ImageView ivAll = (ImageView)findViewById(R.id.iv_all);
		
		btnHome.startAnimation(fadeOut);
		ivLine.startAnimation(fadeOut);
		ivAll.startAnimation(out01);
		btnArtist.startAnimation(out02);
		btnLiving.startAnimation(out03);
		btnStationary.startAnimation(out04);
		btnCrafts.startAnimation(out05);
		btnPrints.startAnimation(out06);
		
		
		
		
	}
	
	@Override
	public void onBackPressed(){
		if(!isChanging){
			isChanging = true;
			outAnimation();
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
	
	
	
	
	
	
	/**
	 * 
	 * @author shimaz
	 * TODO: PageAdapter for ViewPager
	 *
	 */
	@SuppressLint("DefaultLocale")
	public class StationaryPagerAdapter extends PagerAdapter{

		
		
		
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			
			return shopdata.getDataCount(ShopData.DATA_STATIONARY);
			
			
		}
		
		@SuppressLint("DefaultLocale")
		public Object instantiateItem(View collection, int position) {

			
	        ImageView imgview = new ImageView(getBaseContext());
	        String tmp = shopdata.getDataPath(ShopData.DATA_STATIONARY, position);
	        
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
