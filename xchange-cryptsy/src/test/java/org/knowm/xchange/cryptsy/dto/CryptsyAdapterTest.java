package org.knowm.xchange.cryptsy.dto;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.cryptsy.CryptsyAdapters;
import org.knowm.xchange.cryptsy.CryptsyUtils;
import org.knowm.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketDataReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbookReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public class CryptsyAdapterTest {

  @Test
  public void testAdaptOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_MarketOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyOrderBookReturn cryptsyOrderBook = mapper.readValue(is, CryptsyOrderBookReturn.class);

    OrderBook adaptedOrderBook = CryptsyAdapters.adaptOrderBook(cryptsyOrderBook, CurrencyPair.WDC_BTC);

    assertEquals(adaptedOrderBook.getAsks().size(), cryptsyOrderBook.getReturnValue().sellOrders().size());
    assertEquals(adaptedOrderBook.getBids().size(), cryptsyOrderBook.getReturnValue().buyOrders().size());
    assertNull(adaptedOrderBook.getTimeStamp());
  }

  @Test
  public void testAdaptOrderBookPublic() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_Orderbook_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicOrderbook> cryptsyOrderBookMap = CryptsyAdapters
        .adaptPublicOrderBookMap(mapper.readValue(is, CryptsyPublicOrderbookReturn.class).getReturnValue());

    List<OrderBook> adaptedOrderBookList = CryptsyAdapters.adaptPublicOrderBooks(cryptsyOrderBookMap);
    assertThat(adaptedOrderBookList).hasSize(1);

    OrderBook adaptedOrderBook = adaptedOrderBookList.get(0);
    assertEquals(adaptedOrderBook.getAsks().size(), 3);
    assertEquals(adaptedOrderBook.getBids().size(), 3);
    assertNull(adaptedOrderBook.getTimeStamp());

    LimitOrder asks = adaptedOrderBook.getAsks().get(0);
    assertThat(asks.getCurrencyPair()).isEqualTo(CurrencyPair.DOGE_LTC);
    assertThat(asks.getId()).isNull();
    assertThat(asks.getLimitPrice()).isEqualTo("0.00003495");
    assertThat(asks.getTradableAmount()).isEqualTo("334369.75217020");
    assertThat(asks.getType()).isEqualTo(OrderType.ASK);
  }

  @Test
  public void testAdaptOrderBookPublicWithEmptyOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_AllOrderbook_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicOrderbook> cryptsyOrderBookMap = CryptsyAdapters
        .adaptPublicOrderBookMap(mapper.readValue(is, CryptsyPublicOrderbookReturn.class).getReturnValue());

    List<OrderBook> adaptedOrderBookList = CryptsyAdapters.adaptPublicOrderBooks(cryptsyOrderBookMap);
    assertThat(adaptedOrderBookList).hasSize(2);
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_MarketTrades_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyMarketTradesReturn cryptsyTrades = mapper.readValue(is, CryptsyMarketTradesReturn.class);

    Trades adaptedTrades = CryptsyAdapters.adaptTrades(cryptsyTrades, CurrencyPair.WDC_BTC);

    assertEquals(adaptedTrades.getTrades().size(), cryptsyTrades.getReturnValue().size());
  }

  @Test
  public void testAdaptTradesPublic() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_MarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters
        .adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    Map<CurrencyPair, Trades> adaptedTradesMap = CryptsyAdapters.adaptPublicTrades(cryptsyMarketData);

    Trades adaptedTrades = adaptedTradesMap.get(CurrencyPair.DOGE_LTC);
    List<Trade> adaptedTradesList = adaptedTrades.getTrades();
    assertThat(adaptedTradesList).hasSize(2);

    Trade adaptedTrade = adaptedTradesList.get(1);
    assertThat(adaptedTrade.getCurrencyPair()).isEqualTo(CurrencyPair.DOGE_LTC);
    assertThat(adaptedTrade.getId()).isEqualTo("47692497");
    assertThat(adaptedTrade.getPrice()).isEqualTo("0.00003495");
    assertThat(adaptedTrade.getTradableAmount()).isEqualTo("2961.55892792");
    assertThat(adaptedTrade.getTimestamp()).isEqualTo(CryptsyUtils.convertDateTime("2014-05-29 21:49:34"));
    assertThat(adaptedTrade.getType()).isNull();
  }

  @Test
  public void testAdaptTradesPublicNoTrades() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_MarketData_Public_Data_NoTrades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters
        .adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    Map<CurrencyPair, Trades> adaptedTradesMap = CryptsyAdapters.adaptPublicTrades(cryptsyMarketData);

    Trades adaptedTrades = adaptedTradesMap.get(CurrencyPair.FTC_USD);
    List<Trade> adaptedTradesList = adaptedTrades.getTrades();
    assertThat(adaptedTradesList).hasSize(0);
  }

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_GetMarket_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyGetMarketsReturn cryptsyTrades = mapper.readValue(is, CryptsyGetMarketsReturn.class);

    Ticker adaptedTicker = CryptsyAdapters.adaptTicker(cryptsyTrades, CurrencyPair.WDC_BTC);

    assertEquals(adaptedTicker.getCurrencyPair(), CurrencyPair.WDC_BTC);
    assertEquals(adaptedTicker.getLast(), new BigDecimal("0.00006187"));
    assertEquals(adaptedTicker.getLow(), new BigDecimal("0.00006040"));
    assertEquals(adaptedTicker.getHigh(), new BigDecimal("0.00006335"));
    assertEquals(adaptedTicker.getVolume(), new BigDecimal("61283.49173059"));
  }

  @Test
  public void testAdaptTickerPublic() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_MarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters
        .adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    List<Ticker> adaptedTickerList = CryptsyAdapters.adaptPublicTickers(cryptsyMarketData);
    assertThat(adaptedTickerList).hasSize(1);

    Ticker adaptedTicker = adaptedTickerList.get(0);
    assertEquals(adaptedTicker.getCurrencyPair(), CurrencyPair.DOGE_LTC);
    assertEquals(adaptedTicker.getLast(), new BigDecimal("0.00003495"));
    assertEquals(adaptedTicker.getBid(), new BigDecimal("00.00003485"));
    assertEquals(adaptedTicker.getAsk(), new BigDecimal("0.00003495"));
    assertThat(adaptedTicker.getLow()).isNull();
    assertThat(adaptedTicker.getHigh()).isNull();
    assertEquals(adaptedTicker.getVolume(), new BigDecimal("13154836.13271418"));
  }

  @Test
  public void testAdaptTickerPublicNoTrades() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_MarketData_Public_Data_NoTrades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters
        .adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    List<Ticker> adaptedTickerList = CryptsyAdapters.adaptPublicTickers(cryptsyMarketData);
    assertThat(adaptedTickerList).hasSize(1);

    Ticker adaptedTicker = adaptedTickerList.get(0);
    assertEquals(adaptedTicker.getCurrencyPair(), CurrencyPair.FTC_USD);
    assertThat(adaptedTicker.getLast()).isNull();
    assertThat(adaptedTicker.getBid()).isNull();
    assertEquals(adaptedTicker.getAsk(), new BigDecimal("0.98000000"));
    assertThat(adaptedTicker.getLow()).isNull();
    assertThat(adaptedTicker.getHigh()).isNull();
    assertEquals(adaptedTicker.getVolume(), new BigDecimal("0E-8"));
  }

  @Test
  public void testAdaptAccountInfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/account/Sample_GetInfo_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyAccountInfoReturn accountInfo = mapper.readValue(is, CryptsyAccountInfoReturn.class);

    Wallet adaptedWallet = CryptsyAdapters.adaptWallet(accountInfo);

    Map<Currency, Balance> balances = adaptedWallet.getBalances();
    assertEquals(balances.size(), 300);
    for (Balance balance : balances.values()) {
      if (balance.getCurrency().equals(Currency.BTC)) {
        assertEquals(balance.getTotal(), new BigDecimal("0.05567153"));
        assertEquals(balance.getAvailable(), new BigDecimal("0.05466078"));
        assertEquals(balance.getFrozen(), new BigDecimal("0.00101075"));
      }
      if (balance.getCurrency().equals(Currency.ZRC)) {
        assertEquals(balance.getTotal(), new BigDecimal("206.64328423"));
        assertEquals(balance.getAvailable(), new BigDecimal("204.94509877"));
        assertEquals(balance.getFrozen(), new BigDecimal("1.69818546"));
      }
      if (balance.getCurrency().equals(Currency.XPM)) {
        assertEquals(balance.getTotal(), new BigDecimal("17.46835803"));
        assertEquals(balance.getAvailable(), new BigDecimal("17.46835803"));
        assertEquals(balance.getFrozen(), new BigDecimal("0"));
      }
    }
  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/trade/Sample_AllMyOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyOpenOrdersReturn cryptsyOpenOrders = mapper.readValue(is, CryptsyOpenOrdersReturn.class);

    OpenOrders adaptedOpenOrders = CryptsyAdapters.adaptOpenOrders(cryptsyOpenOrders);

    assertEquals(adaptedOpenOrders.getOpenOrders().size(), 9);

    LimitOrder order = adaptedOpenOrders.getOpenOrders().get(0);
    assertEquals(order.getId(), "90039904");
    assertEquals(order.getLimitPrice(), new BigDecimal("0.00000001"));
    assertEquals(order.getTradableAmount(), new BigDecimal("50000.10000000"));
    assertEquals(order.getCurrencyPair().base.getCurrencyCode(), "WDC");
    assertEquals(order.getCurrencyPair().counter.getCurrencyCode(), "BTC");
    assertEquals(order.getType(), OrderType.BID);

    LimitOrder order2 = adaptedOpenOrders.getOpenOrders().get(8);
    assertEquals(order2.getId(), "90041288");
    assertEquals(order2.getLimitPrice(), new BigDecimal("0.00000009"));
    assertEquals(order2.getTradableAmount(), new BigDecimal("50001.00000000"));
    assertEquals(order2.getCurrencyPair().base.getCurrencyCode(), "LTC");
    assertEquals(order2.getCurrencyPair().counter.getCurrencyCode(), "BTC");
    assertEquals(order2.getType(), OrderType.BID);
  }

  @Test
  public void testAdaptTradeHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/trade/Sample_AllMyTrades_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyTradeHistoryReturn cryptsyTradeHistory = mapper.readValue(is, CryptsyTradeHistoryReturn.class);

    UserTrades adaptedTrades = CryptsyAdapters.adaptTradeHistory(cryptsyTradeHistory);

    UserTrade trade = adaptedTrades.getUserTrades().get(0);
    assertEquals(trade.getCurrencyPair(), CurrencyPair.LTC_BTC);
    assertEquals(trade.getId(), "9982231");
    assertEquals(trade.getOrderId(), "23569349");
    assertEquals(trade.getType(), OrderType.BID);
    assertEquals(trade.getTradableAmount(), new BigDecimal("0.15949550"));
    assertEquals(trade.getPrice(), new BigDecimal("0.03128615"));
    assertEquals(trade.getFeeAmount(), new BigDecimal("0.000009980"));
    assertEquals(trade.getFeeCurrency(), Currency.BTC);
  }

  @Test
  public void testAdaptCurrencyPairs() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_AllMarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters
        .adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    Collection<CurrencyPair> adaptedCurrencyPairs = CryptsyAdapters.adaptCurrencyPairs(cryptsyMarketData);

    assertEquals(adaptedCurrencyPairs.size(), 238);
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void testAdaptMarketSets() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTest.class.getResourceAsStream("/marketdata/Sample_AllMarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters
        .adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    HashMap[] marketSets = CryptsyAdapters.adaptMarketSets(cryptsyMarketData);
    assertEquals(marketSets[0].get(135), CurrencyPair.DOGE_LTC);
    assertEquals(marketSets[1].get(CurrencyPair.DOGE_LTC), 135);
  }
}
