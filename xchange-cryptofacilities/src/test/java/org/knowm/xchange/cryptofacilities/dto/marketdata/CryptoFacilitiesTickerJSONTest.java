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
public class CryptoFacilitiesTickerJSONTest {

  @Test
  public void testUnmarshal1() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoFacilitiesTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data-1.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesTicker cryptoFacilitiesTicker = mapper.readValue(is, CryptoFacilitiesTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesTicker.isSuccess()).isTrue();
    assertThat(cryptoFacilitiesTicker.getBid()).isEqualTo(new BigDecimal("328.5"));
    assertThat(cryptoFacilitiesTicker.getAsk()).isEqualTo(new BigDecimal("330.49"));
    assertThat(cryptoFacilitiesTicker.getLast()).isEqualTo(new BigDecimal("328.66"));
  }

  @Test
  public void testUnmarshal2() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoFacilitiesTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data-2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesTicker cryptoFacilitiesTicker = mapper.readValue(is, CryptoFacilitiesTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesTicker.isSuccess()).isFalse();
    assertThat(cryptoFacilitiesTicker.getBid()).isNull();
    assertThat(cryptoFacilitiesTicker.getAsk()).isNull();
    assertThat(cryptoFacilitiesTicker.getLast()).isNull();
  }

}
