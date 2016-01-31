package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesCumulativeBidAskJSONTest {

	@Test
	public void testUnmarshal() throws IOException {

	    // Read in the JSON from the example resources
	    InputStream is = CryptoFacilitiesCumulativeBidAskJSONTest.class.getResourceAsStream("/marketdata/example-cumulativebidask-data.json");

	    // Use Jackson to parse it
	    ObjectMapper mapper = new ObjectMapper();
	    CryptoFacilitiesCumulativeBidAsk cryptoFacilitiesCumulativeBidAsk = mapper.readValue(is, CryptoFacilitiesCumulativeBidAsk.class);
	    
	    // Verify that the example data was unmarshalled correctly
	    assertThat(cryptoFacilitiesCumulativeBidAsk.isSuccess()).isTrue();
	    
	    assertThat(cryptoFacilitiesCumulativeBidAsk.getCumulatedBids().get(0).getPrice()).isEqualTo(new BigDecimal("212.00"));
	    assertThat(cryptoFacilitiesCumulativeBidAsk.getCumulatedBids().get(0).getQuantity()).isEqualTo(new BigDecimal("107"));

	    assertThat(cryptoFacilitiesCumulativeBidAsk.getCumulatedAsks().get(0).getPrice()).isEqualTo(new BigDecimal("327.52"));
	    assertThat(cryptoFacilitiesCumulativeBidAsk.getCumulatedAsks().get(0).getQuantity()).isEqualTo(new BigDecimal("7"));
	  }


}
