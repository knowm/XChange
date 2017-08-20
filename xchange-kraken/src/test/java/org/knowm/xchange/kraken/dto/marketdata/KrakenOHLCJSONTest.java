package org.knowm.xchange.kraken.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenOHLCResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KrakenOHLCJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenOHLCJSONTest.class.getResourceAsStream("/marketdata/example-ohlc-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOHLCResult krakenOHLCs = mapper.readValue(is, KrakenOHLCResult.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getTime()).isEqualTo(1502402520L);
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getOpen()).isEqualTo(new BigDecimal("3449.992"));
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getHigh()).isEqualTo(new BigDecimal("3449.999"));
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getLow()).isEqualTo(new BigDecimal("3449.992"));
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getClose()).isEqualTo(new BigDecimal("3449.999"));
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getVwap()).isEqualTo(new BigDecimal("3449.997"));
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getVolume()).isEqualTo(new BigDecimal("1.01200000"));
    assertThat(krakenOHLCs.getResult().getOHLCs().get(0).getCount()).isEqualTo(new Long("7"));
    long lastId = krakenOHLCs.getResult().getLast();
    assertThat(lastId).isEqualTo(1502445600L);
  }
}
