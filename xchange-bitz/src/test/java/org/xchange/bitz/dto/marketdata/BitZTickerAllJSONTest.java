package org.xchange.bitz.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.xchange.bitz.dto.marketdata.result.BitZTickerAllResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitZTickerAllJSONTest {
	
	@Test
	public void testUnmarshal() throws IOException {
		
		// Read in the JSON from the example resources
	    InputStream is = BitZTickerAllJSONTest.class.getResourceAsStream("/marketdata/example-ticker-all-data.json");

	    // Parse JSON Example Using Jackson
	    ObjectMapper mapper = new ObjectMapper();
	    BitZTickerAllResult bitzTickerAllResult = mapper.readValue(is, BitZTickerAllResult.class);
	    BitZTickerAll bitzTickerAll = bitzTickerAllResult.getData();
	    
	    // Verify The Ticker Result Unmarshalls Correctly
	    assertThat(bitzTickerAllResult.getCode()).isEqualTo(0);
	    assertThat(bitzTickerAllResult.getMessage()).isEqualTo("Success");
	    assertThat(bitzTickerAllResult.getData()).isNotNull();
	    
	    // Verify The Ticker Unmarshalls Correctly
	    assertThat(bitzTickerAll.getAllTickers().size()).isEqualTo(50);
	    
	    // TODO: Deeper Testing
	    assertThat(bitzTickerAll.getTicker("ltc_btc")).isNotNull();
	    assertThat(bitzTickerAll.getTicker("ltc_btc").getTimestamp()).isEqualTo(1515076229L); 
	  }
}
