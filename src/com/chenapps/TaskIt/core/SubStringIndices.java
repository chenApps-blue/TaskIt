package com.chenapps.TaskIt.core;

public class SubStringIndices implements ISubStringIndices{
	
	private int beginIndex;
	private int endIndex;
	private Enum<?> subStrinType;

	public SubStringIndices(Enum<?> subStrinType,int beginIndex, int endIndex) {
		this.subStrinType = subStrinType;		
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;		
	}

	@Override
	public int getSubstringBeginIndex() {
		return beginIndex;
	}

	@Override
	public int getSubstringEndIndex() {
		return endIndex;
	}

	@Override
	public Enum<?> getSubstringType() {	
		return subStrinType;
	}
	
}