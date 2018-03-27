package org.knowm.xchange.lakebtc.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.lakebtc.LakeBTCAdapters;

public class LakeBTCMarketDataJsonTest {

  @Test
  public void testDeserializeTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        LakeBTCMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/lakebtc/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    Map<String, LakeBTCTicker> tickers =
        mapper.readValue(
            is,
            mapper.getTypeFactory().constructMapType(Map.class, String.class, LakeBTCTicker.class));

    LakeBTCTicker hkdTicker = tickers.get(LakeBTCAdapters.adaptCurrencyPair(CurrencyPair.BTC_HKD));
    assertThat(hkdTicker.getAsk()).isEqualTo("73039.54");
    assertThat(hkdTicker.getBid()).isEqualTo("73039.54");
    assertThat(hkdTicker.getLast()).isEqualTo("71230.0");
    assertThat(hkdTicker.getHigh()).isEqualTo("71864.81");
    assertThat(hkdTicker.getLow()).isEqualTo("69830.0");
    assertThat(hkdTicker.getVolume()).isEqualTo("1.41627");

    LakeBTCTicker usdTicker = tickers.get(LakeBTCAdapters.adaptCurrencyPair(CurrencyPair.BTC_USD));
    assertThat(usdTicker.getAsk()).isEqualTo("9336.87");
    assertThat(usdTicker.getBid()).isEqualTo("9334.66");
    assertThat(usdTicker.getLast()).isEqualTo("9347.43");
    assertThat(usdTicker.getHigh()).isEqualTo("9477.63");
    assertThat(usdTicker.getLow()).isEqualTo("8653.83");
    assertThat(usdTicker.getVolume()).isEqualTo("2133.491461");
  }

  @Test
  public void testDeserializeOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        LakeBTCMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/lakebtc/dto/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LakeBTCOrderBook orderBook = mapper.readValue(is, LakeBTCOrderBook.class);

    BigDecimal[][] asks = orderBook.getAsks();
    assertThat(asks).hasSize(3);
    assertThat(asks[0][0]).isEqualTo("564.87");
    assertThat(asks[0][1]).isEqualTo("22.371");

    BigDecimal[][] bids = orderBook.getBids();
    assertThat(bids).hasSize(3);
    assertThat(bids[2][0]).isEqualTo("558.08");
    assertThat(bids[2][1]).isEqualTo("0.9878");
  }
}
