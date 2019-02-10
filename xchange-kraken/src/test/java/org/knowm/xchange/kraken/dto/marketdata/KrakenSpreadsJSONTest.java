package org.knowm.xchange.kraken.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenSpreadsResult;

public class KrakenSpreadsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenSpreadsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/marketdata/example-spreads-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenSpreadsResult krakenResult = mapper.readValue(is, KrakenSpreadsResult.class);
    KrakenSpreads spreads = krakenResult.getResult();

    assertThat(spreads.getLast()).isEqualTo(1391837200);
    KrakenSpread spread = spreads.getSpreads().get(0);
    assertThat(spread.getAsk()).isEqualTo("720.00000");
    assertThat(spread.getBid()).isEqualTo("709.17169");
    assertThat(spread.getTime()).isEqualTo(1391836639);
  }
}
