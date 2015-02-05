package kr.tangomike.leeum.yhg.pad_h;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends Activity {

	private boolean isKorean;
	private ScrollView scrlKor;
	private ScrollView scrlEng;
	private Button btnLang;
	private Button btnPlayPause;
	private ImageView ivTitle;
	private Handler tHandler;
	private long videoDuration;
	
	private SeekBar sbVideo;
	
	private TextView tvTime;
	
	private final static int screenSaverOnTime = 30;
	private int tCounter = 0;
	
	private int pausePosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Disable Sleep Mode */
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		
		isKorean = true;
		scrlKor = (ScrollView)findViewById(R.id.scrl_kor);
		scrlKor.getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener(){

			@Override
			public void onScrollChanged() {
				// TODO Auto-generated method stub
				tCounter = 0;
			}
			
			
		});
		
		scrlEng = (ScrollView)findViewById(R.id.scrl_eng);
		scrlEng.getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener(){

			@Override
			public void onScrollChanged() {
				// TODO Auto-generated method stub
				tCounter = 0;
			}
			
		});
		
		
		
		
		ivTitle = (ImageView)findViewById(R.id.iv_title);
		
		setContent(isKorean);
		
		btnLang = (Button)findViewById(R.id.btn_lang);
		btnLang.setBackgroundResource(R.drawable.btn_eng);
		btnLang.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				tCounter = 0;
				
				if(isKorean){
					isKorean = false;
					btnLang.setBackgroundResource(R.drawable.btn_kor);
					setContent(isKorean);
					
				}else{
					isKorean = true;
					btnLang.setBackgroundResource(R.drawable.btn_eng);
					setContent(isKorean);
				}
				
			}
		});
		
		
	
		
		
		final VideoView vv = (VideoView)findViewById(R.id.vv_main);
		String path = "android.resource://" + getPackageName() + "/" + R.raw.lov_mov_enc;
		vv.setVideoURI(Uri.parse(path));
		vv.start();
		vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
			
				videoDuration = vv.getDuration();
				sbVideo.setMax((int)(videoDuration / 1000));
			}
		});
		
		
		
		vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				btnPlayPause.setBackgroundResource(R.drawable.play);
				pausePosition = 0;
			}
		});
		
		
		btnPlayPause = (Button)findViewById(R.id.btn_playpause);
		btnPlayPause.setBackgroundResource(R.drawable.pause);
		btnPlayPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(vv.isPlaying()){
					btnPlayPause.setBackgroundResource(R.drawable.play);
					pausePosition = vv.getCurrentPosition();
					vv.pause();
					
				}else{
					btnPlayPause.setBackgroundResource(R.drawable.pause);
					vv.seekTo(pausePosition);
					vv.start();
					
				}
				
			}
		});
		
		sbVideo = (SeekBar)findViewById(R.id.sb_video);
		sbVideo.setProgress(0);
		
		sbVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			
				if(fromUser){
					if(!vv.isPlaying()) vv.start();
					vv.seekTo(progress * 1000);
				}
				
			}
		});
		
	
		tvTime = (TextView)findViewById(R.id.tv_time);
		
		
		tHandler = new Handler(){
		
			@Override 
			public void handleMessage(Message msg){
				
			
				android.util.Log.i("seek", "" + vv.getCurrentPosition() / 1000 + " " + vv.getDuration() / 1000);
				
				tHandler.sendEmptyMessageDelayed(0, 1000);
				sbVideo.setProgress(vv.getCurrentPosition() / 1000);
				
				int tmpMin = (int)((vv.getCurrentPosition() / 1000 )/ 60);
				int tmpSec = (int)((vv.getCurrentPosition() / 1000 ) % 60);
			
				String strMin = String.format("%02d", tmpMin);
				String strSec = String.format("%02d", tmpSec);
				
				tvTime.setText(strMin + ":" + strSec);
				
				if(!vv.isPlaying()){
					tCounter++;
				}else{
					tCounter = 0;
				}
				
				
				if(tCounter > screenSaverOnTime){
					tHandler.removeMessages(0);
					finish();
					overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
				}
			}
			
		};
		
		tHandler.sendEmptyMessage(0);
		
	}
	
	
	private void setContent(boolean lang){
		
		if(lang){
			scrlKor.scrollTo(0, 0);
			scrlKor.setVisibility(View.VISIBLE);
			scrlEng.setVisibility(View.GONE);
			ivTitle.setBackgroundResource(R.drawable.h_title_kor);
			
		}else{
			scrlEng.scrollTo(0, 0);
			scrlEng.setVisibility(View.VISIBLE);
			scrlKor.setVisibility(View.GONE);
			ivTitle.setBackgroundResource(R.drawable.h_title_eng);
			
		}
		
		
		
		
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		tHandler.removeMessages(0);
		tHandler = null;
		
		
	}
}
