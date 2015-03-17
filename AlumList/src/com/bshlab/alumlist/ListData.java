package com.bshlab.alumlist;

public class ListData {
	private int id;
	private int notation;
	private String name;
	private String mobile;
	private String email;
	private String company;
	
	public ListData(){
		
	}
	
	public ListData(int id, int notation, String name, String mobile, String email, String company){
		this.id = id;
		this.notation = notation;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.company = company;
	}
	
	public int getID(){
		return this.id;
	}
	
	public int getNotation(){
		return this.notation;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getMobile(){
		return this.mobile;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getCompany(){
		return this.company;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setNotation(int not){
		this.notation = not;
	}
	
	public void setName(String nn){
		this.name = nn;
		
	}
	
	public void setMobile(String mob){
		this.mobile = mob;
	}

	public void setEmail(String em){
		this.email = em;
	}
	
	public void setCompany(String comp){
		this.company = comp;
		
	}
}
