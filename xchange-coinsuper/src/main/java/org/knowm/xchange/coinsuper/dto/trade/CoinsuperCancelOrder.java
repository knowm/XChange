package org.knowm.xchange.coinsuper.dto.trade;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class CoinsuperCancelOrder {
	@JsonProperty("operate")
	private String operate;

	
	public CoinsuperCancelOrder() {
	}
	
	
	@JsonProperty("operate")
	public String getOperate() {
	return operate;
	}

	@JsonProperty("operate")
	public void setOperate(String operate) {
	this.operate = operate;
	}

	@Override
	public String toString() {
	return new ToStringBuilder(this).append("operate", operate).toString();
	}

}