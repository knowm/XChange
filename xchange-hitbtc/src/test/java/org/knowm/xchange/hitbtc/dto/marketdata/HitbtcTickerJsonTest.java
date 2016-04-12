package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcTickerJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcTickerJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTicker ticker = mapper.readValue(is, HitbtcTicker.class);

    assertThat(ticker.getAsk()).isEqualTo("347.76");
    assertThat(ticker.getBid()).isEqualTo("347.21");
    assertThat(ticker.getLast()).isEqualTo("347.53");
    assertThat(ticker.getLow()).isEqualTo("341.41");
    assertThat(ticker.getHigh()).isEqualTo("354.66");
    assertThat(ticker.getOpen()).isEqualTo("347.40");
    assertThat(ticker.getVolume()).isEqualTo("462.82");
    assertThat(ticker.getVolumeQuote()).isEqualTo("161005.26");
    assertThat(ticker.getTimestamp()).isEqualTo(1447539208397L);
  }
}
