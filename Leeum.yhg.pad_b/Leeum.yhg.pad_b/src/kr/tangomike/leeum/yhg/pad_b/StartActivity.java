package kr.tangomike.leeum.yhg.pad_b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class StartActivity extends Activity {
	@Override
	protected void onCreate(Bundle sis){
		super.onCreate(sis);
		
		RelativeLayout rl = new RelativeLayout(this);
		rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		setContentView(rl);
		
		rl.setBackgroundResource(R.drawable.b_cover);
		
		rl.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(StartActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				return false;
			}
			
		});
		
	}
	

}
