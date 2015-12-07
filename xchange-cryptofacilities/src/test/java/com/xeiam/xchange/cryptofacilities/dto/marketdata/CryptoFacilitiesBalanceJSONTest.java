package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptofacilities.dto.account.CryptoFacilitiesBalance;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesBalanceJSONTest {

	@Test
	public void testUnmarshal() throws IOException {

	    // Read in the JSON from the example resources
	    InputStream is = CryptoFacilitiesBalanceJSONTest.class.getResourceAsStream("/marketdata/example-balance-data.json");

	    // Use Jackson to parse it
	    ObjectMapper mapper = new ObjectMapper();
	    CryptoFacilitiesBalance cryptoFacilitiesBalance = mapper.readValue(is, CryptoFacilitiesBalance.class);
	    
	    // Verify that the example data was unmarshalled correctly
	    assertThat(cryptoFacilitiesBalance.isSuccess()).isTrue();
	    
	    Map<String, BigDecimal> balances = cryptoFacilitiesBalance.getBalances();
	    
	    assertThat(balances.size()).isEqualTo(4);
	    
	    Iterator<Entry<String, BigDecimal>> it = balances.entrySet().iterator();
	    Entry<String, BigDecimal> bal = it.next();
	    
	    assertThat(bal.getKey()).isEqualTo("F-XBT:USD-Dec15");
	    assertThat(bal.getValue()).isEqualTo(new BigDecimal("10.0"));	    
	  }


}
