package org.knowm.xchange.bitbns.dto;

import java.util.List;

public class BitbnsOrderStatusResponse {

	private List<Data> data;
	private int status;
	private String error;
	private long id;
	private int code;

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "BitbnsOrderStatusResponse [data=" + data + ", status=" + status + ", error=" + error + ", id=" + id
				+ ", code=" + code + "]";
	}

}
