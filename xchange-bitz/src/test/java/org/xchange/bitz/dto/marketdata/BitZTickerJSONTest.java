package org.xchange.bitz.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.xchange.bitz.dto.marketdata.result.BitZKlineResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitZTickerJSONTest {
	
	@Test
	public void testUnmarshal() throws IOException {
		
		// Read in the JSON from the example resources
	    InputStream is = BitZTickerJSONTest.class.getResourceAsStream("/marketdata/example-kline-data.json");

	    // Parse JSON Example Using Jackson
	    ObjectMapper mapper = new ObjectMapper();
	    BitZKlineResult bitzKlineResult = mapper.readValue(is, BitZKlineResult.class);
	    BitZKline bitzKline = bitzKlineResult.getData();
	    
	    // Verify The Ticker Result Unmarshalls Correctly
	    assertThat(bitzKlineResult.getCode()).isEqualTo(0);
	    assertThat(bitzKlineResult.getMessage()).isEqualTo("Success");
	    assertThat(bitzKlineResult.getData()).isNotNull();
	    
	    // Verify The Ticker Unmarshalls Correctly
	  }
}
