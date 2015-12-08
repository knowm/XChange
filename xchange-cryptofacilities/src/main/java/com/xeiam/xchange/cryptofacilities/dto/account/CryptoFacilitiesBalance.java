package com.xeiam.xchange.cryptofacilities.dto.account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesBalance extends CryptoFacilitiesResult {

	private final Map<String, BigDecimal> balances;
	
	@JsonCreator
	public CryptoFacilitiesBalance(Map<String, Object> data) {

		super(data.get("result").toString(), data.get("error") != null ? ((String) data.get("error")) : null);
		
		this.balances = new HashMap<String, BigDecimal>();
		for(Entry<String, Object> dt : data.entrySet())
		{
			if(!dt.getKey().equals("result") && !dt.getKey().equals("error"))
			{
				balances.put(dt.getKey(), new BigDecimal(dt.getValue().toString()));
			}
		}			
	}

	public Map<String, BigDecimal> getBalances() {
		return balances;
	}
	
	

}