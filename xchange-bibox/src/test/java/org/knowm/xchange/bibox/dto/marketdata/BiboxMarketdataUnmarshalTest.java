
package org.knowm.xchange.bibox.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.bibox.BiboxTestUtils;
import org.knowm.xchange.bibox.dto.BiboxResponse;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Test Marketdata JSON parsing
 * 
 * @author odrotleff
 */
public class BiboxMarketdataUnmarshalTest {

  @Test
  public void testTickerUnmarshal() throws IOException {

    BiboxResponse<BiboxTicker> response = BiboxTestUtils.getResponse(
        new TypeReference<BiboxResponse<BiboxTicker>>() {}, "/marketdata/example-ticker.json");
    assertThat(response.getCmd()).isEqualTo("ticker");

    BiboxTicker ticker = response.getResult();
    assertThat(ticker.getBuy()).isEqualTo(new BigDecimal("0.00009284"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("0.00010000"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("0.00009293"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("0.00008892"));
    assertThat(ticker.getSell()).isEqualTo(new BigDecimal("0.00009293"));
    assertThat(ticker.getTimestamp()).isEqualTo(1518037192690L);
    assertThat(ticker.getVol()).isEqualTo(new BigDecimal("5691444"));
    assertThat(ticker.getLastCny()).isEqualTo(new BigDecimal("4.77"));
    assertThat(ticker.getLastUsd()).isEqualTo(new BigDecimal("0.76"));
    assertThat(ticker.getPercent()).isEqualTo("-3.62%");
  }
}
