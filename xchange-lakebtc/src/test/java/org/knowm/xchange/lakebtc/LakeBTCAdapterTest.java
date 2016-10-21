package org.knowm.xchange.lakebtc;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCMarketDataJsonTest;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTickers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LakeBTCAdapterTest {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = LakeBTCMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    LakeBTCTickers tickers = mapper.readValue(is, LakeBTCTickers.class);

    LakeBTCTicker cnyTicker = tickers.getCny();
    Ticker adaptedTicker = LakeBTCAdapters.adaptTicker(cnyTicker, CurrencyPair.BTC_CNY);

    assertThat(adaptedTicker.getAsk()).isEqualTo("3524.07");
    assertThat(adaptedTicker.getBid()).isEqualTo("3517.13");
    assertThat(adaptedTicker.getLow()).isEqualTo("3480.07");
    assertThat(adaptedTicker.getHigh()).isEqualTo("3584.97");
    assertThat(adaptedTicker.getLast()).isEqualTo("3524.07");
    assertThat(adaptedTicker.getVolume()).isEqualTo("5964.7677");
    assertThat(adaptedTicker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_CNY);
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
    assertThat(order.getTradableAmount()).isEqualTo("22.371");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }
}
