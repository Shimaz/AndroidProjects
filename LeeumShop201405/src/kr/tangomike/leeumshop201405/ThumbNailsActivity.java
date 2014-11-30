package kr.tangomike.leeumshop201405;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class ThumbNailsActivity extends Activity {
/**
 * 
 * Timer 셋팅 후, 타임아웃일 경우 RESULT_OK에 TIME_OUT_CODE를 보내고 이 액태비티 및 상위 액티비티 또한 finish 되도록
 * 
 * 
 */
	
	private int dataCategory;
	private ShopData shopdata;
	private RelativeLayout rlBase;
	private OnClickListener ol;
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		setContentView(R.layout.layout_thumbnails);
		
		Intent intent = getIntent();
		dataCategory = intent.getExtras().getInt("category");
		shopdata = (ShopData)getApplicationContext();
		
		rlBase = (RelativeLayout)findViewById(R.id.rl_base);
		
		ol = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = v.getTag().toString();
				int tmp = Integer.parseInt(str);
				Intent intent = new Intent();
				intent.putExtra("pNumber", tmp);
				setResult(RESULT_OK, intent);
				overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				finish();
				
				
				
				
			}
			
		};
		
		addButtons();
		
		
	}
	
	private void addButtons(){
		
		int scrlWidth = 528 * (int)Math.ceil((float)shopdata.getDataCount(dataCategory)/3.0f);
		
//		android.util.Log.i("shimaz", "count:" + shopdata.getDataCount(dataCategory) + " columns: " +(int)Math.ceil((float)shopdata.getDataCount(dataCategory)/3.0f) + " raw: " + (float)shopdata.getDataCount(dataCategory)/(float)3);
		
		switch(dataCategory){
			default:
			case ShopData.DATA_NEW:
//			case ShopData.DATA_RECOMMAND:
			case ShopData.DATA_SALE:
				
				RelativeLayout rlImage = new RelativeLayout(this);
//				rlImage.setLayoutParams(new LayoutParams((int)(Math.ceil(shopdata.getDataCount(dataCategory)/3)), 1600));
				rlImage.setLayoutParams(new LayoutParams(scrlWidth, 1600));
				for(int i = 0; i < shopdata.getDataCount(dataCategory); i++){
					
					int tmpX = 16 + (int)(( i / 3 ) * 528 );
					int tmpY = 16 + (( i % 3 ) * 528);
					
					
					
					String str = shopdata.getDataPath(dataCategory, i);
					ImageView iv = new ImageView(this);
					File file = new File(str+"t.jpg");
					if(file.exists()){
						iv.setImageURI(Uri.parse(str+"t.jpg"));
					}else{
						iv.setImageResource(R.drawable.nothumbnailimage);
					}
					
					iv.setLayoutParams(new LayoutParams(512, 512));
					iv.setX(tmpX);
					iv.setY(tmpY);
					
					
					rlImage.addView(iv);
					
				}
				
				rlBase.addView(rlImage);
				
				
				RelativeLayout rlButton = new RelativeLayout(this);
//				rlButton.setLayoutParams(new LayoutParams((int)(Math.ceil(shopdata.getDataCount(dataCategory)/3)), 1600));
				rlButton.setLayoutParams(new LayoutParams(scrlWidth, 1600));
				
				
				for(int i = 0; i < shopdata.getDataCount(dataCategory); i++){
					
					int tmpX = 16 + (int)(( i / 3 ) * 528 );
					int tmpY = 16 + (( i % 3 ) * 528);
					
					Button btn = new Button(this);
					btn.setLayoutParams(new LayoutParams(512, 512));
					btn.setBackgroundResource(R.drawable.btn_thumb_list);
					btn.setOnClickListener(ol);
					btn.setX(tmpX);
					btn.setY(tmpY);
					btn.setTag(i);
					
					rlButton.addView(btn);
					
				}
				
				rlBase.addView(rlButton);
				
				
				break;
			
			case ShopData.DATA_RECOMMAND:	
			case ShopData.DATA_ARTIST:
			case ShopData.DATA_LIVING:
			case ShopData.DATA_STATIONARY:
			case ShopData.DATA_CRAFTS:
			case ShopData.DATA_PRINTS:
				
				RelativeLayout rlImageN = new RelativeLayout(this);
				rlImageN.setLayoutParams(new LayoutParams(scrlWidth, 1600));
//				rlImageN.setLayoutParams(new LayoutParams((int)(Math.ceil(shopdata.getDataCount(dataCategory)/3)), 1600));
				
				for(int i = 1; i < shopdata.getDataCount(dataCategory); i++){
					
					int tmpX = 16 + (int)(( (i-1) / 3 ) * 528 );
					int tmpY = 16 + (( (i-1) % 3 ) * 528);
					
					
					
					String str = shopdata.getDataPath(dataCategory, i);
					ImageView iv = new ImageView(this);
					iv.setImageURI(Uri.parse(str+"t.jpg"));
					iv.setLayoutParams(new LayoutParams(512, 512));
					iv.setX(tmpX);
					iv.setY(tmpY);
					
					
					rlImageN.addView(iv);
					
  				}
				
				rlBase.addView(rlImageN);
				
				
				RelativeLayout rlButtonN = new RelativeLayout(this);
				rlButtonN.setLayoutParams(new LayoutParams(scrlWidth, 1600));
//				rlButtonN.setLayoutParams(new LayoutParams((int)(Math.ceil(shopdata.getDataCount(dataCategory)/3)), 1600));
				
				for(int i = 0; i < shopdata.getDataCount(dataCategory); i++){
					
					int tmpX = 16 + (int)(( (i-1) / 3 ) * 528 );
					int tmpY = 16 + (( (i-1) % 3 ) * 528);
					
					Button btn = new Button(this);
					btn.setLayoutParams(new LayoutParams(512, 512));
					btn.setBackgroundResource(R.drawable.btn_thumb_list);
					btn.setOnClickListener(ol);
					btn.setX(tmpX);
					btn.setY(tmpY);
					btn.setTag(i);
					
					rlButtonN.addView(btn);
					
				}
				
				rlBase.addView(rlButtonN);
				
				
				break;
				
		}
		
	}
	
	
	
	
}
