package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesCumulativeBidAsk extends CryptoFacilitiesResult {
		
	private final String cumulatedBids;
	private final String cumulatedAsks;	
		
	public CryptoFacilitiesCumulativeBidAsk(@JsonProperty("result") String result
			, @JsonProperty("error") String error
			, @JsonProperty("cumulatedBids") String cumulatedBids
			, @JsonProperty("cumulatedAsks") String cumulatedAsks
			) throws ParseException {
	
		  super(result, error);

		  this.cumulatedBids = cumulatedBids;
		  this.cumulatedAsks = cumulatedAsks;
	}


	
	@Override
	public String toString() {	
		return "CryptoFacilitiesCumulativeBidAsk [cumulatedBids=" + cumulatedBids 
			+ ", cumulatedAsks=" + cumulatedAsks 
			+" ]";
	}
	
	private List<CryptoFacilitiesCumulatedBidAsk> parseCumulatedString(String strCumulated) throws JsonProcessingException, IOException
	{
		List<CryptoFacilitiesCumulatedBidAsk> res = new LinkedList<CryptoFacilitiesCumulatedBidAsk>();
		
		String jsonString = "{ \"table\" : " + strCumulated + " }";
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(jsonString);
		JsonNode tableNode = root.get("table");
		int size = tableNode.size();
		for(int i=0; i<size; i++)
		{			
			BigDecimal price = new BigDecimal(tableNode.get(i).get(0).asText()).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal qty = new BigDecimal(tableNode.get(i).get(1).asText()).setScale(0, RoundingMode.HALF_EVEN);
			
			res.add(new CryptoFacilitiesCumulatedBidAsk(price, qty));
		}

		return res;
	}
	
	public List<CryptoFacilitiesCumulatedBidAsk> getCumulatedBids() throws JsonProcessingException, IOException
	{
		return parseCumulatedString(this.cumulatedBids);
	}
	
	public List<CryptoFacilitiesCumulatedBidAsk> getCumulatedAsks() throws JsonProcessingException, IOException
	{
		return parseCumulatedString(this.cumulatedAsks);
	}

}
