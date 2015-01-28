package com.xeiam.xchange.lakebtc.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCCancelResponse;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCCancelJsonTest {

  @Test
  public void testDeserializeBuyOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = LakeBTCPlaceOrderJsonTest.class.getResourceAsStream("/trade/example-cancel-order-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    LakeBTCCancelResponse order = mapper.readValue(is, LakeBTCCancelResponse.class);

    assertThat(order.getResult()).isEqualTo("true");

  }
}
