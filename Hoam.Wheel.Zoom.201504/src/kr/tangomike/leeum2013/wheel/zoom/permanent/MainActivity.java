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

package kr.tangomike.leeum2013.wheel.zoom.permanent;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.hardware.*;
import java.net.*;
import java.util.ArrayList;




import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;


/* Main Activity
 * @author Tobias Schwirten
 * @author Martin Kaltenbrunner
 */


@SuppressLint({ "HandlerLeak", "DefaultLocale" })
public class MainActivity extends Activity {
  
	/**
	 * View that shows the Touch points etc
	 */
	public TouchView touchView;
	
	/**
	 * Request Code for the Settings activity to define 
	 * which child activity calls back
	 */
	private static final int REQUEST_CODE_SETTINGS = 0;

//	protected static final ProgressBar m_seekBar = null;
	
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
//	private SensorManager sensorManager;

	 
	private boolean showSettings = false;
	
	
	/**
	 * Timer 
	 * 
	 */
	private Handler mHandler;
	public long tCounter = 0;
	public long screenSaverOnTime = 60;
	private final int exitTime = 180;
	public boolean isTimerStopped = true;


	
	/**
	 * 
	 * seek bar as scroll wheel
	 * 
	 * 
	 */

	private ArrayList<Bitmap> arrWheelUp;
	private ArrayList<Bitmap> arrWheelDown;
	private SeekBar sBar;
	private static final int seekBarLength = 71; 
	private int oldProgress = 0;
	private ImageView ivWheel;
	private int progressCounter = 0;
	private int rotateNumber = 0;
	private static final int SPEED = 1;
	private boolean isDirty = false;
	
	
	/**
	 *  Called when the activity is first created. 
	 */
	public PowerManager.WakeLock wl;
	
    @SuppressLint("DefaultLocale")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* load preferences */
        SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
      
        /* get Values */
        oscIP = settings.getString("myIP", "192.168.0.8");
        oscPort = settings.getInt("myPort", 3333);
        drawAdditionalInfo = settings.getBoolean("ExtraInfo", false);
        sendPeriodicUpdates = settings.getBoolean("VerboseTUIO", true);
        screenOrientation = settings.getInt ("ScreenOrientation", 0);
        this.adjustScreenOrientation(this.screenOrientation);
        
        touchView  = new TouchView(this,oscIP,oscPort,drawAdditionalInfo,sendPeriodicUpdates);
        
        
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams frameLP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//        frameLayout.setBackgroundResource(R.drawable.rot_bg);
        frameLayout.setLayoutParams(frameLP);


        
        
        frameLayout.addView(touchView);
        
        

        
        
//        sensorManager = (SensorManager) this.getBaseContext().getSystemService(Context.SENSOR_SERVICE);
        

        /**
         * 
         * wheel components
         * 
         */
        
        arrWheelUp = new ArrayList<Bitmap>();
        arrWheelDown = new ArrayList<Bitmap>();
        String pkg = getApplicationContext().getPackageName();
        
        for(int i = 0; i < 5; i++){
        	
        	String str = String.format("%02d", i+1);
			int irUp = getResources().getIdentifier("wheel_" + str + "_up", "raw", pkg);
			int irDown = getResources().getIdentifier("wheel_" + str + "_down", "raw", pkg);
			Bitmap tmpUp;
			Bitmap tmpDown;
			tmpUp = BitmapFactory.decodeResource(getResources(), irUp);
			tmpDown = BitmapFactory.decodeResource(getResources(), irDown);
			
			arrWheelUp.add(tmpUp);
			arrWheelDown.add(tmpDown);
        	
        	
        }
        
        ivWheel = new ImageView(this);
        ivWheel.setImageBitmap(arrWheelUp.get(0));
        ivWheel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ivWheel.setX(131);
        ivWheel.setY(1075);
        
        frameLayout.addView(ivWheel);
        
        sBar = new SeekBar(this);
        sBar.setLayoutParams(new LayoutParams(538, 130));
        sBar.setX(131);
        sBar.setY(1045);
        sBar.setMax(seekBarLength);
        sBar.setProgress(0);
        sBar.setAlpha(0);
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int tmp = seekBar.getProgress();
				ivWheel.setImageBitmap(arrWheelUp.get(tmp%5));
				tCounter = 0;
				touchView.isSeekBarTouched = 0;
				
				isDirty = false;
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int tmp = seekBar.getProgress();
				ivWheel.setImageBitmap(arrWheelDown.get(tmp%5));
				isTimerStopped = false;
				tCounter = 0;
				touchView.isSeekBarTouched = 1;
				if(touchView.screenSaver == 0){
		    		touchView.screenSaver = 1;
					mHandler.sendEmptyMessageDelayed(0, 1000);
		    	}
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ivWheel.setImageBitmap(arrWheelDown.get(progress%5));
				
				if(progress - oldProgress < 0){
					progressCounter++;
					if(progressCounter % SPEED == 0){
						rotateNumber = (progressCounter / SPEED) % (seekBarLength +1);
					}
//					android.util.Log.i("progress", "left " + progressCounter + " " + Math.abs(rotateNumber));
					
					
				}else{
					progressCounter--;
					if(progressCounter < 0) progressCounter = seekBarLength * SPEED;
					if(progressCounter % SPEED == 0){
						rotateNumber = (progressCounter / SPEED) % (seekBarLength +1);
					}
//					android.util.Log.i("progress", "right " + progressCounter + " " + Math.abs(rotateNumber));
				}
				
				if(isDirty){
					touchView.imageNumber = Math.abs(rotateNumber)+1;
					touchView.setBGImg();
					
				}
				isDirty = true;
				
				android.util.Log.i("progress" , ""+progress);
				
				oldProgress = seekBar.getProgress();
			}
		});
        
        
        
        
        frameLayout.addView(sBar);
        
        
        Button btnBack = new Button(this);
        btnBack.setText(" settings ");
        btnBack.setX(10);
        btnBack.setY(10);
        btnBack.setTextSize(20.0f);
        btnBack.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnBack.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				touchView.screenSaver = 0;
				openSettingsActivity();
				
			}
		});
        
        frameLayout.addView(btnBack);
        
        setContentView(frameLayout);

        
        
        /*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	    wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
	    
	    
	    isTimerStopped = true;
		mHandler = new Handler() {
        	public void handleMessage(Message msg){
        		tCounter++;
        		


        			
        		if(tCounter <= screenSaverOnTime){
        			
            		mHandler.sendEmptyMessageDelayed(0, 1000);
            		android.util.Log.i("counter", " "+ tCounter);
            		
            	
        		}else if(tCounter > screenSaverOnTime && tCounter < exitTime){
        			
        			isTimerStopped = true;
        			tCounter = 0;
        			progressCounter = 0;
        			oldProgress = 0;
        			touchView.imageNumber = 1;
        			touchView.resetToDefault();
            		
        		}
        		
        		else{
        			android.util.Log.i("count", "exit");
        		}
        		
        		
        	}
        };
	    
    }
    
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	isTimerStopped = false;
    	
    	if(touchView.screenSaver == 0){
    		
    		mHandler.sendEmptyMessageDelayed(0, 1000);
    	}
    	tCounter = 0;
    	touchView.screenSaver = 1;
    	
    	
    	
    	touchView.retreiveTouchEvent(event);
    	
    	
    	return super.onTouchEvent(event);
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
    	myIntent.setClassName("kr.tangomike.leeum2013.wheel.zoom.permanent", "kr.tangomike.leeum2013.wheel.zoom.permanent.SettingsActivity"); 
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
     	myIntent.setClassName("kr.tangomike.leeum2013.wheel.zoom.permanent", "kr.tangomike.leeum2013.wheel.zoom.permanent.HelpActivity");
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
//      sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
      showSettings = false;
    }

    protected void onStop() {
      super.onStop();
//      sensorManager.unregisterListener(shakeListener);
    }

    protected void onPause() {
        super.onPause();
//        sensorManager.unregisterListener(shakeListener);
    }

//    private final SensorEventListener shakeListener = new SensorEventListener() {
//
//        public void onSensorChanged(SensorEvent se) {
//          float x = se.values[0];
//          float y = se.values[1];
//          float z = se.values[2];
//          float shake = x*x + y*y + z*z;
//           
//          if ((!showSettings) && (shake>500)) {
//        	  //android.util.Log.v("Accelerometer",""+shake);
//        	  showSettings = true;
//        	  openSettingsActivity();
//          }
//        }
//
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        }
//      };

      
      @Override 
      public void onDestroy(){
    	  
    	  mHandler.removeMessages(0);
    	  tCounter = exitTime;
    	  
    	  super.onDestroy();
    	  
      }

}