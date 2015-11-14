package com.xeiam.xchange.hitbtc;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTime;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades;

public class HitbtcAdapterTest {

  @Test
  public void testAdaptTime() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-time-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTime time = mapper.readValue(is, HitbtcTime.class);
    Date adaptedTime = HitbtcAdapters.adaptTime(time);

    assertThat(adaptedTime.getTime()).isEqualTo(1447130720605L);
  }

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTicker ticker = mapper.readValue(is, HitbtcTicker.class);
    Ticker adaptedTicker = HitbtcAdapters.adaptTicker(ticker, CurrencyPair.BTC_USD);

    assertThat(adaptedTicker.getAsk()).isEqualTo("347.76");
    assertThat(adaptedTicker.getBid()).isEqualTo("347.21");
    assertThat(adaptedTicker.getLow()).isEqualTo("341.41");
    assertThat(adaptedTicker.getHigh()).isEqualTo("354.66");
    assertThat(adaptedTicker.getLast()).isEqualTo("347.53");
    assertThat(adaptedTicker.getVolume()).isEqualTo("462.82");
    assertThat(adaptedTicker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptOrderbook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    HitbtcOrderBook orderBook = mapper.readValue(is, HitbtcOrderBook.class);

    OrderBook adaptedOrderBook = HitbtcAdapters.adaptOrderBook(orderBook, CurrencyPair.BTC_USD);

    List<LimitOrder> asks = adaptedOrderBook.getAsks();
    assertThat(asks.size()).isEqualTo(3);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("609.58");
    assertThat(order.getTradableAmount()).isEqualTo("1.23");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptSymbols() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-symbols-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    HitbtcSymbols symbols = mapper.readValue(is, HitbtcSymbols.class);

    List<CurrencyPair> adaptedSymbols = HitbtcAdapters.adaptCurrencyPairs(symbols);

    assertThat(adaptedSymbols.size()).isEqualTo(15);
    CurrencyPair currencyPair = adaptedSymbols.get(0);
    assertThat(currencyPair.baseSymbol).isEqualTo("BCN");
    assertThat(currencyPair.counterSymbol).isEqualTo("BTC");
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    HitbtcTrades trades = mapper.readValue(is, HitbtcTrades.class);

    Trades adaptedTrades = HitbtcAdapters.adaptTrades(trades, CurrencyPair.BTC_USD);
    assertThat(adaptedTrades.getlastID()).isEqualTo(4191471L);
    assertThat(adaptedTrades.getTrades()).hasSize(5);

    Trade trade = adaptedTrades.getTrades().get(4);
    assertThat(trade.getType()).isEqualTo(OrderType.BID);
    assertThat(trade.getTradableAmount()).isEqualTo("0.21");
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(trade.getPrice()).isEqualTo("347.65");
    assertThat(trade.getTimestamp()).isEqualTo(new Date(1447538550006L));
    assertThat(trade.getId()).isEqualTo("4191471");
  }
}
