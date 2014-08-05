package com.xeiam.xchange.vaultofsatohi.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosTradeOrder;

/**
 * Test OpenOrders JSON parsing (same JSON as User Transactions)
 */
public class OpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resource
    InputStream is = OpenOrdersJSONTest.class.getResourceAsStream("/trade/open_orders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType type = mapper.getTypeFactory().constructParametricType(List.class, VosTradeOrder.class);
    JavaType type2 = mapper.getTypeFactory().constructParametricType(VosResponse.class, type);
    VosResponse<List<VosTradeOrder>> vaultOfSatoshiOrders = mapper.readValue(is, type2);

    assertThat(vaultOfSatoshiOrders.getData().size()).isEqualTo(2);

    // Verify that the example data was unmarshalled correctly
    assertThat(vaultOfSatoshiOrders.getData().get(0).getPrice().getValue()).isEqualTo(new BigDecimal("0.02540000"));
    assertThat(vaultOfSatoshiOrders.getData().get(0).getUnits().getValue()).isEqualTo(new BigDecimal("0.02000000"));
    assertThat(vaultOfSatoshiOrders.getData().get(0).getPayment_currency()).isEqualTo("CAD");
    assertThat(vaultOfSatoshiOrders.getData().get(0).getOrder_currency()).isEqualTo("LTC");
    assertThat(vaultOfSatoshiOrders.getData().get(0).getType()).isEqualTo("bid");

  }
}
