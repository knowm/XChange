package org.knowm.xchange.krakenfutures.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author Panchen */
public class KrakenFuturesSendOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesCancelOrderJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-sendOrder-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesOrder krakenFuturesOrder = mapper.readValue(is, KrakenFuturesOrder.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(krakenFuturesOrder.isSuccess()).isTrue();
    assertThat(krakenFuturesOrder.getOrderStatus().getStatus()).isEqualTo("placed");
    assertThat(krakenFuturesOrder.getOrderId())
        .isEqualTo("c18f0c17-9971-40e6-8e5b-10df05d422f0");

    //    assertThat(krakenFuturesOrder.isSuccess()).isFalse();
    //
    // assertThat(krakenFuturesOrder.getError().equalsIgnoreCase("apiLimitExceeded")).isTrue();
  }
}
