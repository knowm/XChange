package org.knowm.xchange.lakebtc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCMarketDataJsonTest;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTicker;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LakeBTCAdapterTest {

@Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = LakeBTCMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    Map<String,LakeBTCTicker> tickers = mapper.readValue(is, mapper.getTypeFactory().constructMapType(Map.class, String.class, LakeBTCTicker.class));

    LakeBTCTicker hkdTicker = tickers.get(LakeBTCAdapters.adaptCurrencyPair(CurrencyPair.BTC_HKD));
    Ticker adaptedTicker = LakeBTCAdapters.adaptTicker(hkdTicker, CurrencyPair.BTC_HKD);

    assertThat(adaptedTicker.getAsk()).isEqualTo("73039.54");
    assertThat(adaptedTicker.getBid()).isEqualTo("73039.54");
    assertThat(adaptedTicker.getLow()).isEqualTo("69830.0");
    assertThat(adaptedTicker.getHigh()).isEqualTo("71864.81");
    assertThat(adaptedTicker.getLast()).isEqualTo("71230.0");
    assertThat(adaptedTicker.getVolume()).isEqualTo("1.41627");
    assertThat(adaptedTicker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_HKD);
  }

  @Test
  public void testAdaptOrderbook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = LakeBTCMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LakeBTCOrderBook orderBook = mapper.readValue(is, LakeBTCOrderBook.class);

    OrderBook adaptedOrderBook = LakeBTCAdapters.adaptOrderBook(orderBook, CurrencyPair.BTC_USD);

    List<LimitOrder> asks = adaptedOrderBook.getAsks();
    assertThat(asks.size()).isEqualTo(3);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("564.87");
    assertThat(order.getOriginalAmount()).isEqualTo("22.371");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }
}
