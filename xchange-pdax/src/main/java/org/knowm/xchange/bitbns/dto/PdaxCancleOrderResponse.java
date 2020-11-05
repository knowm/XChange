package org.knowm.xchange.bitbns.dto;

public class PdaxCancleOrderResponse {

	private PdaxCancleOrderDetail data;

	public PdaxCancleOrderResponse(PdaxCancleOrderDetail data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PdaxCancleOrderResponse [data=" + data + "]";
	}

	public PdaxCancleOrderDetail getData() {
		return data;
	}

	public void setData(PdaxCancleOrderDetail data) {
		this.data = data;
	}

}
