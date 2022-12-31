package org.knowm.xchange.krakenfutures.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author Panchen */
public class KrakenFuturesCancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesCancelOrderJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-cancelOrder-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesCancel cryptoFacilitiesCancel =
        mapper.readValue(is, KrakenFuturesCancel.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesCancel.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesCancel.getCancelStatus().getStatus()).isEqualTo("cancelled");

    //    assertThat(cryptoFacilitiesCancel.isSuccess()).isFalse();
    //
    // assertThat(cryptoFacilitiesCancel.getError().equalsIgnoreCase("apiLimitExceeded")).isTrue();
  }
}
