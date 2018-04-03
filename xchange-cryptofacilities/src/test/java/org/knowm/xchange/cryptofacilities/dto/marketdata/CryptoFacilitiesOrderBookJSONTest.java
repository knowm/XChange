package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesOrderBookJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptoFacilitiesOrderBookJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptofacilities/dto/marketdata/example-orderBook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesOrderBook cryptoFacilitiesOrderBook =
        mapper.readValue(is, CryptoFacilitiesOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesOrderBook.isSuccess()).isTrue();

    List<List<BigDecimal>> asks = cryptoFacilitiesOrderBook.getAsks();
    assertThat(asks.size()).isEqualTo(28);
    assertThat(asks.get(0).get(0)).isEqualTo(new BigDecimal("644.74"));
    assertThat(asks.get(0).get(1)).isEqualTo(new BigDecimal("8"));

    List<List<BigDecimal>> bids = cryptoFacilitiesOrderBook.getBids();
    assertThat(bids.size()).isEqualTo(23);
    assertThat(bids.get(0).get(0)).isEqualTo(new BigDecimal("643.92"));
    assertThat(bids.get(0).get(1)).isEqualTo(new BigDecimal("9"));
  }
}
