package org.knowm.xchange.hitbtc;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.hitbtc.dto.marketdata.*;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    assertThat(adaptedTicker.getAsk()).isEqualTo("4254.90");
    assertThat(adaptedTicker.getBid()).isEqualTo("4241.10");
    assertThat(adaptedTicker.getLow()).isEqualTo("3900.00");
    assertThat(adaptedTicker.getHigh()).isEqualTo("4333.13");
    assertThat(adaptedTicker.getLast()).isEqualTo("4255.62");
    assertThat(adaptedTicker.getVolume()).isEqualTo("1751.81");
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
    assertThat(asks.size()).isEqualTo(5);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("4274.81");
    assertThat(order.getTradableAmount()).isEqualTo("0.15");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptToExchangeMetaData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-symbols-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<HitbtcSymbol> symbols = mapper.readValue(is, new TypeReference<List<HitbtcSymbol>>() { });

    ExchangeMetaData adaptedMetaData = HitbtcAdapters.adaptToExchangeMetaData(symbols, null);
    Map<CurrencyPair, CurrencyPairMetaData> metaDataMap = adaptedMetaData.getCurrencyPairs();

    assertThat(metaDataMap.size()).isEqualTo(170);

    CurrencyPairMetaData BTC_USD = metaDataMap.get(CurrencyPair.BTC_USD);
    assertThat(BTC_USD.getTradingFee()).isEqualTo("0.001");
    assertThat(BTC_USD.getMinimumAmount()).isEqualTo("0.001");
    //TODO ??
//    assertThat(BTC_USD.getPriceScale()).isEqualTo(2);
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<HitbtcTrade> trades = mapper.readValue(is, new TypeReference<List<HitbtcTrade>>() { });

    Trades adaptedTrades = HitbtcAdapters.adaptTrades(trades, CurrencyPair.BTC_USD);
    assertThat(adaptedTrades.getlastID()).isEqualTo(17556218L);
    assertThat(adaptedTrades.getTrades()).hasSize(10);

    Trade trade = adaptedTrades.getTrades().get(4);
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getTradableAmount()).isEqualTo("0.21");
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(trade.getPrice()).isEqualTo("4109.48");
    assertThat(trade.getId()).isEqualTo("17556103");
  }

}
