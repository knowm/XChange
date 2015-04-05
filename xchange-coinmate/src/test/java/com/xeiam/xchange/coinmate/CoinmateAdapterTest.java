package com.xeiam.xchange.coinmate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.coinmate.dto.marketdata.CoinmateTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateAdapterTest {
    
  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinmateAdapterTest.class.getResourceAsStream("/marketdata/example-ticker.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinmateTicker bitstampTicker = mapper.readValue(is, CoinmateTicker.class);

    Ticker ticker = CoinmateAdapters.adaptTicker(bitstampTicker, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("254.08");
    assertThat(ticker.getBid().toString()).isEqualTo("252.93");
    assertThat(ticker.getAsk().toString()).isEqualTo("254.08");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("42.78294066"));
  }

}
