package org.xchange.coinegg.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.xchange.coinegg.dto.CoinEggResult;
import org.xchange.coinegg.dto.accounts.CoinEggBalance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinEggBalanceJSONTest {
	
	@Test
	public void testUnmarshal() throws IOException {
		
		// Read in the JSON from the example resources
	    InputStream is = CoinEggBalanceJSONTest.class.getResourceAsStream("/account/example-balance-data.json");

	    // Parse JSON Example Using Jackson
	    ObjectMapper mapper = new ObjectMapper();
	    CoinEggResult<CoinEggBalance> coinEggResult = mapper.readValue(is, new TypeReference<CoinEggResult<CoinEggBalance>>() {});
	    CoinEggBalance coinEggBalance = coinEggResult.getData();
	    
	    // Verify The Ticker Unmarshalls Correctly
	    assertThat(coinEggResult).isNotNull();
	    assertThat(coinEggBalance).isNotNull();
	    
	    assertThat(coinEggBalance.getID()).isEqualTo(1);
	    
	    assertThat(coinEggBalance.isXASLocked()).isEqualTo(false);
	    assertThat(coinEggBalance.isETHLocked()).isEqualTo(false);
	    assertThat(coinEggBalance.isBTCLocked()).isEqualTo(false);
	    
	    assertThat(coinEggBalance.getXASBalance()).isEqualTo(new BigDecimal("1"));
	    assertThat(coinEggBalance.getETHBalance()).isEqualTo(new BigDecimal("1"));
	    assertThat(coinEggBalance.getBTCBalance()).isEqualTo(new BigDecimal("1"));
	    
	    
	    
	  }
}
