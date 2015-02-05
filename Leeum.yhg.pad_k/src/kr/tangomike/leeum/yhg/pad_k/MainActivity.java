package kr.tangomike.leeum.yhg.pad_k;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.annotation.SuppressLint;
import android.app.Activity;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	
	private ViewPager mPager;
	private Handler mHandler;
	private long tCounter = 0;
	private long screenSaverOnTime = 60;
	private SugimotoPagerAdapter adapter;
	private boolean isCounting = false;
	
	
	
//	private RelativeLayout rlContent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_image_only);
		
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        
        adapter = new SugimotoPagerAdapter();
		mPager = (ViewPager)findViewById(R.id.pager_sugimoto);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				tCounter = 0;
				if(!isCounting && mPager.getCurrentItem() == 0){
					isCounting = true;
					mHandler.sendEmptyMessageDelayed(0, 1000);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
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
        		}
        		
        		
        	}
        };
        
        mHandler.sendEmptyMessage(0);
		

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
			return 21;
		}
		
		@SuppressLint("DefaultLocale")
		public Object instantiateItem(View collection, int position) {

			
	        ImageView imgview = new ImageView(getBaseContext());
	        
	        String tmp = String.format("%d", position + 1);
	        
	        int rID = getResources().getIdentifier("k_img_" + tmp, "drawable", getPackageName()); 
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
