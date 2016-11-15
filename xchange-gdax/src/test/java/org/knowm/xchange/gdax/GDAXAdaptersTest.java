package org.knowm.xchange.gdax;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Yingzhe on 4/8/2015.
 */
public class GDAXAdaptersTest {

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = GDAXAdaptersTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");
    InputStream is2 = GDAXAdaptersTest.class.getResourceAsStream("/marketdata/example-stats-data.json");
    InputStream is3 = GDAXAdaptersTest.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GDAXProductTicker coinbaseExTicker = mapper.readValue(is, GDAXProductTicker.class);
    GDAXProductStats coinbaseExStats = mapper.readValue(is2, GDAXProductStats.class);

    Ticker ticker = GDAXAdapters.adaptTicker(coinbaseExTicker, coinbaseExStats, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("246.28000000");
    assertThat(ticker.getBid().toString()).isEqualTo("637");
    assertThat(ticker.getAsk().toString()).isEqualTo("637.11");
    assertThat(ticker.getHigh().toString()).isEqualTo("255.47000000");
    assertThat(ticker.getLow().toString()).isEqualTo("244.29000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("4661.70407704"));
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(ticker.getTimestamp());
    assertThat(dateString).isEqualTo("2015-04-08 20:49:06");
  }
}
