package com.xeiam.xchange.jubi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.jubi.JubiAdapters;
import com.xeiam.xchange.jubi.dto.marketdata.JubiTicker;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yingzhe on 3/17/2015.
 */
public class JubiAdaptersTest {

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = JubiAdaptersTest.class.getResourceAsStream("/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiTicker jubiTicker = mapper.readValue(is, JubiTicker.class);

    Ticker ticker = JubiAdapters.adaptTicker(jubiTicker, CurrencyPair.BTC_CNY);

    assertThat(ticker.getLast().toString()).isEqualTo("1783.06");
    assertThat(ticker.getBid().toString()).isEqualTo("1782.96");
    assertThat(ticker.getAsk().toString()).isEqualTo("1787.05");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("16757.359"));
    assertThat(ticker.getHigh().toString()).isEqualTo("1836.55");
    assertThat(ticker.getLow().toString()).isEqualTo("1776");
  }
}
