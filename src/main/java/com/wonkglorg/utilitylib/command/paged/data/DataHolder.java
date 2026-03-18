package com.wonkglorg.utilitylib.command.paged.data;

import lombok.Getter;

public class DataHolder<T>{
	@Getter
	private T data;
	@Getter
	private long lastModified = System.currentTimeMillis();
	
	public DataHolder(T data) {
		this.data = data;
	}
	
	public void setData(T data) {
		this.data = data;
		lastModified = System.currentTimeMillis();
	}
}
