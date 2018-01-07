package org.xchange.bitz.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.xchange.bitz.dto.marketdata.result.BitZOrdersResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitZDepthJSONTest {
	
	@Test
	public void testUnmarshal() throws IOException {
		
		// Read in the JSON from the example resources
	    InputStream is = BitZDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

	    // Parse JSON Example Using Jackson
	    ObjectMapper mapper = new ObjectMapper();
	    BitZOrdersResult bitzDepthResult = mapper.readValue(is, BitZOrdersResult.class);
	    BitZOrders bitzDepth = bitzDepthResult.getData();
	    
	    // Verify The Depth Result Unmarshalls Correctly
	    assertThat(bitzDepthResult.getCode()).isEqualTo(0);
	    assertThat(bitzDepthResult.getMessage()).isEqualTo("Success");
	    assertThat(bitzDepthResult.getData()).isNotNull();
	    
	    // Verify The Depth Unmarshalls Correctly
	    assertThat(bitzDepth.getTimestamp()).isEqualTo(1515076082L);
	    assertThat(bitzDepth.getAsks().length).isEqualTo(30);
	    assertThat(bitzDepth.getBids().length).isEqualTo(30);
	    
	    // TODO: Deeper Test Case
	    assertThat(bitzDepth.getAsks()[0].getPrice()).isEqualTo(new BigDecimal("0.01585600"));
	    assertThat(bitzDepth.getAsks()[0].getVolume()).isEqualTo(new BigDecimal("0.4771"));
	  }
}
