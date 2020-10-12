package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.vavr.collection.List;

public class BItbnsOrderBooks {

	private List<BitbnsOrderBook> data;
	private int status;
	private String error;
	private int code;

	//	
	
	public BItbnsOrderBooks(@JsonProperty("data")List<BitbnsOrderBook> data,
			@JsonProperty("status")int status,
			@JsonProperty("error")String error,
			@JsonProperty("code")int code) {
	this.data=data;
	this.status=status;
	this.error=error;
	this.code=code;
	}

	public List<BitbnsOrderBook> getData() {
		return data;
	}

	public void setData(List<BitbnsOrderBook> data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "BItbnsOrderBooks [data=" + data + ", status=" + status + ", error=" + error + ", code=" + code + "]";
	}

}
