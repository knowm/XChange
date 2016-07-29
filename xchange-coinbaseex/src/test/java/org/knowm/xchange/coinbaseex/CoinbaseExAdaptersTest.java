package org.knowm.xchange.coinbaseex;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/**
 * Created by Yingzhe on 4/8/2015.
 */
public class CoinbaseExAdaptersTest {

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinbaseExAdaptersTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");
    InputStream is2 = CoinbaseExAdaptersTest.class.getResourceAsStream("/marketdata/example-stats-data.json");
    InputStream is3 = CoinbaseExAdaptersTest.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbaseExProductTicker coinbaseExTicker = mapper.readValue(is, CoinbaseExProductTicker.class);
    CoinbaseExProductStats coinbaseExStats = mapper.readValue(is2, CoinbaseExProductStats.class);
    CoinbaseExProductBook coinbaseExBook = mapper.readValue(is3, CoinbaseExProductBook.class);

    Ticker ticker = CoinbaseExAdapters.adaptTicker(coinbaseExTicker, coinbaseExStats, coinbaseExBook, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("246.28000000");
    assertThat(ticker.getBid().toString()).isEqualTo("245.70000000");
    assertThat(ticker.getAsk().toString()).isEqualTo("246.27000000");
    assertThat(ticker.getHigh().toString()).isEqualTo("255.47000000");
    assertThat(ticker.getLow().toString()).isEqualTo("244.29000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("7697.38286685"));
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(ticker.getTimestamp());
    assertThat(dateString).isEqualTo("2015-04-08 20:49:06");
  }
}
