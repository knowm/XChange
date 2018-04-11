package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author Panchen */
public class CryptoFacilitiesCancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptoFacilitiesCancelOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptofacilities/dto/marketdata/example-cancelOrder-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesCancel cryptoFacilitiesCancel =
        mapper.readValue(is, CryptoFacilitiesCancel.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesCancel.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesCancel.getStatus()).isEqualTo("cancelled");

    //    assertThat(cryptoFacilitiesCancel.isSuccess()).isFalse();
    //
    // assertThat(cryptoFacilitiesCancel.getError().equalsIgnoreCase("apiLimitExceeded")).isTrue();
  }
}
