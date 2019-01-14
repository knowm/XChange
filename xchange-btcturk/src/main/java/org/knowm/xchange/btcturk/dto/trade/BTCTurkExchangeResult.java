package org.knowm.xchange.btcturk.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author mertguner */
public class BTCTurkExchangeResult {
	
	private final String id;
	private final Date datetime;
	private final String type;
	private final BigDecimal price;
	private final BigDecimal amount;

	  public BTCTurkExchangeResult(
	      @JsonProperty("id") String id,
	      @JsonProperty("datetime") Date datetime,
	      @JsonProperty("type") String type,
	      @JsonProperty("price") BigDecimal price,
	      @JsonProperty("amount") BigDecimal amount
			  ){
	    this.id = id;
	    this.datetime = datetime;
	    this.type = type;
	    this.price = price;
	    this.amount = amount;
	  }

	public String getId() {
		return id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public String getType() {
		return type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "BTCTurkOrderResult [id=" + id + ", datetime=" + datetime + ", type=" + type + ", price=" + price
				+ ", amount=" + amount + "]";
	}

	
}
