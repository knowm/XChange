package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author Panchen */
public class CryptoFacilitiesSendOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptoFacilitiesCancelOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptofacilities/dto/marketdata/example-sendOrder-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesOrder cryptoFacilitiesOrder = mapper.readValue(is, CryptoFacilitiesOrder.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesOrder.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesOrder.getStatus()).isEqualTo("placed");
    assertThat(cryptoFacilitiesOrder.getOrderId())
        .isEqualTo("c18f0c17-9971-40e6-8e5b-10df05d422f0");

    //    assertThat(cryptoFacilitiesOrder.isSuccess()).isFalse();
    //
    // assertThat(cryptoFacilitiesOrder.getError().equalsIgnoreCase("apiLimitExceeded")).isTrue();
  }
}
