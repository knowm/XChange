package com.xeiam.xchange.quoine.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.quoine.QuoineAdapters;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineProduct;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineTickerJSONTest;

public class QuoineAdaptersTest {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = QuoineTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineProduct quoineTicker = mapper.readValue(is, QuoineProduct.class);
    Ticker ticker = QuoineAdapters.adaptTicker(quoineTicker, CurrencyPair.BTC_USD);

    // Verify that the example data was unmarshalled correctly
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("227.09383"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("226.78383"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("227.38976"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("0.16"));
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }
}
