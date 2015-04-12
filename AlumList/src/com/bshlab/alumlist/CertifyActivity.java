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
		
		
		
		// �׸� �Է� �˻�
		if(name.isEmpty()){
			Toast toast = Toast.makeText(getApplicationContext(),  "�̸��� �ʼ� �Է� �׸��Դϴ�.", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		if(notation.isEmpty()){
			Toast toast = Toast.makeText(getApplicationContext(),  "����� �ʼ� �Է� �׸��Դϴ�.", Toast.LENGTH_SHORT);
			toast.show();
			return;
			
		}
		
		if(password.isEmpty()){
			Toast toast = Toast.makeText(getApplicationContext(),  "������ȣ�� �ʼ� �Է� �׸��Դϴ�.", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		if(password.length() != 6){
			Toast toast = Toast.makeText(getApplicationContext(),  "������ȣ�� �����ڸ� �����Դϴ�.", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		
		
		dbAdapter.createDatabase();
		dbAdapter.open();
		
		isCertified = dbAdapter.getCertification(Integer.parseInt(notation), name, password);
		
		dbAdapter.close();
		
		if(isCertified){
			
			Toast toast = Toast.makeText(getApplicationContext(),  "���� ������ �Ϸ�Ǿ����ϴ�.", Toast.LENGTH_SHORT);
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
			
			Toast toast = Toast.makeText(getApplicationContext(),  "���� ������ ��ġ���� �ʽ��ϴ�. ������ ��� �� ��� �а� �繫�Ƿ� ���� ��Ź�帳�ϴ�.", Toast.LENGTH_LONG);
			toast.show();
			
		}
		
		
	}
	
	


}
