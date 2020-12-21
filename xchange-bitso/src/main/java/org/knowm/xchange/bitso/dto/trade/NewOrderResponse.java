package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewOrderResponse {

	private String oid;

	public NewOrderResponse(@JsonProperty("oid") String oid) {
		this.oid = oid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Override
	public String toString() {
		return "NewOrderResponse [oid=" + oid + "]";
	}

}
