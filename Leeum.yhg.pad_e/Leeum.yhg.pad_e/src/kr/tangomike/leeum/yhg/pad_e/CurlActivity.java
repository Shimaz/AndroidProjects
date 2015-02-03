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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;


/**
 * Simple Activity for curl testing.
 * 
 * @author harism
 */
public class CurlActivity extends Activity {

	private CurlView mCurlView;
    private int[] mBitmapIds = {R.drawable.e_kor_1, R.drawable.e_kor_2, R.drawable.e_kor_3, R.drawable.e_kor_4, R.drawable.e_kor_5, R.drawable.e_kor_6, R.drawable.e_kor_7, R.drawable.e_kor_8, R.drawable.e_kor_9, R.drawable.e_kor_10, R.drawable.e_kor_11, R.drawable.e_kor_12, R.drawable.e_kor_13};

    private SeekBar sBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);



        /*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



		int index = 0;
		if (getLastNonConfigurationInstance() != null) {
			index = (Integer) getLastNonConfigurationInstance();
		}
		mCurlView = (CurlView) findViewById(R.id.curl);
		mCurlView.setPageProvider(new PageProvider());
		mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		mCurlView.setCurrentIndex(index);
//		mCurlView.setBackgroundColor(0xFF202830);
		mCurlView.setBackgroundColor(0xFFFFFFFF);


		// This is something somewhat experimental. Before uncommenting next
		// line, please see method comments in CurlView.
		// mCurlView.setEnableTouchPressure(true);



        final Button btnTmp = (Button)findViewById(R.id.btn_lang);
        btnTmp.setBackgroundResource(R.drawable.btn_eng);
        btnTmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CurlActivity.this, CurlEngActivity.class);
                startActivity(intent);
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



	@Override
	public Object onRetainNonConfigurationInstance() {
		return mCurlView.getCurrentIndex();
	}

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


            Bitmap front = loadBitmap(width, height, index);
            page.setTexture(front, CurlPage.SIDE_FRONT);
            page.setColor(Color.rgb(180,180,180), CurlPage.SIDE_BACK);

            sBar.setProgress(index);

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

    }

}