package com.bshlab.alumlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class InfoActivity extends Activity {
	
	private int menuStatus = 0;
	
	private static final int MENU_HOME = 0;
	private static final int MENU_101 = 101;
	private static final int MENU_102 = 102;
	private static final int MENU_103 = 103;
	private static final int MENU_201 = 201;
	private static final int MENU_202 = 202;
	private static final int MENU_203 = 203;
	private static final int MENU_301 = 301;
	private static final int MENU_401 = 401;
	
	private boolean isSubMenuOn = false;
	
	private Button btnSubMenu;
	
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
		setContentView(R.layout.layout_info);
		menuStatus = MENU_HOME;
		isSubMenuOn = false;
		
		/*
		 * menu button setup
		 */
		
		Button btnList = (Button)findViewById(R.id.btn_list);
		btnList.setOnClickListener(menuListener);
		
		Button btnBookmark = (Button)findViewById(R.id.btn_bookmark);
		btnBookmark.setOnClickListener(menuListener);
		
		Button btnSettings = (Button)findViewById(R.id.btn_settings);
		btnSettings.setOnClickListener(menuListener);
		
		
		
		btnSubMenu = (Button)findViewById(R.id.btn_sub_menu);
		btnSubMenu.setVisibility(View.INVISIBLE);
		btnSubMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(menuStatus == MENU_HOME || menuStatus == MENU_301 || menuStatus == MENU_401) return;
				
				if(isSubMenuOn){
					hideSubMenu();
					isSubMenuOn = false;
				}else{
					showSubMenu(menuStatus);
					isSubMenuOn = true;
					
				}
				
				
				
			}
		});
		
		Button btnInfo01 = (Button)findViewById(R.id.btn_info_01);
		Button btnInfo02 = (Button)findViewById(R.id.btn_info_02);
		Button btnInfo03 = (Button)findViewById(R.id.btn_info_03);
		Button btnInfo04 = (Button)findViewById(R.id.btn_info_04);
		
		btnInfo01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showInfo(MENU_101);
				btnSubMenu.setVisibility(View.VISIBLE);
				
			}
		});
		
		btnInfo02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showInfo(MENU_201);
				btnSubMenu.setVisibility(View.VISIBLE);
			}
		});
		
		btnInfo03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showInfo(MENU_301);
				btnSubMenu.setVisibility(View.INVISIBLE);
				
			}
		});
		
		btnInfo04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showInfo(MENU_401);
				btnSubMenu.setVisibility(View.INVISIBLE);
			}
		});
			
	}
	
	/* 
	 * Show Information Screen
	 */
	
	private void showInfo(int screenNo){
		
		// create ScrollView and add Content Layout 
		
		RelativeLayout rlContent = (RelativeLayout)findViewById(R.id.rl_content);
		ScrollView scrl;
		if(menuStatus == MENU_HOME){
			scrl = new ScrollView(this);
			scrl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			scrl.setBackgroundColor(Color.WHITE);
			rlContent.addView(scrl);
		}else{
			scrl = (ScrollView)rlContent.getChildAt(rlContent.getChildCount() - 1);
			scrl.removeViews(0, scrl.getChildCount());
		}
		
		float scale = getResources().getDisplayMetrics().density;
		int dpAsPixels = (int) (25 * scale + 0.5f);
		
		LinearLayout llContent = new LinearLayout(this);
		llContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		llContent.setOrientation(LinearLayout.VERTICAL);
		llContent.setGravity(0x11);
		llContent.setPadding(0, dpAsPixels, 0, dpAsPixels);
		
		
		int i = 1;
        while(true){
        	
        	
        	
        	if(getResources().getIdentifier("img_info_" + screenNo + "_0" + i, "drawable", getPackageName()) != 0){
        		ImageView iv = new ImageView(getApplicationContext());
        		iv.setImageResource(getResources().getIdentifier("img_info_" + screenNo + "_0" + i, "drawable", getPackageName()));
        		llContent.addView(iv);
        		
        		android.util.Log.i("content", "img_info_" + screenNo + "_0" + i);
        		
        		i++;
        	}else{
        		break;
        	}
        	
        	
        }
        
        menuStatus = screenNo;
		

		
		
		// recalculate scroll view and scroll to 0
		scrl.addView(llContent);
		scrl.scrollTo(0, 0);
		scrl.requestLayout();
		
		if(isSubMenuOn){
			hideSubMenu();
			isSubMenuOn = false;
		}
		
		
	}
	
	
	/*
	 * Show Submenu
	 */
	
	
	private void showSubMenu(final int menuNo){
		
		LinearLayout llsub = (LinearLayout)findViewById(R.id.ll_sub_menu);
		
		
		
		switch(menuNo){
		case MENU_101:
			ImageView iv = new ImageView(this);
			iv.setBackgroundResource(R.drawable.btn_info_sub_101_pressed);
			iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llsub.addView(iv);
			
			Button btn01 = new Button(this);
			btn01.setBackgroundResource(R.drawable.btn_info_sub_102);
			btn01.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn01.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_102);
					
				}
			});
			
			llsub.addView(btn01);
			
			Button btn02 = new Button(this);
			btn02.setBackgroundResource(R.drawable.btn_info_sub_103);
			btn02.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn02.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_103);
					
				}
			});
			
			llsub.addView(btn02);
			
			
			
			break;
			
		case MENU_102:
			
			
			Button btn03 = new Button(this);
			btn03.setBackgroundResource(R.drawable.btn_info_sub_101);
			btn03.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn03.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_101);
					
				}
			});
			
			llsub.addView(btn03);
			
			
			ImageView iv2 = new ImageView(this);
			iv2.setBackgroundResource(R.drawable.btn_info_sub_102_pressed);
			iv2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llsub.addView(iv2);
			
			Button btn04 = new Button(this);
			btn04.setBackgroundResource(R.drawable.btn_info_sub_103);
			btn04.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn04.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_103);
					
				}
			});
			
			llsub.addView(btn04);
			
			
			
			break;
			
			
		case MENU_103:

			Button btn05 = new Button(this);
			btn05.setBackgroundResource(R.drawable.btn_info_sub_101);
			btn05.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn05.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_101);
					
				}
			});
			
			llsub.addView(btn05);
			
			
			Button btn06 = new Button(this);
			btn06.setBackgroundResource(R.drawable.btn_info_sub_102);
			btn06.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn06.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_102);
					
				}
			});
			
			llsub.addView(btn06);
			
			ImageView iv3 = new ImageView(this);
			iv3.setBackgroundResource(R.drawable.btn_info_sub_103_pressed);
			iv3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llsub.addView(iv3);
			
			
			break;
			
			
			
			
			
			
		case MENU_201:
			ImageView iv4 = new ImageView(this);
			iv4.setBackgroundResource(R.drawable.btn_info_sub_201_pressed);
			iv4.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llsub.addView(iv4);
			
			Button btn07 = new Button(this);
			btn07.setBackgroundResource(R.drawable.btn_info_sub_202);
			btn07.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn07.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_202);
					
				}
			});
			
			llsub.addView(btn07);
			
			Button btn08 = new Button(this);
			btn08.setBackgroundResource(R.drawable.btn_info_sub_203);
			btn08.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn08.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_203);
					
				}
			});
			
			llsub.addView(btn08);
			
			break;
			
		case MENU_202:
			
			Button btn09 = new Button(this);
			btn09.setBackgroundResource(R.drawable.btn_info_sub_201);
			btn09.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn09.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_201);
					
				}
			});
			
			llsub.addView(btn09);
			
			
			ImageView iv5 = new ImageView(this);
			iv5.setBackgroundResource(R.drawable.btn_info_sub_102_pressed);
			iv5.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llsub.addView(iv5);
			
			Button btn10 = new Button(this);
			btn10.setBackgroundResource(R.drawable.btn_info_sub_203);
			btn10.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn10.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_203);
					
				}
			});
			
			llsub.addView(btn10);
			
			
			
			break;
			
			
			
			
		case MENU_203:
			Button btn11 = new Button(this);
			btn11.setBackgroundResource(R.drawable.btn_info_sub_201);
			btn11.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn11.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_203);
					
				}
			});
			
			llsub.addView(btn11);
			
			
			Button btn12 = new Button(this);
			btn12.setBackgroundResource(R.drawable.btn_info_sub_202);
			btn12.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btn12.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					showInfo(MENU_202);
					
				}
			});
			
			llsub.addView(btn12);
			
			ImageView iv6 = new ImageView(this);
			iv6.setBackgroundResource(R.drawable.btn_info_sub_203_pressed);
			iv6.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llsub.addView(iv6);
			
			
			break;
			
		}
		

	}
	
	
	/*
	 * Hide SubMenu
	 */
	
	private void hideSubMenu(){
		LinearLayout llsub = (LinearLayout)findViewById(R.id.ll_sub_menu);
		llsub.removeAllViews();
		
	}
	
	/*
	 * Button Listener for menu buttons
	 */
	private OnClickListener menuListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			
				case R.id.btn_info:
					Intent intent = new Intent(InfoActivity.this, InfoActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "info");
					break;
			
				case R.id.btn_bookmark:
					Intent intent2 = new Intent(InfoActivity.this, BookmarkActivity.class);
					startActivity(intent2);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "bookmark");
					break;
					
				case R.id.btn_list:
					Intent intent3 = new Intent(InfoActivity.this, ListActivity.class);
					startActivity(intent3);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "list");
					break;
					
				case R.id.btn_settings:
					Intent intent4 = new Intent(InfoActivity.this, SettingsActivity.class);
					startActivity(intent4);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
					android.util.Log.i("shimaz", "settings");
					break;
				
				default:
					break;
					
			
			}
			
		}
	};
	

	@Override
	public void onBackPressed(){
	
		if(menuStatus == MENU_HOME){
			super.onBackPressed();
		}else{
			if(isSubMenuOn){
				hideSubMenu();
				isSubMenuOn = false;
				btnSubMenu.setVisibility(View.INVISIBLE);
			}
			RelativeLayout rlContent = (RelativeLayout)findViewById(R.id.rl_content);
			rlContent.removeViewAt(rlContent.getChildCount() - 1);
			menuStatus = MENU_HOME;
		}
		
	
	}
}
