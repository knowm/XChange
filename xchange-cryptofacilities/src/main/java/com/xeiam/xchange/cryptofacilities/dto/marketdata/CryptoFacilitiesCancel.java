package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesCancel extends CryptoFacilitiesResult {

	public CryptoFacilitiesCancel(@JsonProperty("result") String result
			, @JsonProperty("error") String error) {
	
		  super(result, error);
	}

}
