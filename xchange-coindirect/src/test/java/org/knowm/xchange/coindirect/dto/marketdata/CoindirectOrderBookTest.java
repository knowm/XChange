package org.knowm.xchange.coindirect.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class CoindirectOrderBookTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoindirectOrderBookTest.class.getResourceAsStream(
            "/org/knowm/xchange/coindirect/dto/marketdata/example-order-book.json");

    ObjectMapper mapper = new ObjectMapper();
    CoindirectOrderbook coindirectOrderbook = mapper.readValue(is, CoindirectOrderbook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coindirectOrderbook.bids.size()).isEqualTo(1);
    assertThat(coindirectOrderbook.asks.size()).isEqualTo(3);

    assertThat(coindirectOrderbook.bids.get(0).price).isEqualTo(new BigDecimal("0.06779200"));
  }
}
