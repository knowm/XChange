package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesOpenOrders extends CryptoFacilitiesResult {

  private final List<CryptoFacilitiesOpenOrder> orders;
	
  
  public CryptoFacilitiesOpenOrders(@JsonProperty("result") String result
		  , @JsonProperty("error") String error
		  , @JsonProperty("orders") List<CryptoFacilitiesOpenOrder> orders) {

	  super(result, error);
	    
	    this.orders = orders;	    
  }

  public List<CryptoFacilitiesOpenOrder> getOrders() {
	  return orders;
  }
  
	@Override
	public String toString() {
		
		String res = "CryptoFacilitiesOrders [orders=";
		for(CryptoFacilitiesOpenOrder ord : orders)
			res = res + ord.toString() + ", ";
		res = res + " ]";
		
		return res;
	}

  

}
