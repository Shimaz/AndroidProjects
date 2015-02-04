package kr.tangomike.leeum.yhg.pad_h;

import android.app.Activity;
import android.os.Bundle;
import android.widget.VideoView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		VideoView vv = (VideoView)findViewById(R.id.vv_main);
//		vv.setVideoPath();
		
		
	}
}
