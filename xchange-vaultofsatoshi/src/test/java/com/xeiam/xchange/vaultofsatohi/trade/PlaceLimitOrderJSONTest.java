package com.xeiam.xchange.vaultofsatohi.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosOrderId;
/**
 * Test Transaction[] JSON parsing
 */
public class PlaceLimitOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = PlaceLimitOrderJSONTest.class.getResourceAsStream("/trade/place_order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType type = mapper.getTypeFactory().constructParametricType(VosResponse.class, VosOrderId.class);
    VosResponse<VosOrderId> vaultOfSatoshiPlaceOrder = mapper.readValue(is, type);

    assertThat(vaultOfSatoshiPlaceOrder.getData().getOrder_id()).isEqualTo(2036833);
    assertThat(vaultOfSatoshiPlaceOrder.getStatus()).isEqualTo("success");
  }

  @Test
  public void testError() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = PlaceLimitOrderJSONTest.class.getResourceAsStream("/error/error.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType type = mapper.getTypeFactory().constructParametricType(VosResponse.class, Void.class);
    VosResponse<Void> vaultOfSatoshiError = mapper.readValue(is, type);

    assertThat(vaultOfSatoshiError.getMessage()).isEqualTo("Missing required parameter: payment_currency");
    assertThat(vaultOfSatoshiError.getStatus()).isEqualTo("error");
  }
}
