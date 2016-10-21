package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Panchen
 */

public class CryptoFacilitiesCancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoFacilitiesCancelOrderJSONTest.class.getResourceAsStream("/marketdata/example-cancelOrder-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesCancel cryptoFacilitiesCancel = mapper.readValue(is, CryptoFacilitiesCancel.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesCancel.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesCancel.getStatus()).isEqualTo("cancelled");

    //    assertThat(cryptoFacilitiesCancel.isSuccess()).isFalse();
    //    assertThat(cryptoFacilitiesCancel.getError().equalsIgnoreCase("apiLimitExceeded")).isTrue();
  }

}
