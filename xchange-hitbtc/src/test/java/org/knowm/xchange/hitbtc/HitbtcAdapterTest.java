package org.knowm.xchange.hitbtc;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.MarketMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcIncrementalRefresh;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSnapshotFullRefresh;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTime;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrades;
import org.knowm.xchange.hitbtc.dto.meta.HitbtcMetaData;

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
  public void testAdaptToExchangeMetaData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-symbols-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    HitbtcSymbols symbols = mapper.readValue(is, HitbtcSymbols.class);

    ExchangeMetaData adaptedMetaData = HitbtcAdapters.adaptToExchangeMetaData(symbols, new HitbtcMetaData());
    Map<CurrencyPair, MarketMetaData> metaDataMap = adaptedMetaData.getMarketMetaDataMap();

    assertThat(metaDataMap.size()).isEqualTo(15);

    MarketMetaData BTC_USD = metaDataMap.get(CurrencyPair.BTC_USD);
    assertThat(BTC_USD.getTradingFee()).isEqualTo("0.001");
    assertThat(BTC_USD.getMinimumAmount()).isEqualTo("0.01");
    assertThat(BTC_USD.getPriceScale()).isEqualTo(2);
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

  @Test
  public void testAdaptSnapshotFullRefresh() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-snapshot-full-refresh-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    HitbtcSnapshotFullRefresh snapshotFullRefresh = mapper.readValue(is, HitbtcSnapshotFullRefresh.class);

    OrderBook adaptedOrderBook = HitbtcAdapters.adaptSnapshotFullRefresh(snapshotFullRefresh);

    List<LimitOrder> asks = adaptedOrderBook.getAsks();
    assertThat(asks).hasSize(205);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("348.34");
    assertThat(order.getTradableAmount()).isEqualTo("40");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(order.getType()).isEqualTo(OrderType.ASK);

    List<LimitOrder> bids = adaptedOrderBook.getBids();
    assertThat(bids).hasSize(248);
    order = bids.get(247);
    assertThat(order.getLimitPrice()).isEqualTo("0.02");
    assertThat(order.getTradableAmount()).isEqualTo("36400");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(order.getType()).isEqualTo(OrderType.BID);
  }

  @Test
  public void testAdaptIncrementalRefresh() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-incremental-refresh-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    HitbtcIncrementalRefresh incrementalRefresh = mapper.readValue(is, HitbtcIncrementalRefresh.class);

    List<OrderBookUpdate> adaptedOrders = HitbtcAdapters.adaptIncrementalRefreshOrders(incrementalRefresh);

    assertThat(adaptedOrders).hasSize(1);
    OrderBookUpdate order = adaptedOrders.get(0);
    assertThat(order.getLimitOrder().getLimitPrice()).isEqualTo("0.001276");
    assertThat(order.getLimitOrder().getCurrencyPair()).isEqualTo(new CurrencyPair("XMR/BTC"));
    assertThat(order.getLimitOrder().getType()).isEqualTo(OrderType.BID);

    Trades adaptedTrades = HitbtcAdapters.adaptIncrementalRefreshTrades(incrementalRefresh);
    assertThat(adaptedTrades.getlastID()).isEqualTo(4193069L);
    assertThat(adaptedTrades.getTrades()).hasSize(1);
    Trade trade = adaptedTrades.getTrades().get(0);
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getTradableAmount()).isEqualTo("747");
    assertThat(trade.getCurrencyPair()).isEqualTo(new CurrencyPair("XMR/BTC"));
    assertThat(trade.getPrice()).isEqualTo("0.001276");
    assertThat(trade.getTimestamp()).isEqualTo(new Date(1447559153368L));
    assertThat(trade.getId()).isEqualTo("4193069");
  }
}
