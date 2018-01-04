package org.xchange.bitz.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.xchange.bitz.dto.marketdata.result.BitZTickerResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitZKlineJSONTest {
	
	@Test
	public void testUnmarshal() throws IOException {
		
		// Read in the JSON from the example resources
	    InputStream is = BitZKlineJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

	    // Parse JSON Example Using Jackson
	    ObjectMapper mapper = new ObjectMapper();
	    BitZTickerResult bitzTickerResult = mapper.readValue(is, BitZTickerResult.class);
	    BitZTicker bitzTicker = bitzTickerResult.getData();
	    
	    // Verify The Ticker Result Unmarshalls Correctly
	    assertThat(bitzTickerResult.getCode()).isEqualTo(0);
	    assertThat(bitzTickerResult.getMessage()).isEqualTo("Success");
	    assertThat(bitzTickerResult.getData()).isNotNull();
	    
	    // Verify The Ticker Unmarshalls Correctly
	    assertThat(bitzTicker.getTimestamp()).isEqualTo(1515076017L);
	    assertThat(bitzTicker.getLast()).isEqualTo(new BigDecimal("0.00098657"));
	    assertThat(bitzTicker.getBuy()).isEqualTo(new BigDecimal("0.00092992"));
	    assertThat(bitzTicker.getSell()).isEqualTo(new BigDecimal("0.00098657"));
	    assertThat(bitzTicker.getHigh()).isEqualTo(new BigDecimal("0.00100000"));
	    assertThat(bitzTicker.getLow()).isEqualTo(new BigDecimal("0.00089504"));
	    assertThat(bitzTicker.getVolume()).isEqualTo(new BigDecimal("15262.8785"));
	  }
}
