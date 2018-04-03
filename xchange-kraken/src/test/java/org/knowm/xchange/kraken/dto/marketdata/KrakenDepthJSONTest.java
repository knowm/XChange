package org.knowm.xchange.kraken.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenDepthResult;

/** Test KrakenDepth JSON parsing */
public class KrakenDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenDepthResult krakenDepthResult = mapper.readValue(is, KrakenDepthResult.class);
    Map<String, KrakenDepth> krakenDepths = krakenDepthResult.getResult();

    // Verify that the example data was unmarshalled correctly
    assertThat(krakenDepths.get("XXBTXLTC")).isEqualTo(null);
    List<KrakenPublicOrder> krakenAsks = krakenDepths.get("XXBTZEUR").getAsks();
    KrakenPublicOrder krakenPublicOrder = krakenAsks.get(0);

    assertThat(krakenPublicOrder.getPrice()).isEqualTo(new BigDecimal("530.75513"));
    assertThat(krakenPublicOrder.getVolume()).isEqualTo(new BigDecimal("0.248"));
    assertThat(krakenPublicOrder.getTimestamp()).isEqualTo(1391825343L);
  }
}
