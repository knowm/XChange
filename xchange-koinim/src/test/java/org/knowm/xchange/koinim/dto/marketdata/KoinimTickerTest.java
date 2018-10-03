package org.knowm.xchange.koinim.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author ahmetoz */
public class KoinimTickerTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KoinimTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/koinim/dto/marketdata/example-ticker-data.json");
    ObjectMapper mapper = new ObjectMapper();
    KoinimTicker koinimTicker = mapper.readValue(is, KoinimTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(koinimTicker.getSell()).isEqualTo(new BigDecimal("63500.02000000"));
    assertThat(koinimTicker.getHigh()).isEqualTo(new BigDecimal("64980.00000000"));
    assertThat(koinimTicker.getBuy()).isEqualTo(new BigDecimal("63500.00000000"));
    assertThat(koinimTicker.getChangeRate()).isEqualTo(new BigDecimal("34.1078"));
    assertThat(koinimTicker.getBid()).isEqualTo(new BigDecimal("63500.00000000"));
    assertThat(koinimTicker.getLow()).isEqualTo(new BigDecimal("50000.00000000"));
    assertThat(koinimTicker.getVolume()).isEqualTo(new BigDecimal("421.24152018"));
    assertThat(koinimTicker.getAsk()).isEqualTo(new BigDecimal("63500.02000000"));
    assertThat(koinimTicker.getWavg()).isEqualTo(new BigDecimal("57614.15626747679543045561326"));
    assertThat(koinimTicker.getLastOrder()).isEqualTo(new BigDecimal("63500.02000000"));
    assertThat(koinimTicker.getAvg()).isEqualTo(new BigDecimal("58281.67839967173"));
  }
}
