package org.knowm.xchange.btcturk.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTickerTest;
import com.fasterxml.jackson.databind.ObjectMapper;

/** @author mertguner */
public class BTCTurkExchangeResultTest {
	@Test
	  public void testWithStaticData() throws IOException {

	    // Read in the JSON from the example resources
	    InputStream is =
	        BTCTurkTickerTest.class.getResourceAsStream(
	            "/org/knowm/xchange/btcturk/dto/trade/example-exchange-result-data.json");
	    ObjectMapper mapper = new ObjectMapper();
	    BTCTurkExchangeResult btcTurkExchangeResult = mapper.readValue(is, BTCTurkExchangeResult.class);

	    assertThat(btcTurkExchangeResult.getId()).isEqualTo("24502215");
	    assertThat(btcTurkExchangeResult.getDatetime().toString()).isEqualTo("Mon Jan 14 17:55:31 UTC 2019");
	    assertThat(btcTurkExchangeResult.getType()).isEqualTo(BTCTurkOrderTypes.Sell);
	    assertThat(btcTurkExchangeResult.getPrice()).isEqualTo(new BigDecimal("900"));
	    assertThat(btcTurkExchangeResult.getAmount()).isEqualTo(new BigDecimal("0.0123"));
	  }
	
}
