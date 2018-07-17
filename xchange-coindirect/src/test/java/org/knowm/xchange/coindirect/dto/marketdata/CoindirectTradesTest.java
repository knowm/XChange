package org.knowm.xchange.coindirect.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoindirectTradesTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoindirectTradesTest.class.getResourceAsStream(
            "/org/knowm/xchange/coindirect/dto/marketdata/example-trade-history.json");

    ObjectMapper mapper = new ObjectMapper();

    CoindirectTrades coindirectTrades = mapper.readValue(is, CoindirectTrades.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coindirectTrades.data.size()).isEqualTo(2);
    assertThat(coindirectTrades.metaData.market).isEqualTo("ETH-BTC");
  }
}
