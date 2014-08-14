package com.xeiam.xchange.vaultofsatoshi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Michael Lagacé
 */

public final class VosOrderId {

	private int order_id;

	public VosOrderId(@JsonProperty("order_id") int order_id) {

		this.order_id = order_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	@Override
	public String toString() {
		return "VosOrderId [order_id=" + order_id + "]";
	}

}
