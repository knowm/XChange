package org.knowm.xchange.bitstamp.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test Transaction[] JSON parsing */
public class PlaceLimitOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        PlaceLimitOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/trade/example-place-limit-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrder newOrder = mapper.readValue(is, BitstampOrder.class);

    assertThat(newOrder.getId()).isEqualTo(1273070);
    assertThat(newOrder.getAmount()).isEqualTo(BigDecimal.ONE);
    assertThat(newOrder.getPrice()).isEqualTo(new BigDecimal("1.25"));
    assertThat(newOrder.getType()).isEqualTo(0);
  }

  @Test
  public void testError() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        PlaceLimitOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/trade/example-place-limit-order-error.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrder response = mapper.readValue(is, BitstampOrder.class);

    assertThat(response.getErrorMessage()).isEqualTo("Minimum order size is $1");
  }
}
