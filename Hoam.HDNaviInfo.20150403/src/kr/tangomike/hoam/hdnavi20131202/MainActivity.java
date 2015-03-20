/*
 TUIOdroid http://www.tuio.org/
 An Open Source TUIO Tracker for Android
 (c) 2011 by Tobias Schwirten and Martin Kaltenbrunner
 
 TUIOdroid is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 TUIOdroid is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with TUIOdroid.  If not, see <http://www.gnu.org/licenses/>.
*/

package kr.tangomike.hoam.hdnavi20131202;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
//import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import android.hardware.*;
import java.net.*;



import android.widget.FrameLayout;
//import android.widget.ImageView;

/**
 * Main Activity
 * @author Tobias Schwirten
 * @author Martin Kaltenbrunner
 */
@SuppressLint("ClickableViewAccessibility")
public class MainActivity extends Activity {
  
	/**
	 * View that shows the Touch points etc
	 */
	private TouchView touchView;
	
	/**
	 * Request Code for the Settings activity to define 
	 * which child activity calls back
	 */
	private static final int REQUEST_CODE_SETTINGS = 0;
	
	/**
	 * IP Address for OSC connection
	 */
	private String oscIP;
	
	/**
	 * Port for OSC connection
	 */
	private int oscPort;
	
	/**
	 * Adjusts the Touch View
	 */
	private boolean drawAdditionalInfo;
	
	/**
	 * Adjusts the TUIO verbosity
	 */
	private boolean sendPeriodicUpdates;
	
	/**
	 * Adjusts the Touch View
	 */
	private int screenOrientation;
	
	/**
	 * Detects shaking gesture
	 */	
	private SensorManager sensorManager;
	
	private boolean showSettings = false;
	 
	/**
	 *  Called when the activity is first created. 
	 */
	
	
	
	private ImageView iv01;
	private ImageView iv02;
	private ImageView iv03;
	private ImageView iv04;
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        /* load preferences */
        SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
      
        /* get Values */
        oscIP = settings.getString("myIP", "192.168.0.58");
        oscPort = settings.getInt("myPort", 3333);
        drawAdditionalInfo = settings.getBoolean("ExtraInfo", false);
        sendPeriodicUpdates = settings.getBoolean("VerboseTUIO", true);
        screenOrientation = settings.getInt ("ScreenOrientation", 0);
        this.adjustScreenOrientation(this.screenOrientation);
        
        touchView  = new TouchView(this,oscIP,oscPort,drawAdditionalInfo,sendPeriodicUpdates);
        
        
        
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams frameLP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        frameLayout.setLayoutParams(frameLP);
        frameLayout.addView(touchView);
        
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
		
        	
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				
				touchView.retriveTouchEvent(arg1);
				iv01.setVisibility(View.INVISIBLE);
				iv02.setVisibility(View.INVISIBLE);
				iv03.setVisibility(View.INVISIBLE);
				iv04.setVisibility(View.INVISIBLE);
				
				return true;
				
			}
		});
        
        
        
        
        
        Button btnInfo01 = new Button(this);
        btnInfo01.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnInfo01.setText("Button 01");
        btnInfo01.setX(700);
        btnInfo01.setY(0);
        btnInfo01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				iv01.setVisibility(View.VISIBLE);
				iv02.setVisibility(View.INVISIBLE);
				iv03.setVisibility(View.INVISIBLE);
				iv04.setVisibility(View.INVISIBLE);
				touchView.setNavData(400, 600, 70, 2);
				
			}
		});
        
        
        
        Button btnInfo02 = new Button(this);
        btnInfo02.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnInfo02.setText("Button 02");
        btnInfo02.setX(700);
        btnInfo02.setY(100);
        btnInfo02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				iv01.setVisibility(View.INVISIBLE);
				iv02.setVisibility(View.VISIBLE);
				iv03.setVisibility(View.INVISIBLE);
				iv04.setVisibility(View.INVISIBLE);
				
				touchView.setNavData(400, 600, 100, 3);
				
			}
		});
        
        Button btnInfo03 = new Button(this);
        btnInfo03.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnInfo03.setText("Button 03");
        btnInfo03.setX(700);
        btnInfo03.setY(200);
        btnInfo03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				iv01.setVisibility(View.INVISIBLE);
				iv02.setVisibility(View.INVISIBLE);
				iv03.setVisibility(View.VISIBLE);
				iv04.setVisibility(View.INVISIBLE);
				
				touchView.setNavData(400, 600, 125, 4);
				
			}
		});
        
        Button btnInfo04 = new Button(this);
        btnInfo04.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnInfo04.setText("Button 04");
        btnInfo04.setX(700);
        btnInfo04.setY(300);
        btnInfo04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				iv01.setVisibility(View.INVISIBLE);
				iv02.setVisibility(View.INVISIBLE);
				iv03.setVisibility(View.INVISIBLE);
				iv04.setVisibility(View.VISIBLE);
				touchView.setNavData(400, 600, 150, 5);
			}
		});
        
        
        
        frameLayout.addView(btnInfo01);
        frameLayout.addView(btnInfo02);
        frameLayout.addView(btnInfo03);
        frameLayout.addView(btnInfo04);
        
        
        
        
        iv01 = new ImageView(this);
        iv02 = new ImageView(this);
        iv03 = new ImageView(this);
        iv04 = new ImageView(this);
        
        iv01.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        iv02.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        iv03.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        iv04.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        iv01.setBackgroundResource(R.drawable.img_mask_01);
        iv02.setBackgroundResource(R.drawable.img_mask_02);
        iv03.setBackgroundResource(R.drawable.img_mask_03);
        iv04.setBackgroundResource(R.drawable.img_mask_04);
        
        iv01.setX(100);
        iv01.setY(100);
        iv02.setX(100);
        iv02.setY(200);
        iv03.setX(100);
        iv03.setY(300);
        iv04.setX(100);
        iv04.setY(400);
        
        iv01.setVisibility(View.INVISIBLE);
        iv02.setVisibility(View.INVISIBLE);
        iv03.setVisibility(View.INVISIBLE);
        iv04.setVisibility(View.INVISIBLE);
        
        frameLayout.addView(iv01);
        frameLayout.addView(iv02);
        frameLayout.addView(iv03);
        frameLayout.addView(iv04);
        
        
        
        
        setContentView(frameLayout);
        
        
        sensorManager = (SensorManager) this.getBaseContext().getSystemService(Context.SENSOR_SERVICE);
        

        /*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    }
    

	/**
     *  Called when the options menu is created
     *  Options menu is defined in m.xml 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {   	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.m, menu);
    	return true;
    }

    
    /**
     * Called when the user selects an Item in the Menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
    	// Handle item selection
        switch (item.getItemId()) {
	        case R.id.settings:
	            this.openSettingsActivity();
	            return true;
	 
	        case R.id.help:
	        	this.openHelpActivity();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    


    
	/**
	 * Opens the Activity that provides the Settings
	 */
    private void openSettingsActivity (){
    	Intent myIntent = new Intent();
    	myIntent.setClassName("kr.tangomike.hoam.hdnavi20131202", "kr.tangomike.hoam.hdnavi20131202.SettingsActivity"); 
    	myIntent.putExtra("IP_in", oscIP);
    	myIntent.putExtra("Port_in", oscPort);
    	myIntent.putExtra("ExtraInfo", this.drawAdditionalInfo);
       	myIntent.putExtra("VerboseTUIO", this.sendPeriodicUpdates);
      	myIntent.putExtra("ScreenOrientation", this.screenOrientation);
      	showSettings = true;
    	startActivityForResult(myIntent, REQUEST_CODE_SETTINGS);
    }
    
    
    /**
	 * Opens the Activity that Help information
	 */
    private void openHelpActivity (){
    	Intent myIntent = new Intent();
     	myIntent.setClassName("kr.tangomike.hoam.hdnavi20131202", "kr.tangomike.hoam.hdnavi20131202.HelpActivity");
     	showSettings = true;
     	startActivity(myIntent);  
    }
    
    

    /**
     * Listens for results of new child activities. 
     * Different child activities are identified by their requestCode
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
       
    	 // See which child activity is calling us back.
    	if(requestCode == REQUEST_CODE_SETTINGS){
        	
        	switch (resultCode){
        	
        		case RESULT_OK:
        			Bundle dataBundle = data.getExtras(); 
        		            			
        	    	String ip = dataBundle.getString("IP");
        	    	
        	    	try { InetAddress.getByName(ip); } 
        	    	catch (Exception e) {
        	    		Toast.makeText(this, "Invalid host name or IP address!", Toast.LENGTH_LONG).show();
        			}
        	    	
        	    	int port = 3333;
        	    	try { port = Integer.parseInt(dataBundle.getString("Port")); }
        	    	catch (Exception e) { port = 0; }
        	    	if (port<1024) Toast.makeText(this, "Invalid UDP port number!", Toast.LENGTH_LONG).show();
        	    		
        	    	this.oscIP = ip;
            	    this.oscPort = port;        	
            	    this.drawAdditionalInfo = dataBundle.getBoolean("ExtraInfo");
            	    this.sendPeriodicUpdates = dataBundle.getBoolean("VerboseTUIO");
            	    	
            	    this.touchView.setNewOSCConnection(oscIP, oscPort);
            	    this.touchView.drawAdditionalInfo = this.drawAdditionalInfo;
            	    this.touchView.sendPeriodicUpdates = this.sendPeriodicUpdates;
            	    	
            	    /* Change behavior of screen rotation */
            	    this.screenOrientation  = dataBundle.getInt("ScreenOrientation");
            	    this.adjustScreenOrientation(this.screenOrientation);
            	    	
        	    	/* Get preferences, edit and commit */
            	    SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
            	    SharedPreferences.Editor editor = settings.edit();
            	    
            	    /* define Key/Value */
            	    editor.putString("myIP", this.oscIP);
            	    editor.putInt("myPort", this.oscPort);
            	    editor.putBoolean("ExtraInfo",this.drawAdditionalInfo);
            	    editor.putBoolean("VerboseTUIO",this.sendPeriodicUpdates);
            	    editor.putInt("ScreenOrientation",this.screenOrientation);
            	    
            	    /* save Settings*/
            	    editor.commit();            	    	        			
         	    	
        	    	break;
        	    
        	    
        	    default:
        	    	// Do nothing
        		
        	}
    	}
    }

    /**
     * Adjusts the screen orientation
     */
    private void adjustScreenOrientation (int screenOrientation){
    	
    	switch(screenOrientation){
    	
    		case 0: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
    			
    		case 1: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
				
    		case 2: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
	
    		default: this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		break;
    	}	
    }
    
    protected void onResume() {
      super.onResume();
      sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
      showSettings = false;
    }

    protected void onStop() {
      super.onStop();
      sensorManager.unregisterListener(shakeListener);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeListener);
    }

    private final SensorEventListener shakeListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
          float x = se.values[0];
          float y = se.values[1];
          float z = se.values[2];
          float shake = x*x + y*y + z*z;
           
          if ((!showSettings) && (shake>500)) {
        	  showSettings = true;
        	  openSettingsActivity();
          }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
      };

      


}