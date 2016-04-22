package org.knowm.xchange.btcchina.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTicker;

/**
 * Test BTCChinaTicker JSON parsing
 */
public class BTCChinaTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCChinaTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCChinaTicker BTCChinaTicker = mapper.readValue(is, BTCChinaTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BTCChinaTicker.getTicker().getLast()).isEqualTo(new BigDecimal("546.00"));
    assertThat(BTCChinaTicker.getTicker().getHigh()).isEqualTo(new BigDecimal("547.77"));
    assertThat(BTCChinaTicker.getTicker().getLow()).isEqualTo(new BigDecimal("545.00"));
    assertThat(BTCChinaTicker.getTicker().getVol()).isEqualTo(new BigDecimal("2593.89900000"));
  }

}
