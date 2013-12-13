package com.xeiam.xchange.cryptsy.service.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyAccountInfoReturn {

	int success;
	CryptsyAccountInfo result;
	String error;

	public CryptsyAccountInfoReturn(@JsonProperty("success") int success, @JsonProperty("return") CryptsyAccountInfo result, @JsonProperty("error") String error) {
		super();
		this.success = success;
		this.result = result;
		this.error = error;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public CryptsyAccountInfo getResult() {
		return result;
	}

	public void setResult(CryptsyAccountInfo result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
