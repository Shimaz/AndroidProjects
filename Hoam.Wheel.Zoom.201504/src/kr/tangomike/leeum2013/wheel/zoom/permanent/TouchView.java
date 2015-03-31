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

package kr.tangomike.leeum2013.wheel.zoom.permanent;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import kr.tangomike.leeum2013.wheel.zoom.permanent.TuioPoint;


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
public class TouchView extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final int MAX_TOUCHPOINTS = 2;
	private static final int FRAME_RATE = 60;
	private Paint textPaint = new Paint();
	private Paint dataPaint = new Paint();
	private Paint bgPaint = new Paint();
	private Paint touchAreaPaint = new Paint();
	
	
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
	
	private Paint navPaint = new Paint();
	private Paint navShadowPaint = new Paint();

	
	private static final float maxNavScale = 289.0f;
	
	
	private static final int defaultNavScale = 153; 
	private static final int defaultNavX = 400;
	private static final int defaultNavY = 548;
	private static final float minNavScale = 28.5f;

	
	
	
	
	private float navX = defaultNavX;
	private float navY = defaultNavY;
	private float navScale = defaultNavScale;
	
	private float xDif = 0;
	private float yDif = 0;

	private double dDifNow = 1.0f;
	private double dDifOld = 150.0f;
	
	
	
	
	private boolean insideNav = false;
	
	public int screenSaver = 0;
	
	private static int navMargin = 50;
	
	
	private int mode = 0;
	
	public int imageNumber = 1;
	public int isSeekBarTouched = 0;
	
	private Bitmap arrowUp, arrowDown, arrowLeft, arrowRight, uiHand;
	////////////////////
	
	// Boundary Limit
	private static final int boundaryLeft = 111;
	private static final int boundaryTop = 34;
	private static final int boundaryRight = 689;
	private static final int boundaryBottom = 1062;
	
	private static final int centerMargin = 0;
	
	
	private static final int bgLeft = 223;
	private static final int bgTop = 216;
	private static final int bgRight = 577;
	private static final int bgBottom = 899;
	
	
	

	
	
	
	
 
	public String PACKAGE_NAME;

	
	/**
	 * Constructor
	 * @param context
	 * @param devIP
	 * @param oscIP
	 * @param oscPort
	 */
	@SuppressLint("HandlerLeak")
	public TouchView(Context context, String oscIP, int oscPort, boolean drawAdditionalInfo, boolean sendPeriodicUpdates) {
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
		navPaint.setStrokeWidth(5);
		navPaint.setStyle(Style.STROKE);
		navPaint.setAlpha(255);
		navPaint.setAntiAlias(true);

		
		navShadowPaint.setColor(Color.DKGRAY);
		navShadowPaint.setStrokeWidth(5);
		navShadowPaint.setStyle(Style.STROKE);
		navShadowPaint.setAlpha(128);
		navShadowPaint.setAntiAlias(true);
		 
		
		touchAreaPaint.setColor(Color.WHITE);
		touchAreaPaint.setStrokeWidth(40);
		touchAreaPaint.setStyle(Style.STROKE);
		touchAreaPaint.setAlpha(64);
		touchAreaPaint.setAntiAlias(true);
		
		
		bgPaint.setColor(Color.BLACK);
		bgPaint.setStyle(Style.FILL);
		
		
		PACKAGE_NAME = context.getPackageName();
		bgArr = new ArrayList<Bitmap>();
		
		for (int i = 0; i < 72; i++){
			
			String str = String.format("%d", i+1);
			int ir = getResources().getIdentifier("helmet_" + str, "raw", PACKAGE_NAME);
			Bitmap tmpBmp;
			tmpBmp = BitmapFactory.decodeResource(getResources(), ir);
			
			bgArr.add(tmpBmp);
			
		}
		
		backgroundImage = bgArr.get(0);
		bgImg = BitmapFactory.decodeResource(getResources(), R.drawable.rot_bg_1232);
        
		
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
		
		
		
		if (c != null) {
			c.drawBitmap(bgImg, 0, 0, null);
			c.drawRect(boundaryLeft, boundaryTop, boundaryRight, boundaryBottom, bgPaint);
			c.drawBitmap(backgroundImage, bgLeft, bgTop, null);
			
			


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
			
			if(navX <= bgLeft + centerMargin){ // left
				navX = bgLeft + centerMargin;
			}
			if(navX >= bgRight - centerMargin){ // right
				navX = bgRight - centerMargin;
			}
			if(navY <= bgTop + centerMargin){ // top
				navY = bgTop + centerMargin;
			}
			if(navY >= bgBottom - centerMargin){ // bottom
				navY = bgBottom - centerMargin;
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
			c.drawRect(navX - navScale + 4, navY - (navScale / 9*16) + 4, navX + navScale + 4, navY + (navScale / 9*16) + 4, navShadowPaint);			
			// Navigator
			c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);
			
			if(navScale < minNavScale + navMargin/2){
				c.drawRect(navX - navScale - 20, navY - (navScale / 9*16) - 20, navX + navScale + 20, navY + (navScale / 9*16) + 20, touchAreaPaint);
			}

			
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
			c.drawBitmap(backgroundImage,bgLeft,bgTop,null);
			c.drawRect(navX - navScale + 4, navY - (navScale / 9*16) + 4, navX + navScale + 4, navY + (navScale / 9*16) + 4, navShadowPaint);			
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
		
		outputData[0] = (Float) (navX - bgLeft - navScale);
		outputData[1] = (Float) (navY - bgTop - (navScale / 9*16));
		outputData[2] = (Float) navScale;
		outputData[3] = (Integer) screenSaver;
		outputData[4] = (Integer) imageNumber;
		outputData[5] = (Integer) isSeekBarTouched;
		
		
		oscBundle.addPacket(new OSCMessage("/Void/Leeum", outputData));
		

		oscInterface.sendOSCBundle(oscBundle);
		
		
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
			c.drawBitmap(backgroundImage,bgLeft,bgTop,null);
			c.drawRect(navX - navScale + 4, navY - (navScale / 9*16) + 4, navX + navScale + 4, navY + (navScale / 9*16) + 4, navShadowPaint);			
			c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);

			
			// ui guide 
			c.drawBitmap(arrowLeft, navX-(navScale + 10 + arrowUp.getWidth()), navY - (arrowLeft.getHeight()/2), null);
			c.drawBitmap(arrowRight, navX+(navScale + 10), navY - (arrowRight.getHeight()/2), null);
			c.drawBitmap(arrowUp, navX - (arrowUp.getWidth()/2), navY - ((navScale / 9*16) + 10 + arrowUp.getHeight()), null);
			c.drawBitmap(arrowDown, navX - (arrowDown.getWidth()/2), navY + ((navScale / 9*16) + 10), null);
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
		    				c.drawBitmap(backgroundImage,bgLeft,bgTop,null);
		    				c.drawRect(navX - navScale + 4, navY - (navScale / 9*16) + 4, navX + navScale + 4, navY + (navScale / 9*16) + 4, navShadowPaint);			
		    				c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);

		    				
	            			// ui guide 
	            			c.drawBitmap(arrowLeft, navX-(navScale + 10 + arrowUp.getWidth()), navY - (arrowLeft.getHeight()/2), null);
	            			c.drawBitmap(arrowRight, navX+(navScale + 10), navY - (arrowRight.getHeight()/2), null);
	            			c.drawBitmap(arrowUp, navX - (arrowUp.getWidth()/2), navY - ((navScale / 9*16) + 10 + arrowUp.getHeight()), null);
	            			c.drawBitmap(arrowDown, navX - (arrowDown.getWidth()/2), navY + ((navScale / 9*16) + 10), null);
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
			c.drawBitmap(backgroundImage,bgLeft,bgTop,null);
			c.drawRect(navX - navScale + 4, navY - (navScale / 9*16) + 4, navX + navScale + 4, navY + (navScale / 9*16) + 4, navShadowPaint);			
			c.drawRect(navX - navScale, navY - (navScale / 9*16), navX + navScale, navY + (navScale / 9*16), navPaint);
			
			// ui guide 
			c.drawBitmap(arrowLeft, navX-(navScale + 10 + arrowUp.getWidth()), navY - (arrowLeft.getHeight()/2), null);
			c.drawBitmap(arrowRight, navX+(navScale + 10), navY - (arrowRight.getHeight()/2), null);
			c.drawBitmap(arrowUp, navX - (arrowUp.getWidth()/2), navY - ((navScale / 9*16) + 10 + arrowUp.getHeight()), null);
			c.drawBitmap(arrowDown, navX - (arrowDown.getWidth()/2), navY + ((navScale / 9*16) + 10), null);
			c.drawBitmap(uiHand, navX - uiHand.getWidth()/2, navY - uiHand.getHeight()/2, null);
		}
		getHolder().unlockCanvasAndPost(c);
    	
    	
    }

	


	
	
	

}