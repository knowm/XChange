package org.knowm.xchange.btcchina.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrders;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;

/**
 * Test Transaction[] JSON parsing
 */
public class PlaceLimitOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = PlaceLimitOrderJSONTest.class.getResourceAsStream("/trade/example-place-limit-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCChinaGetOrdersResponse btcChinaResponse = mapper.readValue(is, BTCChinaGetOrdersResponse.class);
    System.out.println(btcChinaResponse);

    BTCChinaOrders btcChinaOrders = btcChinaResponse.getResult();
    System.out.println(btcChinaOrders.toString());

    assertThat(btcChinaOrders.getOrdersArray()[0].getId()).isEqualTo(4972937);
    assertThat(btcChinaOrders.getOrdersArray()[0].getCurrency()).isEqualTo("CNY");

  }
}
