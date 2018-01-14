package org.xchange.coinegg.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.xchange.coinegg.dto.marketdata.CoinEggTrades;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinEggTradesJSONTest {
	
	@Test
	public void testUnmarshal() throws IOException {
		
		// Read in the JSON from the example resources
	    InputStream is = CoinEggTradesJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

	    // Parse JSON Example Using Jackson
	    ObjectMapper mapper = new ObjectMapper();
	    CoinEggTrades coinEggTrades = mapper.readValue(is, CoinEggTrades.class);
	    
	    // Verify The Trades Unmarshalls Correctly
	    assertThat(coinEggTrades).isNotNull();
	    
	    assertThat(coinEggTrades.getAsks()).isNotNull();
	    assertThat(coinEggTrades.getAsks()).isNotEmpty();
	    assertThat(coinEggTrades.getAsks().length).isEqualTo(31);
      
	    assertThat(coinEggTrades.getAsks()[0].getPrice()).isEqualTo(new BigDecimal("70000"));
      assertThat(coinEggTrades.getAsks()[0].getQuantity()).isEqualTo(new BigDecimal("5"));
	    
	    assertThat(coinEggTrades.getBids()).isNotNull();
      assertThat(coinEggTrades.getBids()).isNotEmpty();
      assertThat(coinEggTrades.getBids().length).isEqualTo(32);
      
      assertThat(coinEggTrades.getBids()[0].getPrice()).isEqualTo(new BigDecimal("38300"));
      assertThat(coinEggTrades.getBids()[0].getQuantity()).isEqualTo(new BigDecimal("1.879"));
	    
	  }
}
