package org.knowm.xchange.gateio.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class GateioFuturesTickerDeserializationTest {

  @Test
  public void testDeserialize() throws IOException {
    InputStream is = getClass().getResourceAsStream("/__files/api_v4_futures_ticker.json");
    GateioFuturesTicker[] tickers = new ObjectMapper()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .readValue(is, GateioFuturesTicker[].class);
    assertThat(tickers).isNotEmpty();
    GateioFuturesTicker ticker = tickers[0];

    assertThat(ticker.getContract()).isEqualTo("BTC_USDT");
    assertThat(ticker.getLastPrice()).isEqualTo(new BigDecimal("6432"));
    assertThat(ticker.getLow24h()).isEqualTo(new BigDecimal("6278"));
    assertThat(ticker.getHigh24h()).isEqualTo(new BigDecimal("6790"));
    assertThat(ticker.getChangeUtc0()).isNull();
    assertThat(ticker.getChangeUtc8()).isNull();
    assertThat(ticker.getQuantoBaseRate()).isNull();

    // Check adapter mapping
    Ticker adaptedTicker = GateioAdapters.toTickerFutures(ticker, BigDecimal.ONE);
    assertThat(adaptedTicker.getHigh()).isEqualTo(new BigDecimal("6790"));
    assertThat(adaptedTicker.getLow()).isEqualTo(new BigDecimal("6278"));
  }
}
