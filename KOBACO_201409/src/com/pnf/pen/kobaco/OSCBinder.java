package com.pnf.pen.kobaco;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;

import tuioDroid.osc.OSCInterface;
import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class OSCBinder extends Application {

	
	public static final int RESULT_TIMER = 0xa0;
	
	
	private OSCInterface oscInterface;
	private String serverIP;
	private int serverPort;
	private boolean isPowered = false;
	
	
	private Handler mHandler;
	@Override 
	public void onCreate(){
		super.onCreate();
		
		 serverIP = getResources().getString(R.string.server_ip);
	     serverPort = Integer.parseInt(getResources().getString(R.string.osc_port));
	        
	     oscInterface = new OSCInterface(serverIP, serverPort);
	     
	     
	     mHandler = new Handler(){
	    	 @Override
	    	 public void handleMessage(Message msg){
	    	
	    		 if(!oscInterface.isReachable()){
	    			 
	    			 oscInterface = null;
	    			 oscInterface = new OSCInterface(serverIP, serverPort);
	    			 
	    		
	    					 
	    		 }
	    		 
	    		 mHandler.sendEmptyMessageDelayed(0, 500);
	    	 }
	     };
	     
	     mHandler.sendEmptyMessage(0);
		
	     
	     
	     Thread thread = new Thread(){
	    	
	    	 @Override
	    	 public void run(){
	    		 
	    		 
	    		 while(true){
		    		 sendOSCData();
		    		 try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		 }
	    		 
	    	 }
	    	 
	     };
	     
	     thread.start();
	     
	     
	}
	
	public void setPowered(boolean bl){
		
		isPowered = bl;
	}
	
	private void sendOSCData() throws ArrayIndexOutOfBoundsException{
		OSCBundle oscBundle = new OSCBundle();
		
		// Navigator Data Message
		Object outputData[] = new Object[3];
		
		
		outputData[0] = 0; // screen number  //(Integer) screenSaver;
		outputData[1] = 0;
		outputData[2] = 1;
		
		oscBundle.addPacket(new OSCMessage("/Void/Leeum", outputData));
		

		if(oscInterface != null && isPowered){
			android.util.Log.i("shimaz", "dummy" + " " + isPowered);
			oscInterface.sendOSCBundle(oscBundle);
		}
		
		
		
		
	}
	
	public void sendOSCData (int imgKind, int sendOK) throws ArrayIndexOutOfBoundsException {
		
		OSCBundle oscBundle = new OSCBundle();
		
		// Navigator Data Message
		Object outputData[] = new Object[3];
		
		
		outputData[0] = imgKind; // screen number  //(Integer) screenSaver;
		outputData[1] = sendOK;
		outputData[2] = 0;
		
		oscBundle.addPacket(new OSCMessage("/Void/Leeum", outputData));
		

		oscInterface.sendOSCBundle(oscBundle);
		
		
		
		if(!oscInterface.isReachable()){
			android.util.Log.i("shimaz", "osc fail");
			
		}else{
			android.util.Log.i("shimaz", "osc sent" + outputData.toString());
		}
		
	}
	
}
