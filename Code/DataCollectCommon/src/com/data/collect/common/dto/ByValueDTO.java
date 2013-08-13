package com.data.collect.common.dto;

public class ByValueDTO {
	private String byValue1 = "";	
	private String byValue2 = "";
	private String byValue3 = "";
	private String byValue4 = "";
	private String byValue5 = "";
	public String getByValue1() {
		if(byValue1==null)
		{
			byValue1 = "";
		}
		return byValue1;
	}
	public ByValueDTO setByValue1(String byValue1) {
		this.byValue1 = byValue1;
		return this;
	}
	public String getByValue2() {
		if(byValue2==null)
		{
			byValue2 = "";
		}
		return byValue2;
	}
	public ByValueDTO setByValue2(String byValue2) {
		this.byValue2 = byValue2;
		return this;
	}
	public String getByValue3() {
		if(byValue3==null)
		{
			byValue3 = "";
		}
		return byValue3;
	}
	public ByValueDTO setByValue3(String byValue3) {
		this.byValue3 = byValue3;
		return this;
	}
	public String getByValue4() {
		if(byValue4==null)
		{
			byValue4 = "";
		}
		return byValue4;
	}
	public ByValueDTO setByValue4(String byValue4) {
		this.byValue4 = byValue4;
		return this;
	}
	public String getByValue5() {
		if(byValue5==null)
		{
			byValue5 = "";
		}
		return byValue5;
	}
	public ByValueDTO setByValue5(String byValue5) {
		this.byValue5 = byValue5;
		return this;
	}
	
	public static ByValueDTO createInstance()
	{
		ByValueDTO ret = new ByValueDTO();
		return ret;
	}
}
