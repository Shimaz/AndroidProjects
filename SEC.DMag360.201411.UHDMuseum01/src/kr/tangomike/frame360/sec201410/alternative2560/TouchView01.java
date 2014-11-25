/*
 TUIOdroid http://www.tuio.org/
 An Open Source TUIO Tracker for Android
 (c) 2011 by Tobias Schwirten and MArtin Kaltenbrunner 
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

package kr.tangomike.frame360.sec201410.alternative2560;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import kr.tangomike.frame360.sec201410.alternative2560.TuioPoint;


import tuioDroid.osc.OSCInterface;
//import tuioDroid.impl.R;


import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.*;
import android.view.*;

/**
 * Main View
 */




@SuppressLint({ "HandlerLeek", "ViewConstructor", "DefaultLocale" })
public class TouchView01 extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final int MAX_TOUCHPOINTS = 2;
	private static final int FRAME_RATE = 60;
	private Paint textPaint = new Paint();
	private Paint dataPaint = new Paint();
	private Paint bgPaint = new Paint();
	
	
	private ArrayList<TuioPoint> tuioPoints;
	
	private int height;
	private float scale = 1.0f;
	
	private OSCInterface oscInterface ;
	
	
	
	public boolean drawAdditionalInfo;
	public boolean sendPeriodicUpdates;


	
	private String sourceName;
	
	private long startTime;
	private long lastTime = 0;

	private Bitmap backgroundImage;
	private Bitmap bgImg;
	
	
	private ArrayList<Bitmap> bgArr;

	private boolean running = false;
	
	
	/* Navigator */
	
	private final static int MAGNIFY = 2;

	
	private Paint navPaint = new Paint();
	private Paint navShadowPaint = new Paint();

	
	private static final float maxNavScale = 578.0f / 2 * MAGNIFY;
	
	
	private static final int defaultNavScale = 153 * MAGNIFY; 
	private static final int defaultNavX = 800 / 2 * MAGNIFY;
	private static final int defaultNavY = 1096 / 2 * MAGNIFY;
	private static final float minNavScale = 77.5f * MAGNIFY;

	
	
	
	
	private float navX = defaultNavX;
	private float navY = defaultNavY;
	private float navScale = defaultNavScale;
	
	private float xDif = 0;
	private float yDif = 0;

	private double dDifNow = 1.0f;
	private double dDifOld = 300.0f;
	
	
	
	
	private boolean insideNav = false;
	
	public int screenSaver = 0;
	
	private static int navMargin = 100 / 2 * MAGNIFY;
	
	
	private int mode = 0;
	
	public int imageNumber = 1;
	public int isSeekBarTouched = 0;
	
	private Bitmap arrowUp, arrowDown, arrowLeft, arrowRight, uiHand;
	////////////////////
	
	// Boundary Limit
	private static final int boundaryLeft = 222 / 2 * MAGNIFY;
	private static final int boundaryTop = 68 / 2 * MAGNIFY;
	private static final int boundaryRight = 1378 / 2 * MAGNIFY;
	private static final int boundaryBottom = 2124 / 2 * MAGNIFY;
	
	private static final int centerMargin = 80 / 2 * MAGNIFY;
	
	
	private int bgX = 211 * MAGNIFY;
	private int bgY = 274 * MAGNIFY;
	
	
	
	

	public int screenNumber = 1;
	
	
	
 
	public String PACKAGE_NAME;

	
	/**
	 * Constructor
	 * @param context
	 * @param devIP
	 * @param oscIP
	 * @param oscPort
	 */
	@SuppressLint("HandlerLeak")
	public TouchView01(Context context, String oscIP, int oscPort, boolean drawAdditionalInfo, boolean sendPeriodicUpdates) {
		super(context, null);
		oscInterface  = new OSCInterface(oscIP , oscPort);
		this.drawAdditionalInfo = drawAdditionalInfo;
		this.sendPeriodicUpdates = sendPeriodicUpdates;
		startTime = System.currentTimeMillis();
		
		tuioPoints = new ArrayList<TuioPoint>();
		sourceName = "TUIOdroid@"+getLocalIpAddress();
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		setFocusable(true); // make sure we get key events
		setFocusableInTouchMode(true); // make sure we get touch events

		textPaint.setColor(Color.WHITE);
		textPaint.setAlpha(255);
		
		
		
		navPaint.setColor(Color.rgb(0, 191, 174));
		navPaint.setStrokeWidth(10);
		navPaint.setStyle(Style.STROKE);
		navPaint.setAlpha(255);
		navPaint.setAntiAlias(true);

		
		navShadowPaint.setColor(Color.DKGRAY);
		navShadowPaint.setStrokeWidth(10);
		navShadowPaint.setStyle(Style.STROKE);
		navShadowPaint.setAlpha(64);
		navShadowPaint.setAntiAlias(true);
		 
		
		bgPaint.setColor(Color.rgb(0, 0, 0));
		bgPaint.setStyle(Style.FILL);
		
		
		PACKAGE_NAME = context.getPackageName();
		bgArr = new ArrayList<Bitmap>();
		
		for (int i = 0; i < 72; i++){
			
			String str = String.format("%d", i+1);
			int ir = getResources().getIdentifier("img1_" + str, "raw", PACKAGE_NAME);
			Bitmap tmpBmp;
			tmpBmp = BitmapFactory.decodeResource(getResources(), ir);
			
			bgArr.add(tmpBmp);
			
		}
		
		backgroundImage = bgArr.get(0);
		bgImg = BitmapFactory.decodeResource(getResources(), R.drawable.rot_bg);
        
		
		arrowUp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_nav_arrow_up);
		arrowDown = BitmapFactory.decodeResource(getResources(), R.drawable.ui_nav_arrow_down);
		arrowLeft = BitmapFactory.decodeResource(getResources(), R.drawable.ui_nav_arrow_left);
		arrowRight = BitmapFactory.decodeResource(getResources(), R.drawable.ui_nav_arrow_right);
		uiHand = BitmapFactory.decodeResource(getResources(), R.drawable.ui_nav_hand);
		

        
		
        
//        mHandler.sendEmptyMessage(0);
        
//        width = this.getWidth();
//        height = this.getHeight();
        
        
	}
	
	/**
	 * Is called if a touch events occurs
	 */
	

	public boolean retreiveTouchEvent(MotionEvent event){
		long timeStamp = System.currentTimeMillis() - startTime;
		long dt = timeStamp - lastTime;
		lastTime = timeStamp;
		
		//always send on ACTION_DOWN & ACTION_UP
		if ((event.getAction() == MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_UP)) dt = 1000;

		int pointerCount = event.getPointerCount();
		//android.util.Log.v("PointerCount",""+pointerCount);
		
		if (pointerCount > MAX_TOUCHPOINTS) {
			pointerCount = MAX_TOUCHPOINTS;
		}
		
		
		
		
		Canvas c = getHolder().lockCanvas();
		
		
		
		if(screenSaver == 0){
			
			
		}
		screenSaver = 1;
		
//		android.util.Log.i("touch", "x: " + event.getX(0) + "   y: " + event.getY(0));
		
		
		
		if (c != null) {
			c.drawBitmap(bgImg, 0, 0, null);
			c.drawRect(boundaryLeft, boundaryTop, boundaryRight, boundaryBottom, bgPaint);
			c.drawBitmap(backgroundImage,bgX,bgY,null);
			
			


			switch (event.getAction() & MotionEvent.ACTION_MASK) {
	        case MotionEvent.ACTION_DOWN:
	           mode = 1; // DRAG
	           
	           if(event.getX(0) > navX - navScale - navMargin
						&& event.getX(0) < navX + navScale + navMargin 
						&& event.getY(0) > navY - (navScale/9*16) - navMargin
						&& event.getY(0) < navY + (navScale/9*16) + navMargin){
					xDif = navX - event.getX(0);
					yDif = navY - event.getY(0);
//					difShow = 1;
					
					
					insideNav = true;
				}else 
				{
					insideNav = false;
				}
				
	           
	           break;
	        case MotionEvent.ACTION_POINTER_DOWN:
					
        	   if(event.getX(0) > navX - navScale - navMargin
						&& event.getX(0) < navX + navScale + navMargin 
						&& event.getY(0) > navY - (navScale/9*16) - navMargin
						&& event.getY(0) < navY + (navScale/9*16) + navMargin
						&& event.getX(1) > navX - navScale - navMargin
						&& event.getX(1) < navX + navScale + navMargin
						&& event.getY(1) > navY - (navScale/9*16) - navMargin
						&& event.getY(1) < navY + (navScale/9*16) + navMargin){
											
						xDif = navX - event.getX(0);
						yDif = navY - event.getY(0);
						
//						difShow = 262;
						
						
						insideNav = true;
					}else 
					{
						insideNav = false;
					}
	        	   dDifOld = Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1)) + (event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));
	        	   mode = 2;
	        	   
	           break;
	        case MotionEvent.ACTION_UP:

	        case MotionEvent.ACTION_POINTER_UP:
	           mode = 0; // NONE
	           break;
	           
	           
	        case MotionEvent.ACTION_MOVE:
	           if (mode == 1  /*Drag*/) {
	           
					if(insideNav){
						dataPaint.setColor(0xff40a7ff);
						dataPaint.setTextSize(25);
						navX = xDif + event.getX();
						
						navY = yDif + event.getY();
						
						

						
					}else
					{
						dataPaint.setColor(0xffff3a7b);
						dataPaint.setTextSize(25);
					}
	        	   
	        	   
	           }else if (mode == 2 /*Zoom*/) {
					if(insideNav){
						navX = (event.getX(0)+event.getX(1))/2;
						navY = (event.getY(0)+event.getY(1))/2;
							
						dDifNow = Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1)) + (event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));

						navScale = navScale * ((float)dDifNow/(float)dDifOld);

							
						dDifOld = dDifNow;

						
						
						dataPaint.setColor(0xff40a7ff);
						dataPaint.setTextSize(25);

					}else
					{
						dataPaint.setColor(0xffff3a7b);
						dataPaint.setTextSize(25);
					}
					
	           break;
	           }
			}

			
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////
			///////////////////////////////////

			
			//////////////////
			//
			// set navigator data
			//
			//////////////////
			
			
			
			
			


			
			
			
		
			
			
			// calculate navigator data

					
			
			/////////////////////////////////////
			//
			//
			// limit settings
			//
			//
			////////////////////////////////////
			
			// Size Limit
			if (navScale > maxNavScale){
				navScale = maxNavScale;
			}else if (navScale < minNavScale)
			{
				navScale = minNavScale;
			}
			
			
			// Center Boundary limit
			
			if(navX <= bgX + centerMargin){ // left
				navX = bgX + centerMargin;
			}
			if(navX >= (589 * MAGNIFY) - centerMargin){ // right
				navX = (589 * MAGNIFY) - centerMargin;
			}
			if(navY <= bgY + centerMargin){ // top
				navY = bgY + centerMargin;
			}
			if(navY >= (810 * MAGNIFY) - centerMargin){ // bottom
				navY = (810 * MAGNIFY) - centerMargin;
			}
				
			
			// Boundary Limit
			if(navX-navScale <= boundaryLeft)
			{
				navX = boundaryLeft + navScale;
			}
			if(navX + navScale >= boundaryRight)
			{
				navX =  boundaryRight- navScale;	
			}
			if(navY - (navScale/9*16) <= boundaryTop)
			{
				navY  = (navScale/9*16) + boundaryTop;
			}
			if(navY + (navScale/9*16) >= boundaryBottom)
			{
				navY = boundaryBottom - (navScale/9*16);
			}
				
			
						
			if(navScale > maxNavScale - 1 || navScale < minNavScale + 1){
				//navPaint.setColor(0xffec008c);
				navPaint.setColor(Color.RED);
				
			}else
			{
				navPaint.setColor(Color.rgb(0, 191, 174));
			}
			
			
			
			
			//////////////////////////////////////
			//
			// Draw Rect
			//
			//////////////////////////////////////
			
			
			// Shadow
			c.drawRect(navX - navScale + (8 / 2 * MAGNIFY), navY - (navScale / 9*16) + (8 / 2 * MAGNIFY), navX + navScale + (8 / 2 * MAGNIFY), navY + (navScale / 9*16) + (8 / 2 * MAGNIFY), navShadowPaint);			
			// Navigator
			c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);
			
			

			
			if ((!oscInterface.isReachable()) || (drawAdditionalInfo)) drawInfo(c);
			getHolder().unlockCanvasAndPost(c);

		}
		
		

		
		
		
		if ((!sendPeriodicUpdates) && (dt>(1000/FRAME_RATE)) ) sendTUIOdata();
		return true;
		
		
		
		
	}
	
	
	public void setBGImg(){
		
//		PACKAGE_NAME = this.getContext().getPackageName();
//		String str = String.format("%02d", imageNumber);
//		int ir = getResources().getIdentifier("r_" + str + "_sec", "raw", PACKAGE_NAME);
		
//		backgroundImage = BitmapFactory.decodeResource(getResources(), ir);
		
		backgroundImage = bgArr.get(imageNumber-1);
		
		
		Canvas c = getHolder().lockCanvas();
		
		if (c != null) {
			c.drawBitmap(bgImg, 0, 0, null);
			c.drawRect(boundaryLeft, boundaryTop, boundaryRight, boundaryBottom, bgPaint);
			c.drawBitmap(backgroundImage,bgX,bgY,null);
			c.drawRect(navX - navScale + (8 / 2 * MAGNIFY), navY - (navScale / 9*16) + (8 / 2 * MAGNIFY), navX + navScale + (8 / 2 * MAGNIFY), navY + (navScale / 9*16) + (8 / 2 * MAGNIFY), navShadowPaint);			
			c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);
			getHolder().unlockCanvasAndPost(c);
		}
		
		
	}

	
	private void drawInfo(Canvas c) {
		
		if (!oscInterface.isReachable()) {
			textPaint.setColor(Color.RED);
			c.drawText("client not reachable", 5, height-2*textPaint.getTextSize()-5,textPaint );
			textPaint.setColor(Color.DKGRAY);
		}
			
		String sourceString = "source: "+sourceName;
		c.drawText(sourceString, 5, height-textPaint.getTextSize()-5,textPaint );
		String clientString = "TUIO/UDP client: "+this.oscInterface.getInetAdress() + ":" + this.oscInterface.getPort();
		c.drawText(clientString, 5, height-5,textPaint );
	}
	
	
	
	
	

	
	
	/**
	 * Sends the TUIO Data
	 * @param blobList
	 */
	public void sendTUIOdata () throws ArrayIndexOutOfBoundsException {
	
		OSCBundle oscBundle = new OSCBundle();
		
		// Navigator Data Message
		Object outputData[] = new Object[6];
		
		outputData[0] = (Float) (navX - bgX - navScale) / MAGNIFY;
		outputData[1] = (Float) (navY - bgY - (navScale / 9*16))  / MAGNIFY;
		outputData[2] = (Float) navScale / MAGNIFY;
		outputData[3] = (Integer) screenSaver; //1; // Screen Number screenSaver;
		outputData[4] = (Integer) imageNumber;
		outputData[5] = (Integer) isSeekBarTouched;
		
		
		oscBundle.addPacket(new OSCMessage("/Void/Leeum", outputData));
		

		oscInterface.sendOSCBundle(oscBundle);
		
//		android.util.Log.i("screen number", "" + screenSaver);
		
	}

	
	
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
		
		android.util.Log.i("surface", "changed");
		
//		this.width = width;
		this.height = height;
		if (width > height) {
			this.scale = width / 480f;
		} else {
			this.scale = height / 480f;
		}
		textPaint.setTextSize(14 * scale);
		Canvas c = getHolder().lockCanvas();
		
		if (c != null) {
			c.drawBitmap(bgImg, 0, 0, null);
			c.drawRect(boundaryLeft, boundaryTop, boundaryRight, boundaryBottom, bgPaint);
			c.drawBitmap(backgroundImage,bgX,bgY,null);
			c.drawRect(navX - navScale + (8 / 2 * MAGNIFY), navY - (navScale / 9*16) + (8 / 2 * MAGNIFY), navX + navScale + (8 / 2 * MAGNIFY), navY + (navScale / 9*16) + (8 / 2 * MAGNIFY), navShadowPaint);			
			c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);

			
			// ui guide 
			c.drawBitmap(arrowLeft, navX-(navScale + (20 / 2 * MAGNIFY) + arrowLeft.getWidth()), navY - (arrowLeft.getHeight()/2), null);
			c.drawBitmap(arrowRight, navX+(navScale + (20 / 2 * MAGNIFY)), navY - (arrowRight.getHeight()/2), null);
			c.drawBitmap(arrowUp, navX - (arrowUp.getWidth()/2), navY - ((navScale / 9*16) + (20 / 2 * MAGNIFY) + arrowUp.getHeight()), null);
			c.drawBitmap(arrowDown, navX - (arrowDown.getWidth()/2), navY + ((navScale / 9*16) + (20 / 2 * MAGNIFY)), null);
			c.drawBitmap(uiHand, navX - uiHand.getWidth()/2, navY - uiHand.getHeight()/2, null);
			
			if ((!oscInterface.isReachable()) || (drawAdditionalInfo)) drawInfo(c);
			getHolder().unlockCanvasAndPost(c);
		}
	}

	
	public void surfaceCreated(SurfaceHolder holder) {
		
		
		android.util.Log.i("surface", " creadted");
		
		running = true;

		new Thread(new Runnable() {
		    public void run() {
		      boolean network = oscInterface.isReachable();
		      while (running) {
		    	  
    			  oscInterface.checkStatus();
    			  boolean status = oscInterface.isReachable();
		    	  if (network!=status) {
		    		  network = status;
		    		  sourceName = "TUIOdroid@"+getLocalIpAddress();
		    		  Canvas c = getHolder().lockCanvas();
		    		  if (c != null) {
		    				c.drawBitmap(bgImg, 0, 0, null);
		    				c.drawRect(boundaryLeft, boundaryTop, boundaryRight, boundaryBottom, bgPaint);
		    				c.drawBitmap(backgroundImage,bgX,bgY,null);
		    				c.drawRect(navX - navScale + (8 / 2 * MAGNIFY), navY - (navScale / 9*16) + (8 / 2 * MAGNIFY), navX + navScale + (8 / 2 * MAGNIFY), navY + (navScale / 9*16) + (8 / 2 * MAGNIFY), navShadowPaint);			
		    				c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);

		    				
		    				// ui guide 
		    				c.drawBitmap(arrowLeft, navX-(navScale + (20 / 2 * MAGNIFY) + arrowLeft.getWidth()), navY - (arrowLeft.getHeight()/2), null);
		    				c.drawBitmap(arrowRight, navX+(navScale + (20 / 2 * MAGNIFY)), navY - (arrowRight.getHeight()/2), null);
		    				c.drawBitmap(arrowUp, navX - (arrowUp.getWidth()/2), navY - ((navScale / 9*16) + (20 / 2 * MAGNIFY) + arrowUp.getHeight()), null);
		    				c.drawBitmap(arrowDown, navX - (arrowDown.getWidth()/2), navY + ((navScale / 9*16) + (20 / 2 * MAGNIFY)), null);
		    				c.drawBitmap(uiHand, navX - uiHand.getWidth()/2, navY - uiHand.getHeight()/2, null);
		    				
		    				
		    				if (!network || drawAdditionalInfo) drawInfo(c);
		    				getHolder().unlockCanvasAndPost(c);
		    		  }		    		  
		    	  }
		    	 
		    	  if (sendPeriodicUpdates) {
		    		  try { sendTUIOdata(); }
		    	  	  catch (Exception e) {}
		    	  }
		    	  try { Thread.sleep(1000/FRAME_RATE); }
		    	  catch (Exception e) {}
		      }
		    }
		}).start();
		
		
		

		
	}
	

	public void surfaceDestroyed(SurfaceHolder holder) {
		
		android.util.Log.i("surface"," destroyed");
		
		
		
		running = false;
		tuioPoints.clear();
		startTime = System.currentTimeMillis();
		lastTime = 0;
		
	
	}
		
	/**
	 * Sets up a new OSC connection
	 * @param ip
	 * @param port
	 */
	public void setNewOSCConnection (String oscIP, int oscPort){	
		oscInterface.closeInteface();
		oscInterface = new OSCInterface(oscIP,oscPort);
		sourceName = "TUIOdroid@"+getLocalIpAddress();
	}

    public String getLocalIpAddress() {
       try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {}
        return "127.0.0.1";
        
        
    	
    }
    
    public void resetToDefault(){
    	
    	
    	
    	screenSaver = 0;
		navX = defaultNavX;
		navY = defaultNavY;
		navScale = defaultNavScale;
		navPaint.setColor(Color.rgb(0, 191, 174));
		
		setBGImg();
		
		Canvas c = getHolder().lockCanvas();

		if(c != null)
		{
			
			c.drawBitmap(bgImg, 0, 0, null);
			c.drawRect(boundaryLeft, boundaryTop, boundaryRight, boundaryBottom, bgPaint);
			c.drawBitmap(backgroundImage,bgX,bgY,null);
			c.drawRect(navX - navScale + (8 / 2 * MAGNIFY), navY - (navScale / 9*16) + (8 / 2 * MAGNIFY), navX + navScale + (8 / 2 * MAGNIFY), navY + (navScale / 9*16) + (8 / 2 * MAGNIFY), navShadowPaint);			
			c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);

			
			// ui guide 
			c.drawBitmap(arrowLeft, navX-(navScale + (20 / 2 * MAGNIFY) + arrowLeft.getWidth()), navY - (arrowLeft.getHeight()/2), null);
			c.drawBitmap(arrowRight, navX+(navScale + (20 / 2 * MAGNIFY)), navY - (arrowRight.getHeight()/2), null);
			c.drawBitmap(arrowUp, navX - (arrowUp.getWidth()/2), navY - ((navScale / 9*16) + (20 / 2 * MAGNIFY) + arrowUp.getHeight()), null);
			c.drawBitmap(arrowDown, navX - (arrowDown.getWidth()/2), navY + ((navScale / 9*16) + (20 / 2 * MAGNIFY)), null);
			c.drawBitmap(uiHand, navX - uiHand.getWidth()/2, navY - uiHand.getHeight()/2, null);
			
		}
		getHolder().unlockCanvasAndPost(c);
    	
    	
    }

	


	
	
	

}