/**
 * 
 * @author: shimaz 
 * 2013.11 all codes and descriptions are written by shimaz @tangomkie for sugimoto leeum exhibition. 2013.
 * 
 */


package kr.tangomike.leeum.yhg.pad_j;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class PageDots extends LinearLayout {

	private int dotPosition;
	private int dotCount;
	private int dotMargin;
	private ArrayList<ImageView> dotArr;
	private Bitmap dotFocused;
	private Bitmap dotUnfocused;
	private Context mContext;
	

	
	public PageDots(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		dotPosition = 0;
		dotCount = 0;
		dotMargin = 10;
		dotFocused = null;
		dotUnfocused = null;
		dotArr = new ArrayList<ImageView>();
	}

	
	
	public PageDots(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		dotPosition = 0;
		dotCount = 0;
		dotMargin = 10;
		dotFocused = null;
		dotUnfocused = null;
		dotArr = new ArrayList<ImageView>();
		
	}
	
	public void setDotCount(int count){
		dotCount = count;
		renewPageDots();
	}
	
	
	public void setDotMargin(int margin){
		dotMargin = margin;
		renewPageDots();
	}
	
	
	public void setFocusedDot(int resourceId){
		dotFocused = BitmapFactory.decodeResource(mContext.getResources(), resourceId);
		renewPageDots();
	}
	
	public void setUnfocusedDot(int resourceId){
		dotUnfocused = BitmapFactory.decodeResource(mContext.getResources(), resourceId);
		renewPageDots();
	}
	
	
	public void setDotPosition(int position){
		
		if(position > dotArr.size()){
			android.util.Log.i("Error", "position oversize");
		}else{
			
			for(int i = 0; i < dotArr.size(); i++){
				if(i == position){
					ImageView iv = (ImageView)dotArr.get(i);
					iv.setImageBitmap(dotFocused);
//					iv.setAlpha(0.4f);
				}else{
					ImageView iv = (ImageView)dotArr.get(i);
					iv.setImageBitmap(dotUnfocused);
//					iv.setAlpha(0.4f);
				}
				
			}
			
		}
		
	}
	
	private void renewPageDots(){
		this.removeAllViews();
		dotArr.clear();
		
		for(int i = 0; i < dotCount; i++){
			LinearLayout layoutMargin = new LinearLayout(mContext);
			layoutMargin.setLayoutParams(new LayoutParams(dotMargin, dotMargin));
			this.addView(layoutMargin);

			ImageView iv = new ImageView(mContext);
			if(i == dotPosition){
				iv.setImageBitmap(dotFocused);
//				iv.setAlpha(0.4f);
			}
			else{
				iv.setImageBitmap(dotUnfocused);
//				iv.setAlpha(0.4f);
			}
			
			if(dotArr == null) android.util.Log.i("Error", "arr is null");
			
			if(iv != null){
				dotArr.add(iv);
				this.addView(iv);
			}
			
			
			
		}
		
		LinearLayout layoutMargin = new LinearLayout(mContext);
		layoutMargin.setLayoutParams(new LayoutParams(dotMargin, dotMargin));
		this.addView(layoutMargin);
		
		
		
	}
	
	

}
