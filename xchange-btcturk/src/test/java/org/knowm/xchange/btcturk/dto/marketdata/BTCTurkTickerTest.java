package org.knowm.xchange.btcturk.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.btcturk.BTCTurk;
import org.knowm.xchange.currency.CurrencyPair;

/** Created by semihunaldi on 26/11/2017 */
public class BTCTurkTickerTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-ticker-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkTicker btcTurkTicker = mapper.readValue(is, BTCTurkTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(btcTurkTicker.getPair()).isEqualTo(new BTCTurk.Pair(CurrencyPair.BTC_TRY));
    assertThat(btcTurkTicker.getHigh()).isEqualTo(new BigDecimal("39436.99"));
    assertThat(btcTurkTicker.getLast()).isEqualTo(new BigDecimal("38800.02"));
    assertThat(btcTurkTicker.getTimestamp()).isEqualTo(1511724574L);
    assertThat(btcTurkTicker.getBid()).isEqualTo(new BigDecimal("38800.02"));
    assertThat(btcTurkTicker.getVolume()).isEqualTo(new BigDecimal("850.97"));
    assertThat(btcTurkTicker.getLow()).isEqualTo(new BigDecimal("35501.0"));
    assertThat(btcTurkTicker.getAsk()).isEqualTo(new BigDecimal("38800.02"));
    assertThat(btcTurkTicker.getOpen()).isEqualTo(new BigDecimal("35599.0"));
    assertThat(btcTurkTicker.getAverage()).isEqualTo(new BigDecimal("37909.3"));
  }
}
