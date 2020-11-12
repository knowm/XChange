package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxCancleOrderDetail {

	private long orderId;
	private String status;

	public PdaxCancleOrderDetail(@JsonProperty("order_id") long orderId, @JsonProperty("status") String status) {
		this.orderId = orderId;
		this.status = status;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PdaxCancleOrderDetail [orderId=" + orderId + ", status=" + status + "]";
	}

}
