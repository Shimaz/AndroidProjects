package com.bshlab.alumlist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CertifyActivity extends Activity {
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		setContentView(R.layout.layout_certify);
		
		SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
		
		int certified = settings.getInt("cert", 0);
		
		if(certified == 0){ // not certified
			
			
		}else{ // already certified
			
		}
		
		
	}

}
