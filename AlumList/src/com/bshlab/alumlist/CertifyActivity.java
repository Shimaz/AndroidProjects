package com.bshlab.alumlist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CertifyActivity extends Activity {
	
	private EditText etNotation;
	private EditText etName;
	private EditText etPassword;
	
	private boolean isCertified;
	
	private ABClass abc;
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
		
		abc = (ABClass)getApplicationContext();
		
		setContentView(R.layout.layout_certify);
		
		etNotation = (EditText)findViewById(R.id.et_notation);
		etNotation.setNextFocusDownId(R.id.et_name);
		etName = (EditText)findViewById(R.id.et_name);
		etName.setNextFocusDownId(R.id.et_number);
		etPassword = (EditText)findViewById(R.id.et_number);
		
		Button btnCheckCert = (Button)findViewById(R.id.btn_check_cert);
		
		btnCheckCert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				checkCertification();
				
			}
		});
	}
	
	private void checkCertification(){
		
		DBAdapter dbAdapter = new DBAdapter(this);

		String name = etName.getText().toString();
		String notation = etNotation.getText().toString();
		String password = etPassword.getText().toString();
		
		
		
		// 항목 입력 검사
		if(name.isEmpty()){
			Toast toast = Toast.makeText(getApplicationContext(),  "이름은 필수 입력 항목입니다.", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		if(notation.isEmpty()){
			Toast toast = Toast.makeText(getApplicationContext(),  "기수는 필수 입력 항목입니다.", Toast.LENGTH_SHORT);
			toast.show();
			return;
			
		}
		
		if(password.isEmpty()){
			Toast toast = Toast.makeText(getApplicationContext(),  "인증번호는 필수 입력 항목입니다.", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		if(password.length() != 6){
			Toast toast = Toast.makeText(getApplicationContext(),  "인증번호는 여섯자리 숫자입니다.", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		
		
		dbAdapter.createDatabase();
		dbAdapter.open();
		
		isCertified = dbAdapter.getCertification(Integer.parseInt(notation), name, password);
		
		dbAdapter.close();
		
		if(isCertified){
			
			Toast toast = Toast.makeText(getApplicationContext(),  "동문 인증이 완료되었습니다.", Toast.LENGTH_SHORT);
			toast.show();
			
			String path = abc.getSSUFilePath();
			
			File file = new File(path +"ssu.cert");
			String str = notation + " " + name + " " + password;
			
			
		    try {
		      
		       
		        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		        writer.write(str);
		        writer.close();
		      } catch (IOException e) {
		        android.util.Log.w("shimaz", e.getMessage(), e);
		   
		      }
			
			finish();
			
		}else{
			
			Toast toast = Toast.makeText(getApplicationContext(),  "동문 정보가 일치하지 않습니다. 에러가 계속 될 경우 학과 사무실로 문의 부탁드립니다.", Toast.LENGTH_LONG);
			toast.show();
			
		}
		
		
	}
	
	


}
