package org.knowm.xchange.bitstamp.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

/** Test Transaction[] JSON parsing */
class CancelAllOrdersJSONTest {

  @Test
  void unmarshal() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        CancelOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/trade/example-cancel-all-orders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampCancelAllOrdersResponse result =
        mapper.readValue(is, BitstampCancelAllOrdersResponse.class);

    assertThat(result.success).isTrue();
  }
}
