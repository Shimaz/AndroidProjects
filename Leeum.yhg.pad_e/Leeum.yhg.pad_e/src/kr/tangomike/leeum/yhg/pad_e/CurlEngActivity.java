package kr.tangomike.leeum.yhg.pad_e;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;


/**
 * Simple Activity for curl testing.
 * 
 * @author harism
 */
public class CurlEngActivity extends Activity {

	private CurlView mCurlView;
	private int[] mBitmapIds = {R.drawable.e_eng_1, R.drawable.e_eng_2, R.drawable.e_eng_3, R.drawable.e_eng_4, R.drawable.e_eng_5, R.drawable.e_eng_6, R.drawable.e_eng_7, R.drawable.e_eng_8, R.drawable.e_eng_9, R.drawable.e_eng_10, R.drawable.e_eng_11, R.drawable.e_eng_12, R.drawable.e_eng_13, R.drawable.e_eng_14, R.drawable.e_eng_15, R.drawable.e_eng_16, R.drawable.e_eng_17, R.drawable.e_eng_18};

    private SeekBar sBar;
    
    private Handler mHandler;
    
    private int tCounter;
    private static final int screenSaverOnTime = 120;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
	
        /*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        tCounter = 0;

		int index = 0;
		if (getLastNonConfigurationInstance() != null) {
			index = (Integer) getLastNonConfigurationInstance();
		}
		mCurlView = (CurlView) findViewById(R.id.curl);
		mCurlView.setPageProvider(new PageProvider());
		mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		mCurlView.setCurrentIndex(index);
		mCurlView.setBackgroundColor(0x00ffffff);
		mCurlView.setAllowLastPageCurl(false);
//		mCurlView.setBackgroundResource(R.drawable.e_kor_14);
	


		// This is something somewhat experimental. Before uncommenting next
		// line, please see method comments in CurlView.
		// mCurlView.setEnableTouchPressure(true);



        final Button btnTmp = (Button)findViewById(R.id.btn_lang);
        btnTmp.setBackgroundResource(R.drawable.btn_kor);
        btnTmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CurlEngActivity.this, CurlActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
                finish();

            }
        });

        sBar = (SeekBar)findViewById(R.id.prgs_page);
        sBar.setMax(mBitmapIds.length - 1);
        sBar.setProgress(0);
        
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onStopTrackingTouch(SeekBar seekbar){

                android.util.Log.i("seekbar", "" + seekbar.getProgress());

                mCurlView.setCurrentIndex(seekbar.getProgress());

            }

            public void onStartTrackingTouch(SeekBar seekbar){

            }

            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){

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
        			
        			Intent intent = new Intent(CurlEngActivity.this, CurlActivity.class);
        			startActivity(intent);
        			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
        			finish();
        		}
        		
        		
        	}
        };
        
        mHandler.sendEmptyMessage(0);

	}
	


	@Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}


//
//	@Override
//	public Object onRetainNonConfigurationInstance() {
//		return mCurlView.getCurrentIndex();
//	}

	/**
	 * Bitmap provider.
	 */
	private class PageProvider implements CurlView.PageProvider {


		



		@Override
		public int getPageCount() {

            int retVal = mBitmapIds.length;

			return retVal;
			
		}

		private Bitmap loadBitmap(int width, int height, int index) {
			
		
			
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0x00FFFFFF);  
			Canvas c = new Canvas(b);

			Drawable d = getResources().getDrawable(mBitmapIds[index]);



			int margin = 0;
			int border = 0;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			r.left += ((r.width() - imageWidth) / 2) - border;
			r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
//			p.setColor(0xFFC0C0C0);
			p.setColor(Color.TRANSPARENT);
			c.drawRect(r, p);
			r.left += border;																							
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);

			return b;
		}
		


		@Override
		public void updatePage(CurlPage page, int width, int height, int index) {


			tCounter = 0;
			
            Bitmap front = loadBitmap(width, height, index);
            page.setTexture(front, CurlPage.SIDE_FRONT);
            page.setColor(Color.rgb(180,180,180), CurlPage.SIDE_BACK);

 
            android.util.Log.i("index", "" + mCurlView.getCurrentIndex() + " " +  mCurlView.getCurlState() + " " + mCurlView.getCurlTarget());

            
            
		}

	


	}

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			if (w > h) {
				mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
				mCurlView.setMargins(.1f, .05f, .1f, .05f);
			} else {
				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
//				mCurlView.setMargins(.1f, .1f, .1f, .1f);
				
			}
		}
	}

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeMessages(0);

    }

}