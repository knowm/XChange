package org.knowm.xchange.gatecoin.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;

/** Test Transaction[] JSON parsing */
public class PlaceOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        PlaceOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/trade/example-place-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinPlaceOrderResult newOrder = mapper.readValue(is, GatecoinPlaceOrderResult.class);

    assertThat(newOrder.getOrderId()).isEqualTo("BK11432031302");
    assertThat(newOrder.getResponseStatus().getMessage()).isEqualTo("OK");
  }
}
