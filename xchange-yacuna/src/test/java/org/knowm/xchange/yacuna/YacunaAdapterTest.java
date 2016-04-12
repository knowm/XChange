package org.knowm.xchange.yacuna;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.yacuna.dto.marketdata.YacunaTickerReturn;

/**
 * Created by Yingzhe on 12/28/2014.
 */
public class YacunaAdapterTest {

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = YacunaAdapterTest.class.getResourceAsStream("/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    YacunaTickerReturn tickerReturn = mapper.readValue(is, YacunaTickerReturn.class);

    Ticker ticker = YacunaAdapters.adaptTicker(tickerReturn.getTickerList().get(0), new CurrencyPair("XBT", "EUR"));

    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("279.00"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("273.04"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("279.00"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("1620.34069171"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("278.00"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("275.00"));
  }
}
