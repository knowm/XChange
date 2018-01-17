package org.xchange.coinegg.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.xchange.coinegg.dto.marketdata.CoinEggOrder;
import org.xchange.coinegg.dto.marketdata.CoinEggOrder.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinEggOrderJSONTest {
	
	@Test
	public void testUnmarshal() throws IOException {
		
		// Read in the JSON from the example resources
	    InputStream is = CoinEggOrderJSONTest.class.getResourceAsStream("/marketdata/example-orders-data.json");

	    // Parse JSON Example Using Jackson
	    ObjectMapper mapper = new ObjectMapper();
	    CoinEggOrder[] coinEggOrders = mapper.readValue(is, CoinEggOrder[].class);
	    
	    // Verify The Ticker Unmarshalls Correctly
	    assertThat(coinEggOrders).isNotNull();
	    
	    // Verify Buy Order
	    assertThat(coinEggOrders[0].getTimestamp()).isEqualTo(0);
	    assertThat(coinEggOrders[0].getPrice()).isEqualTo(new BigDecimal("3"));
	    assertThat(coinEggOrders[0].getAmount()).isEqualTo(new BigDecimal("0.1"));    
	    assertThat(coinEggOrders[0].getTransactionID()).isEqualTo(1);
	    assertThat(coinEggOrders[0].getType()).isEqualTo(Type.BUY);
	        
	    // Verify Sell Order
	    assertThat(coinEggOrders[1].getTimestamp()).isEqualTo(0);
      assertThat(coinEggOrders[1].getPrice()).isEqualTo(new BigDecimal("32323"));
      assertThat(coinEggOrders[1].getAmount()).isEqualTo(new BigDecimal("2"));    
      assertThat(coinEggOrders[1].getTransactionID()).isEqualTo(2);
      assertThat(coinEggOrders[1].getType()).isEqualTo(Type.SELL);
	  }
}
