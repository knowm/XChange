package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesIndex extends CryptoFacilitiesResult {

	private final BigDecimal cfbpi;
		
	public CryptoFacilitiesIndex(@JsonProperty("result") String result
			, @JsonProperty("error") String error
			, @JsonProperty("cf-bpi") BigDecimal cfbpi) {
	
		  super(result, error);
		    
		  this.cfbpi = cfbpi;
	}
	  
	public BigDecimal getCfbpi() {
		return cfbpi;
	}

	@Override
	public String toString() {

	    if(isSuccess())
	    	return "CryptoFacilitiesIndex [cfbpi=" + cfbpi  + "]";
	    else
	    	return super.toString();
	}  

}
