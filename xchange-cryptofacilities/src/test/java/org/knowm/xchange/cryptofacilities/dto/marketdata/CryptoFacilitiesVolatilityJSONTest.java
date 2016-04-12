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
public class CryptoFacilitiesVolatilityJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoFacilitiesVolatilityJSONTest.class.getResourceAsStream("/marketdata/example-volatility-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesVolatility cryptoFacilitiesVolatility = mapper.readValue(is, CryptoFacilitiesVolatility.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesVolatility.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesVolatility.getVolatility()).isEqualTo(new BigDecimal("30.03"));
  }

}
