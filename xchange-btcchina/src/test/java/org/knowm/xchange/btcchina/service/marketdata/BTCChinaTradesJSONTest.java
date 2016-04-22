package org.knowm.xchange.btcchina.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTrade;

/**
 * Test BTCChinaTrade[] JSON parsing
 */
public class BTCChinaTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCChinaTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCChinaTrade[] BTCChinaTrades = mapper.readValue(is, BTCChinaTrade[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BTCChinaTrades[0].getPrice()).isEqualTo(new BigDecimal("4719"));
  }
}
