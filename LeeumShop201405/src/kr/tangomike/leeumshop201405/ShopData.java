package kr.tangomike.leeumshop201405;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Application;

@SuppressLint("DefaultLocale")
public class ShopData extends Application {
	
	public static final int DATA_NEW = 100;
	public static final int DATA_RECOMMAND = 101;
	public static final int DATA_SALE = 102;
	public static final int DATA_ARTIST = 103;
	public static final int DATA_LIVING = 104;
	public static final int DATA_STATIONARY = 105;
	public static final int DATA_CRAFTS = 106;
	public static final int DATA_PRINTS = 107;
	public static final int DATA_BG = 108;
	public static final int DATA_BG_ANI = 109;
	// 추가 어레이 명칭 넣을
	
	
	// 전역 상수
	public static final int SCREEN_SAVER_TIME = 120;
	public static final int FONT_SIZE = 30;
	
	
	// 전역 문구 
	public static final String NO_DESC_ENG = "No Description.";
	public static final String NO_DESC_KOR = "상세 설명이 없습니다.";
	public static final String LAST_PAGE = "Last Page";
	public static final String FIRST_PAGE = "First Page";
	 
	
	private ArrayList<String> newArrivalArray;
	private ArrayList<String> recommandArray;
	private ArrayList<String> saleArray;
	private ArrayList<String> artistArray;
	private ArrayList<String> livingArray;
	private ArrayList<String> stationaryArray;
	private ArrayList<String> craftsArray;
	private ArrayList<String> printsArray;
	private ArrayList<String> bgArray;
	private ArrayList<String> bgAniArray;
	
	
	private void InitArray(){
		newArrivalArray = new ArrayList<String>();
		recommandArray = new ArrayList<String>();
		saleArray = new ArrayList<String>();
		artistArray = new ArrayList<String>();
		livingArray = new ArrayList<String>();
		stationaryArray = new ArrayList<String>();
		craftsArray = new ArrayList<String>();
		printsArray = new ArrayList<String>();
		
		bgArray = new ArrayList<String>();
		bgAniArray = new ArrayList<String>();
		
		setupNewArrival();
		setupRecommand();
		setupSale();
		setupArtist();
		setupLiving();
		setupStationary();
		setupCrafts();
		setupPrints();
		setupBGs();
	}
	
	
	@SuppressLint("DefaultLocale")
	private void setupNewArrival(){
		
		int tmp = 0;
		
		while(true){
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/01_New_Products/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				newArrivalArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			tmp++;
		}

	} 
	
	@SuppressLint("DefaultLocale")
	private void setupRecommand(){
		
		
		int tmp = 0;
		
		while(true){
			
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/02_Recommanded/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				recommandArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			
			tmp++;
		}
		
	}
	
	
	private void setupSale(){
		
		int tmp = 0;
		
		while(true){
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/03_Sale/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				saleArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			tmp++;
			
		}
		
	}
	
	private void setupArtist(){
		
		int tmp = 0;
		
		while(true){
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/04_Artist/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				artistArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			tmp++;
		}
		
	}
	
	
	private void setupLiving(){
		
		int tmp = 0;
		
		while(true){
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/05_Living/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				livingArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			tmp++;
			
		}
		
		
	}
	
 
	private void setupStationary(){
		
		int tmp = 0;
		
		while(true){
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/06_Stationary/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				stationaryArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			tmp++;
			
		}
		
	}
	
	private void setupCrafts(){
		
		int tmp = 0;
		
		while(true){
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/07_Crafts/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				craftsArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			
			tmp++;
			
		}
		
		
	}
	
	
	private void setupPrints(){
		
		int tmp = 0;
		
		while(true){
			
			
			File file;
			String str;
			
			str = String.format("/storage/extSdCard/ShopData/08_Prints/n%04d", tmp);
			file = new File(str+".jpg");
			if(file.exists()){
				printsArray.add(str);
				android.util.Log.i("path", str);
			}
			
			if(!file.exists() && tmp%10 == 0) break;
			
			tmp++;
			
		}
		
		
	}
	
	private void setupBGs(){
		
		File file;
		String str;
		
		for(int i = 1; i < 6; i++){
			for(int t = 1; t < 6; t++){
				
				str = String.format("/storage/extSdCard/ShopData/00_Home_BG/%d_%d.jpg", i, t);
				file = new File(str);
				if(file.exists()){
					
					bgArray.add(str);
					bgAniArray.add("" + t);
					
					android.util.Log.i("path", str);
				}
				
				
				
			}
			
			
		}
		
	}
	
	
	
	
	public String getDataPath(int type, int index){
		String str = "";
		
		switch(type){
			case DATA_NEW:
				str = newArrivalArray.get(index);
				break;
				
			case DATA_RECOMMAND:
				str = recommandArray.get(index);
				break;
				
				
			case DATA_SALE:
				str = saleArray.get(index);
				break;
				
				
			case DATA_ARTIST:
				str = artistArray.get(index);
				break;
				
				
			case DATA_LIVING:
				str = livingArray.get(index);
				break;
				
				
			case DATA_STATIONARY:
				str = stationaryArray.get(index);
				break;
				
				
			case DATA_CRAFTS:
				str = craftsArray.get(index);
				break;
				
				
			case DATA_PRINTS:
				str = printsArray.get(index);
				break;
				
			case DATA_BG:
				str = bgArray.get(index);
				break;
				
				
			case DATA_BG_ANI:
				str = bgAniArray.get(index);
				break;
				
			default:
				str = "error";
				android.util.Log.i("error", "data type error");
				
				break;
		
		}
		
		return str;
		
	}
	
	
	
	public int getDataCount(int type){
		
		int retVal = 0;

		switch(type){
			case DATA_NEW:
				retVal = newArrivalArray.size();
				break;
				
			case DATA_RECOMMAND:
				retVal = recommandArray.size();
				break;
				
				
			case DATA_SALE:
				retVal = saleArray.size();
				break;
				
				
			case DATA_ARTIST:
				retVal = artistArray.size();
				break;
				
			case DATA_LIVING:
				retVal = livingArray.size();
				break;
				
				
			case DATA_STATIONARY:
				retVal = stationaryArray.size();
				break;
				
				
			case DATA_CRAFTS:
				retVal = craftsArray.size();
				break;
				
				
			case DATA_PRINTS:
				retVal = printsArray.size();
				break;
				
				
			default:
				retVal = 0;
				android.util.Log.i("error", "data type error");
				
				break;
		
		}
		
		
		return retVal;
		
	}
	
	
	public void onCreate(){
		
		super.onCreate();
		
		InitArray();
		
	}
	
	
	
	

}
