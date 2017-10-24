package org.knowm.xchange.taurus.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    TaurusOrder newOrder = mapper.readValue(is, TaurusOrder.class);

    assertThat(newOrder.getId()).isEqualTo("musi0joa54mzpj0vvpo811mr53g6cj4zewieg7plccl2wlxrbm0cnm3tqkz3343i");
    assertThat(newOrder.getAmount()).isEqualTo(new BigDecimal("0.01000000"));
    assertThat(newOrder.getPrice()).isEqualTo(new BigDecimal("400.00"));
    assertThat(newOrder.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(newOrder.getBook()).isEqualTo(CurrencyPair.BTC_CAD);
  }

  @Test
  public void testError() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = PlaceLimitOrderJSONTest.class.getResourceAsStream("/trade/example-place-limit-order-error.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.readValue(is, TaurusOrder.class);
      assertThat(false).isTrue();
    } catch (JsonMappingException ignore) {
    }
  }
}
