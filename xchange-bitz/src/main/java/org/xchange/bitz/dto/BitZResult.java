package org.xchange.bitz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZResult<V> {
	
	private final V data;
	private final int code;
	private final String message;
	
	public BitZResult(@JsonProperty("code") int code, @JsonProperty("msg") String message, @JsonProperty("data") V data) {
		// TODO: Some Validation - See GatecoinResult.java
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public V getData() {
		return data;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
	  return String.format("BitZResult [code=%d, message=%s, data=%s]", getCode(), getMessage(), getData().toString());
	}
}
