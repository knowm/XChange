package com.xeiam.xchange.gatecoin.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;


public class CancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CancelOrderJSONTest.class.getResourceAsStream("/trade/example-cancel-order-pass.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinCancelOrderResult result = mapper.readValue(is, GatecoinCancelOrderResult.class);

    assertThat(result.getResponseStatus().getMessage()).isEqualTo("OK");
    
     // Read in the JSON from the example resources
    is = CancelOrderJSONTest.class.getResourceAsStream("/trade/example-cancel-order-fail.json");

    // Use Jackson to parse it
    mapper = new ObjectMapper();
    result = mapper.readValue(is, GatecoinCancelOrderResult.class);

    assertThat(result.getResponseStatus().getMessage()).isEqualTo("Cancel order rejected");
     assertThat(result.getResponseStatus().getErrorCode()).isEqualTo("1010");
    
    
  }
}
