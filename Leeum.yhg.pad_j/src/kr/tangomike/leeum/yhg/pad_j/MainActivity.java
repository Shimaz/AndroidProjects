package kr.tangomike.leeum.yhg.pad_j;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.annotation.SuppressLint;
import android.app.Activity;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	
	private ViewPager mPager;
	private PageDots pDots;
	private Handler mHandler;
	private long tCounter = 0;
	private long screenSaverOnTime = 60;
	private SugimotoPagerAdapter adapter;
	private boolean isCounting = false;
	
	
	private ImageView ivTitle;
	private boolean isKorean = true;
	
//	private RelativeLayout rlContent;
	
	private ScrollView scrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        
        ivTitle = (ImageView)findViewById(R.id.iv_title);
       
        ivTitle.setImageResource(R.drawable.j_1_title_kor);
        
        
//        rlContent = (RelativeLayout)findViewById(R.id.rl_content);
        scrl = (ScrollView)findViewById(R.id.scrl_content);
        
        adapter = new SugimotoPagerAdapter();
		mPager = (ViewPager)findViewById(R.id.pager_sugimoto);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				pDots.setDotPosition(mPager.getCurrentItem());
				setPageContent(mPager.getCurrentItem(), isKorean);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				tCounter = 0;
				if(!isCounting && mPager.getCurrentItem() == 0){
					isCounting = true;
//					mHandler.sendEmptyMessageDelayed(0, 1000);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		
		pDots = (PageDots)findViewById(R.id.pdots);
		pDots.setDotCount(adapter.getCount());
		pDots.setUnfocusedDot(R.drawable.dot_unselected);
		pDots.setFocusedDot(R.drawable.dot_selected);
		pDots.setDotMargin(11);
		
		
		
		final Button btnLang = (Button)findViewById(R.id.btn_lang);
		btnLang.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				tCounter = 0;
				
				
				if(isKorean){
					isKorean = false;
					
					btnLang.setBackgroundResource(R.drawable.btn_kor);
					
					
				}else{
					isKorean = true;
					
					btnLang.setBackgroundResource(R.drawable.btn_eng);
				}
				
				setPageContent(mPager.getCurrentItem(), isKorean);
				
				
				
			}
		});
		
		Button btnLeft = (Button)findViewById(R.id.btn_left);
		btnLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				tCounter = 0;
				
				if(mPager.getCurrentItem() != 0){
					mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
				}
			}
		});
		
		Button btnRight = (Button)findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				tCounter = 0;
					
				if(mPager.getCurrentItem() < adapter.getCount() - 1){
				
					mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
					
				}
				
			}
		});
		
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
            	
        		}else if(tCounter > screenSaverOnTime){
        			// TODO 
        			tCounter = 0;
        			mHandler.removeMessages(0);
        			
        			
        			// Run ScreenSaver Activity
        			mPager.setCurrentItem(0, true);
        			isCounting = false;
        			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
        			finish();
        		}
        		
        		
        	}
        };
        
        mHandler.sendEmptyMessage(0);
		
     // set initial contents
        setPageContent(0, true);
        
	}
	
	
	
	private void setPageContent(int pageNumber, boolean lang){
		
		
		

		String tmp = String.format("%d", pageNumber + 1);
        
		scrl.removeViews(0, scrl.getChildCount());
		
		
		
		LinearLayout llContent = new LinearLayout(getApplicationContext());
		llContent.setOrientation(LinearLayout.VERTICAL);
		llContent.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		
		
		if(lang){
			int rID = getResources().getIdentifier("j_" + tmp + "_title_kor", "drawable", getPackageName()); 
	        ivTitle.setImageResource(rID);
	        
	        
	        int i = 1;
	        while(true){
	        	
	        	String ttt = "j_" + tmp + "_text_kor_" + i;
	        	android.util.Log.i("scrl", ttt);
	        	
	        	if(getResources().getIdentifier("j_" + tmp + "_text_kor_" + i, "drawable", getPackageName()) != 0){
	        		ImageView iv = new ImageView(getApplicationContext());
	        		iv.setImageResource(getResources().getIdentifier("j_" + tmp + "_text_kor_" + i, "drawable", getPackageName()));
	        		llContent.addView(iv);
	        		
	        		android.util.Log.i("content", "j_" + tmp + "_text_kor_" + i);
	        		
	        		i++;
	        	}else{
	        		break;
	        	}
	        	
	        	
	        }
	        
	        
		}else{
			int rID = getResources().getIdentifier("j_" + tmp + "_title_eng", "drawable", getPackageName()); 
	        ivTitle.setImageResource(rID);
	        
	        
	        int i = 1;
	        while(true){
	        	if(getResources().getIdentifier("j_" + tmp + "_text_eng_" + i, "drawable", getPackageName()) != 0){
	        		ImageView iv = new ImageView(getApplicationContext());
	        		iv.setImageResource(getResources().getIdentifier("j_" + tmp + "_text_eng_" + i, "drawable", getPackageName()));
	        		llContent.addView(iv);
	        		
	        		android.util.Log.i("content", "j_" + tmp + "_text_eng_" + i);
	        		
	        		i++;
	        	}else{
	        		break;
	        	}
	        	
	        	
	        }
		}
		scrl.addView(llContent);
		scrl.scrollTo(0, 0);
		scrl.requestLayout();
		

		
		
		
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
	public class SugimotoPagerAdapter extends PagerAdapter{

		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}
		
		@SuppressLint("DefaultLocale")
		public Object instantiateItem(View collection, int position) {

			
	        ImageView imgview = new ImageView(getBaseContext());
	        
	        String tmp = String.format("%d", position + 1);
	        
	        int rID = getResources().getIdentifier("j_" + tmp + "_img", "drawable", getPackageName()); 
	        imgview.setImageResource(rID);
	        
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
