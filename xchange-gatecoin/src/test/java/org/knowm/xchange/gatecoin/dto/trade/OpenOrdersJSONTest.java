package org.knowm.xchange.gatecoin.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = OpenOrdersJSONTest.class.getResourceAsStream("/trade/example-openorders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinOrderResult ordersResult = mapper.readValue(is, GatecoinOrderResult.class);

    assertThat(ordersResult.getOrders().length).isEqualTo(1);

    // Verify that the example data was unmarshalled correctly
    assertThat(ordersResult.getOrders()[0].getClOrderId()).isEqualTo("BK11431862176");
    assertThat(ordersResult.getOrders()[0].getPrice()).isEqualTo(new BigDecimal("500"));
    assertThat(ordersResult.getOrders()[0].getInitialQuantity()).isEqualTo(new BigDecimal("1"));

  }
}
