package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTicker extends CryptoFacilitiesResult {

	private final BigDecimal bid;
	private final BigDecimal ask;
	private final BigDecimal last;
			
	public CryptoFacilitiesTicker(@JsonProperty("result") String result
			, @JsonProperty("error") String error
			, @JsonProperty("bid") BigDecimal bid
			, @JsonProperty("ask") BigDecimal ask
			, @JsonProperty("last") BigDecimal last) {
	
		  super(result, error);
		    
		  this.bid = bid;
		  this.ask = ask;
		  this.last = last;
	}

	public BigDecimal getBid() {
		return bid;
	}
	
	public BigDecimal getAsk() {
		return ask;
	}
	
	public BigDecimal getLast() {
		return last;
	}
	
	@Override
	public String toString() {

	    if(isSuccess())
	    	return "CryptoFacilitiesTicker [bid=" + bid + ", ask=" + ask + ", last=" + last + "]";
	    else
	    	return super.toString();
	}  

}
