package org.knowm.xchange.bitstamp.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** Test Transaction[] JSON parsing */
public class CancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CancelOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/trade/example-cancel-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Boolean result = mapper.readValue(is, Boolean.class);

    assertThat(result).isTrue();
  }
}
