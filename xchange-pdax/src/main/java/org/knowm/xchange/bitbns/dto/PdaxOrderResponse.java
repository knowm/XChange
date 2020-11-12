package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxOrderResponse {

	private long orderId;
	private long timestamp;
	private String resultCode;
	private String orderState;

	public PdaxOrderResponse(@JsonProperty("order_id") long orderId, @JsonProperty("timestamp") long timestamp,
			@JsonProperty("result_code") String resultCode, @JsonProperty("order_state") String orderState) {

		this.orderId=orderId;
		this.timestamp=timestamp;
		this.resultCode=resultCode;
		this.orderState=orderState;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	@Override
	public String toString() {
		return "PdaxOrderResponse [orderId=" + orderId + ", timestamp=" + timestamp + ", resultCode=" + resultCode
				+ ", orderState=" + orderState + "]";
	}

}
