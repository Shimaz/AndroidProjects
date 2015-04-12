package com.bshlab.alumlist;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class ListAdapterWithSection extends ArrayAdapter<ListData> implements SectionIndexer{
	
	private ArrayList<ListData> arrList;
	private Context context;
	private int itemRID;
	private int sectionRID;
	private String[] mSections;

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }
    
	public ListAdapterWithSection(Context context, int itemRID, int sectionRID, ArrayList<ListData> arrList){
		super(context, itemRID, sectionRID, arrList);
		this.context = context;
		this.arrList = arrList;
		this.itemRID = itemRID;
		this.sectionRID = sectionRID;
		
		// SectionIndexer
        int size = this.arrList.size();
        mSections = new String[size];
         
        for(int i=0; i < size; i++){
//            mSections[i] = this.arrList.get(i).getName().substring(0, 1);
            mSections[i] = "" + this.arrList.get(i).getNotation()+"기";
            //Log.d("mSections[" + i + "]" , mSections[i]);
        }

	}
	
	


	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		final ListData data = arrList.get(position);
		
		View v = convertView;
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(data.getIsHeader()){
			v = vi.inflate(this.sectionRID, null);	
		}else{
			v = vi.inflate(this.itemRID, null);
			
			
		}
		
		
		if(data != null){
			if(!data.getIsHeader()){
				TextView tvName = (TextView)v.findViewById(R.id.tv_row_name);
				TextView tvCompany = (TextView)v.findViewById(R.id.tv_row_company);
				Button btnCall = (Button)v.findViewById(R.id.btn_list_call);
				Button btnEmail = (Button)v.findViewById(R.id.btn_list_emaill);
				if(tvName != null) tvName.setText(data.getName());
				if(tvCompany != null) tvCompany.setText(data.getCompany());
				btnEmail.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 /* Create the Intent */
						Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

						/* Fill it with Data */
						emailIntent.setType("text/plain");
						emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{data.getEmail()});
						emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "제목");
						emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "내용");

						/* Send it off to the Activity-Chooser */
						context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
						
						
					}
				});
				
				btnCall.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent callIntent = new Intent(Intent.ACTION_DIAL);
					    callIntent.setData(Uri.parse("tel:" + data.getMobile()));
					    
					    
					    
					    context.startActivity(callIntent);
						
					}
				});
				
				
				LinearLayout ll = (LinearLayout)v.findViewById(R.id.list_row);
				ll.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					
						
						Intent intent = new Intent(context, DetailActivity.class);
						intent.putExtra("id", data.getID());
						
						context.startActivity(intent);
						
						
					}
				});
				
			}else{
				TextView tvNotation = (TextView)v.findViewById(R.id.tv_notation);
				if(tvNotation != null) tvNotation.setText(data.getNotation() + " 기");
			}
		}
			
		
		
		return v;
	}




	@Override
	public int getPositionForSection(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}




	@Override
	public int getSectionForPosition(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return mSections;
	}

	
	

}