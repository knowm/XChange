package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTrades extends CryptoFacilitiesResult {

  private final List<CryptoFacilitiesTrade> trades;
	
  
  public CryptoFacilitiesTrades(@JsonProperty("result") String result
		  , @JsonProperty("error") String error
		  , @JsonProperty("orders") List<CryptoFacilitiesTrade> trades) {

	  super(result, error);
	    
	    this.trades = trades;	    
  }

  public List<CryptoFacilitiesTrade> getTrades() {
	  return trades;
  }
  
	@Override
	public String toString() {
		
		String res = "CryptoFacilitiesTrades [trades=";
		for(CryptoFacilitiesTrade trd : trades)
			res = res + trd.toString() + ", ";
		res = res + " ]";
		
		return res;
	}

  

}
