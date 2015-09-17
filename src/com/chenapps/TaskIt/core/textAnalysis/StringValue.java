package com.chenapps.TaskIt.core.textAnalysis;

public class StringValue {
	private String keyString;
	private int keyValue;
	public StringValue(String keyString, int keyValue) {
		this.keyString = keyString;
		this.keyValue = keyValue;
		
	}
	String getKeyString(){
		return keyString;
	}
	int getKeyValue(){
		return keyValue;
	}
}
