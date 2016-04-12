package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jean-Christophe Laruelle
 */

@Deprecated
public class CryptoFacilitiesIndexJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoFacilitiesIndexJSONTest.class.getResourceAsStream("/marketdata/example-index-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesIndex cryptoFacilitiesIndex = mapper.readValue(is, CryptoFacilitiesIndex.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesIndex.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesIndex.getCfbpi()).isEqualTo(new BigDecimal("322.48"));
  }

}
