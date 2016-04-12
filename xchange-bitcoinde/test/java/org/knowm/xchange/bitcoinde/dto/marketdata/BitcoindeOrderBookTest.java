package org.knowm.xchange.bitcoinde.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralTrade;

/**
 * @author matthewdowney
 */
public class BitcoindeOrderBookTest {

	@Test
	public void testBitcoindeOrderBook() throws JsonParseException, JsonMappingException, IOException {

		// Read in the JSON from the example resources
		InputStream is = MarketDataJSONTest.class.getResourceAsStream("/orderbook.json");

		// Use Jackson to parse it
		ObjectMapper mapper = new ObjectMapper();
		BitcoindeOrderBook bitcoindeOrderBook = mapper.readValue(is, BitcoindeOrderBook.class);

		// Make sure it returns what we expect
		assertEquals(bitcoindeOrderBook.getAsks[0][0], new BigDecimal("335"));
		assertEquals(bitcoindeOrderBook.getAsks[0][1], new BigDecimal("3.98"));

		System.out.println(bitcoindeOrderBook.getAsks[0][1]);	

		assertEquals(bitcoindeOrderBook.getBids[0][0], new BigDecimal("200"));
		assertEquals(bitcoindeOrderBook.getBids[0][1], new BigDecimal("10"));
	}
}
