package org.xchange.bitz;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.xchange.bitz.dto.marketdata.result.BitZOrdersResult;
import org.xchange.bitz.dto.marketdata.result.BitZTradesResult;
import org.xchange.bitz.dto.marketdata.result.BitZTickerResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitZAdapterTest {
	
	@Test
	public void testTickerAdapter() throws IOException {

	    // Read in the JSON from the example resources
		InputStream is = BitZAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");
		
	    // Use Jackson to parse it
	    ObjectMapper mapper = new ObjectMapper();
	    BitZTickerResult bitZTickerResult = mapper.readValue(is, BitZTickerResult.class);
	    
	    // Use Adapter To Get Generic Ticker
	    // TODO: Implement Actual Currency MZC
	    Ticker ticker = BitZAdapters.adaptTicker(bitZTickerResult.getData(), CurrencyPair.BTC_LTC);
	    
	    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1515076017L);
	    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("0.00098657"));
	    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("0.00092992"));
	    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("0.00098657"));
	    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("0.00100000"));
	    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("0.00089504"));
	    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("15262.8785"));  
	}
	
	// TODO: Implement Test
	@Test
	public void testOrdersAdapter() throws IOException {
		// Read in the JSON from the example resources
		InputStream is = BitZAdapterTest.class.getResourceAsStream("/marketdata/example-depth-data.json");
				
		// Use Jackson to parse it
		ObjectMapper mapper = new ObjectMapper();
		BitZOrdersResult bitZOrdersResult = mapper.readValue(is, BitZOrdersResult.class);
		
		// Use Adapter To Get Generic
		// TODO: Implement Actual Currency MZC
		OrderBook book = BitZAdapters.adaptOrders(bitZOrdersResult.getData(), CurrencyPair.BTC_LTC);
		
		// Verify Orderbook
		assertThat(book).isNotNull();
		assertThat(book.getAsks()).isNotEmpty();
		assertThat(book.getBids()).isNotEmpty();
		assertThat(book.getTimeStamp().getTime()).isEqualTo(1515076082L);

		// TODO: Deeper Test Cases
	}
	
	// TODO: Implement Test
	@Test
	public void testTradesAdapter() throws IOException {
		// Read in the JSON from the example resources
		InputStream is = BitZAdapterTest.class.getResourceAsStream("/marketdata/example-orders-data.json");
				
		// Use Jackson to parse it
		ObjectMapper mapper = new ObjectMapper();
		BitZTradesResult bitZTradesResult = mapper.readValue(is, BitZTradesResult.class);
			    
		// Use Adapter To Get Generic Ticker
		// TODO: Implement Actual Currency MZC
		Trades trades = BitZAdapters.adaptTrades(bitZTradesResult.getData(), CurrencyPair.BTC_LTC);
		
		// TODO: More Tests
		assertThat(trades.getlastID()).isNotNull();
		assertThat(trades.getTrades()).isNotEmpty();
	}
}
