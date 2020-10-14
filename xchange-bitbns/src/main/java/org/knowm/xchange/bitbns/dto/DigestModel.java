package org.knowm.xchange.bitbns.dto;

public class DigestModel {
	String timeStamp_nonce;
	String body;

	public String getTimeStamp_nonce() {
		return timeStamp_nonce;
	}

	public void setTimeStamp_nonce(String timeStamp_nonce) {
		this.timeStamp_nonce = timeStamp_nonce;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "DigestModel [timeStamp_nonce=" + timeStamp_nonce + ", body=" + body + "]";
	}

}
