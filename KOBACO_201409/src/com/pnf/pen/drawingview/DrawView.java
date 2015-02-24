


package com.pnf.pen.drawingview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.View;

//import com.pnf.pen.test.MainDefine;

public class DrawView extends View {
	protected final String TAG = "DrawView";
	
	Context mContext;
	
	Paint mPaint;
	Bitmap mBitmap;
	Canvas mCanvas;
	Path mPath;
	Path rectPath;
	
	public Paint EraserPaint;
	
	int iDrawCnt = 0;
	PowerManager pm;
	PowerManager.WakeLock wl;
	
	Rect m_rtRender;
    int m_nRenderedIdx;
    boolean m_bRendering;
	boolean m_bRenderingStop;
	
	
	private final int marginX = 235;
	private final int marginY = 44;
	
	ArrayList<PointF> m_Stroke;
	
	public DrawView(Context c)
	{
		super(c);
		initMyview(c);
	}
	
	
	public DrawView(Context c, AttributeSet attrs) 
	{
		super(c, attrs);
		initMyview(c);
		
	}
	
	void initMyview(Context c)
	{
//		if (isInEditMode()) {
//            return;
//        }
		
		mContext = c;
		
		m_Stroke = new ArrayList<PointF>();
		
		mPath = new Path();
		rectPath = new Path();
		
		if(mPaint == null){
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setColor(Color.BLACK);
			mPaint.setAntiAlias(true);
			mPaint.setStrokeWidth(3);
			mPaint.setAlpha(255);
		}
		
		if(EraserPaint == null){
			EraserPaint = new Paint();
			EraserPaint.setStyle(Paint.Style.FILL);
			EraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		}
		
		if(mBitmap == null){
//			mBitmap = Bitmap.createBitmap(MainDefine.iDisGetWidth, MainDefine.iDisGetHeight, Bitmap.Config.ARGB_8888);
			
			Bitmap tmp = BitmapFactory.decodeResource(getResources(), com.pnf.pen.kobaco.R.drawable.img_draw_back_moon);
			mBitmap = tmp.copy(Bitmap.Config.ARGB_8888, true);
			mCanvas = new Canvas(mBitmap);
		}
		
		if(pm == null){
			pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);//
		}
		if(wl == null){
			wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, TAG);
		}
	}
	
	
	@Override
	protected void onDraw(Canvas c) {
		c.drawBitmap(mBitmap, 0, 0, null);
	}
	
	
	PointF	previousPoint1 = new PointF();
	PointF	previousPoint2 = new PointF();
	PointF	currentPoint = new PointF();
	/*
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		RectF drawRect = null;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			DoMouseDown(x ,y);
			
			break;
		case MotionEvent.ACTION_MOVE:
			DoMouseDragged(x ,y);
			
			drawRect = new RectF();
			rectPath.computeBounds(drawRect, true);
			invalidate(RectFtoRect(drawRect,10));
			
			rectPath.reset();
			break;
		case MotionEvent.ACTION_UP:
			DoMouseUp(x ,y);
			
			drawRect = new RectF();
			rectPath.computeBounds(drawRect, true);
			invalidate(RectFtoRect(drawRect,10));
			
			rectPath.reset();
			break;
		}
		return true;
	}
	*/
	public void invalidatePath(){
		RectF drawRect = new RectF();
		rectPath.computeBounds(drawRect, true);
		invalidate(RectFtoRect(drawRect,10));
	} 
	
	int nDrawingPoint = 0;
	public void DoMouseDown(float fx ,float fy){
		
		float x = fx - marginX;
		float y = (fy * 0.88f) - marginY;
		
		iDrawCnt = 0;
		mPath.reset();
		
		
		previousPoint1 = new PointF(x ,y);
		previousPoint2 = new PointF(x ,y);
		currentPoint = new PointF(x ,y);
		
	}
	
	public void DoMouseDragged(float fx ,float fy){
		
		float x = fx - marginX;
		float y = (fy * 0.88f) - marginY;
		
		if(iDrawCnt == 0 ){
			previousPoint2 = new PointF(previousPoint1.x ,previousPoint1.y);
			currentPoint = new PointF(x ,y);
		}else{
			previousPoint2 = new PointF(previousPoint1.x ,previousPoint1.y);
			previousPoint1 = new PointF(currentPoint.x ,currentPoint.y);
			currentPoint = new PointF(x ,y);
		}
		
		PointF mid1 = BizMidPoint(previousPoint1, previousPoint2);
		PointF mid2 = BizMidPoint(currentPoint, previousPoint1);
		
		mPath.moveTo(mid1.x, mid1.y);
		mPath.quadTo(previousPoint1.x, previousPoint1.y, mid2.x, mid2.y);
		mCanvas.drawPath(mPath, mPaint);
		iDrawCnt++;
		
		
		rectPath.moveTo(mid1.x, mid1.y);
		rectPath.quadTo(previousPoint1.x, previousPoint1.y, mid2.x, mid2.y);
	}

	public void DoMouseUp(float fx ,float fy){
		
		float x = fx - marginX;
		float y = (fy * 0.88f) - marginY;
		
		previousPoint2 = new PointF(previousPoint1.x ,previousPoint1.y);
		previousPoint1 = new PointF(currentPoint.x ,currentPoint.y);
		currentPoint = new PointF(x ,y);
		PointF mid1 = BizMidPoint(previousPoint1, previousPoint2);
//		PointF mid2 = BizMidPoint(currentPoint, previousPoint1);
		
		mPath.moveTo(mid1.x, mid1.y);
		mPath.quadTo(previousPoint1.x, previousPoint1.y, currentPoint.x, currentPoint.y);
		mCanvas.drawPath(mPath, mPaint);
		
		rectPath.moveTo(mid1.x, mid1.y);
		rectPath.quadTo(previousPoint1.x, previousPoint1.y, currentPoint.x, currentPoint.y);
	}
	
	
	public void btnEraser()
	{
//		mCanvas.drawPaint(EraserPaint);
		mCanvas.drawBitmap(mBitmap, 0,  0, null);
		invalidate();
	}
	
	public PointF BizMidPoint(PointF pt,PointF pt2)
	{
		return new PointF((pt.x + pt2.x)/2, (pt.y + pt2.y)/2);
	}
	
	public Rect RectFtoRect(RectF rectf,float thick)
	{
		Rect rect = new Rect((int)(rectf.left-thick) ,(int)(rectf.top-thick) ,(int)(rectf.right+thick) ,(int)(rectf.bottom+thick));
		return rect;
	}
	
	
	public void setColor(int color){
	
		switch(color){
		default:
		case 1:
			mPaint.setColor(Color.rgb(243, 182, 84));
			break;
			
		case 2:
			mPaint.setColor(Color.rgb(232, 109, 88));
			break;
			
		case 3:
			mPaint.setColor(Color.rgb(62, 143, 181));
			break;
			
		case 4:
			mPaint.setColor(Color.rgb(95, 174, 119));
			break;
			
			
		case 5:
			mPaint.setColor(Color.rgb(223, 223, 223));
			break;
			
			
		case 6:
			mPaint.setColor(Color.rgb(99, 100, 100));
			break;
			
		case 7:
			mPaint.setColor(Color.rgb(51, 51, 51));
			break;
			
			
		}
		
	
	}
}