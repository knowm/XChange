package org.knowm.xchange.paribu.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author semihunaldi */
public class ParibuTickerTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        ParibuTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/paribu/dto/marketdata/example-ticker-data.json");
    ObjectMapper mapper = new ObjectMapper();
    ParibuTicker paribuTicker = mapper.readValue(is, ParibuTicker.class);

    // Verify that the example data was unmarshalled correctly
    BTC_TL btcTL = paribuTicker.getBtcTL();
    assertThat(btcTL.getLast()).isEqualTo(new BigDecimal("41400"));
    assertThat(btcTL.getLowestAsk()).isEqualTo(new BigDecimal("41450.89"));
    assertThat(btcTL.getHighestBid()).isEqualTo(new BigDecimal("41400"));
    assertThat(btcTL.getPercentChange()).isEqualTo(new BigDecimal("9.81"));
    assertThat(btcTL.getVolume()).isEqualTo(new BigDecimal("2370.89"));
    assertThat(btcTL.getHigh24hr()).isEqualTo(new BigDecimal("42000"));
    assertThat(btcTL.getLow24hr()).isEqualTo(new BigDecimal("37650"));
  }
}
