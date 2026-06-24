package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gateio.GateioAdapters;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GateioFuturesTickerAndFundingDeserializationTest {

  @Test
  public void testDeserialize() throws IOException {
    InputStream is = getClass().getResourceAsStream("/__files/api_v4_futures_ticker.json");
    GateioFuturesTickerAndFunding[] tickers = new ObjectMapper()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .readValue(is, GateioFuturesTickerAndFunding[].class);
    assertThat(tickers).isNotEmpty();
    GateioFuturesTickerAndFunding ticker = tickers[0];

    assertThat(ticker.getContract()).isEqualTo(new FuturesContract("BTC/USDT/PERP"));
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
