package org.knowm.xchange.coindirect.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

public class CoindirectMarketTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoindirectMarketTest.class.getResourceAsStream(
            "/org/knowm/xchange/coindirect/dto/marketdata/example-market-list.json");

    ObjectMapper mapper = new ObjectMapper();
    List<CoindirectMarket> coindirectMarkets =
        mapper.readValue(is, new TypeReference<List<CoindirectMarket>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(coindirectMarkets.size()).isEqualTo(10);

    assertThat(coindirectMarkets.get(1).symbol).isEqualTo("USDT-ZAR");
  }
}
