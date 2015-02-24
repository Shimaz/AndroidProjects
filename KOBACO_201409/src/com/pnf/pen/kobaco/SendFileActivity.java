package com.pnf.pen.kobaco;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class SendFileActivity extends Activity {
	
//	private int imgKind;
	private String serverIP;
	private int serverPort;
	
	private String fp;
	
	private Handler mHandler;
	private Handler tHandler;

	private final int CHECK_TIME = 30;
	private int dTime = 0;

	private OSCBinder oscBinder;
	
	private RelativeLayout rlMain;
	private Button btnOK;
	private Button btnCancel;
	
	private boolean isSuceed = false;
	
	private ImageView ivBack;
	
	
	
	@Override 
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		
		StrictMode.enableDefaults();
		
//		imgKind = getIntent().getExtras().getInt("imgKind");
		
		fp = getIntent().getExtras().getString("fp");
		
		
		oscBinder = (OSCBinder)getApplicationContext();
		
	
		
		serverIP = getResources().getString(R.string.server_ip);
        serverPort = Integer.parseInt(getResources().getString(R.string.server_port));
		/**
		 * OK Button 클릭 시 bmp -> jpg변환
		 * sd_card에 저장한 뒤 그것을 fileStream으로 
		 * 이 때 파일명은 imgKind로 이미지 종류를 구분하여 설정
		 *  
		 * 0_YYYY_MM_DD_HH_MM_SS.jpg
		 * 
		 * 
		 * 
		 * 전송 완료 후 OSC 전송, RESULT_OK로 종료를 하여 DrawAcitivity까지 함께 종료하도록 구
		 * 
		 * 
		 * 취소할 경우 본 Activity만 종료.
		 * 
		 */
        
        
        rlMain = new RelativeLayout(this);
        rlMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        rlMain.setBackgroundResource(R.drawable.img_back_conversation);
 
        rlMain.setMotionEventSplittingEnabled(false);
        
//        rlMain.setBackgroundColor(Color.argb(178, 0, 0, 0));
        
        String path = getIntent().getExtras().getString("bgPath");
		

        BitmapDrawable dbp = new BitmapDrawable(path);
        
        rlMain.setBackgroundDrawable(dbp);
        
        
        setContentView(rlMain);
        
        
        ivBack = new ImageView(this);
        ivBack.setBackgroundResource(R.drawable.img_back_conversation);
        
        rlMain.addView(ivBack);
        
        btnOK = new Button(this);
        btnOK.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnOK.setBackgroundResource(R.drawable.btn_ok);
        btnOK.setX(359);
        btnOK.setY(656);
        btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!isSuceed){
					mHandler.sendEmptyMessage(0);
				}
				
				dTime = 0;
			}
		});
        
        rlMain.addView(btnOK);
		
        mHandler = new Handler() {
        	public void handleMessage(Message msg){
        		
        		sendFile();
        		
        	}
        };
        
        
        
        btnCancel = new Button(this);
        btnCancel.setBackgroundResource(R.drawable.btn_cancel);
        btnCancel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnCancel.setX(218);
        btnCancel.setY(656);
        btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				
			}
		});
        
        rlMain.addView(btnCancel);
        
        
        // 타이머 

        tHandler = new Handler(){
        	@Override
        	public void handleMessage(Message msg){
        		
        		if(dTime == CHECK_TIME){
        			
        			setResult(OSCBinder.RESULT_TIMER);
        			overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
        			finish();
        			
        		}else{
        			
        			dTime++;
        			
        			tHandler.sendEmptyMessageDelayed(0, 1000);
        			
        		}
        		
        	}
        };
        
        tHandler.sendEmptyMessage(0);
        
   
	}
	
	

	
	
	
	
	final private void sendFile(){
		
		
		
	
		
		
		Socket socket;
		
		
		try {
			
			/* 파일을 전송하는 경우 */
//			socket = new Socket(serverIP, serverPort);
//			File myFile = new File (fp);
//			FileInputStream fis = new FileInputStream(myFile);
//			OutputStream os = socket.getOutputStream();
//			int filesize = (int) myFile.length();
//
//			
//			android.util.Log.i("shimaz", fp);
//			
////			byte[] buffer = stream.toByteArray();
//			byte [] buffer  = new byte [filesize];
//			     int bytesRead =0;
//			     
//			     os.write(buffer);
//			     while ((bytesRead = fis.read(buffer)) > 0) {
//			     os.write(buffer, 0, bytesRead);
//			//Log display exact the file size
//			     System.out.println("SO sendFile" + bytesRead);
//			     }
//			     os.flush();
//			     os.close();
//			     fis.close();
//			     Log.d("Client", "Client sent message");
//			     socket.close();
			
			
			/* byte array 를 전송하는 경우 */
			socket = new Socket(serverIP, serverPort);
			OutputStream os = socket.getOutputStream();
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			
			Bitmap bmp = BitmapFactory.decodeFile(fp);
			
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			
			
			byte[] buffer = stream.toByteArray();
			
			os.write(buffer);
			
			
			
			os.flush();
			os.close();
			Log.d("Client", "Client sent message");
			socket.close();
			
			isSuceed = true;
			
			
		}catch(UnknownHostException e){
			e.printStackTrace();
			
			Toast.makeText(this, "전송에 실패하였습니다. 재전송을 원하시면 확인 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
			isSuceed = false;
			
			return;
			
		}catch(IOException e){
			e.printStackTrace();
			
			Toast.makeText(this, "전송에 실패하였습니다. 재전송을 원하시면 확인 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
			isSuceed = false;
			return;
			
		}
		
		
		if(isSuceed){
			
			
			
			
			
			
			btnOK.setVisibility(View.GONE);
			btnOK.setEnabled(false);
			btnCancel.setVisibility(View.GONE);
			btnCancel.setEnabled(false);

			ivBack.setBackgroundResource(R.drawable.img_back_complete);
			
			
			Button btnClose = new Button(this);
			btnClose.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			btnClose.setBackgroundResource(R.drawable.btn_popup_close);
			btnClose.setX(355);
			btnClose.setY(823);
			btnClose.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
//					oscBinder.sendOSCData(imgKind, 1);
					
					setResult(RESULT_OK);
					
					finish();
					
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				}
			});
			
			rlMain.addView(btnClose);
			
			
			Handler th = new Handler(){
				@Override
				public void handleMessage(Message msg){
					
					oscBinder.sendOSCData(0, 1);
					
				}
			};
			th.sendEmptyMessageDelayed(0, 1000);
			
	
			
			
		}else{
			
			// 재시도 버튼 생성 
			
			
			
			
		}
			
		
		

		
	}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		tHandler.removeMessages(0);
		tHandler = null;
		
	}

}
