package org.knowm.xchange.btcturk;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;

/** Created by semihunaldi on 26/11/2017 */
public class BTCTurkAdapterTest {
  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-full-depth-data.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkOrderBook btcTurkOrderBook = mapper.readValue(is, BTCTurkOrderBook.class);

    OrderBook orderBook = BTCTurkAdapters.adaptOrderBook(btcTurkOrderBook, CurrencyPair.BTC_TRY);
    assertThat(orderBook.getBids().size()).isEqualTo(30);
    assertThat(orderBook.getAsks().size()).isEqualTo(30);
    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().toString()).isEqualTo("38623.00000000");
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(orderBook.getBids().get(0).getOriginalAmount())
        .isEqualTo(new BigDecimal("0.00286318"));
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_TRY);
    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1511725654L);
  }

  @Test
  public void testTickerAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-ticker-data.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkTicker btcTurkTicker = mapper.readValue(is, BTCTurkTicker.class);

    Ticker ticker = BTCTurkAdapters.adaptTicker(btcTurkTicker);
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("38800.02"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("39436.99"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1511724574L);
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("37909.3"));
  }

  @Test
  public void testTradesAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-trades-data.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkTrade[] btcTurkTrades = mapper.readValue(is, BTCTurkTrade[].class);
    Trades trades = BTCTurkAdapters.adaptTrades(btcTurkTrades, CurrencyPair.BTC_TRY);

    assertThat(trades.getTrades().get(0).getId()).isEqualTo("1");
    assertThat(trades.getTrades().get(0).getPrice()).isEqualTo(new BigDecimal("38880"));
    assertThat(trades.getTrades().get(0).getOriginalAmount())
        .isEqualTo(new BigDecimal("0.09967147"));
    assertThat(trades.getTrades().get(0).getTimestamp().getTime()).isEqualTo(1511728478L);
  }
}
