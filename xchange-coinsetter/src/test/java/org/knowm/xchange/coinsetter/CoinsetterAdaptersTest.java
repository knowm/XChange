package org.knowm.xchange.coinsetter;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPair;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CoinsetterAdaptersTest {

  @Test
  public void testAdaptTicker() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterTicker coinsetterTicker = ObjectMapperHelper.readValue(getClass().getResource("dto/marketdata/ticker.json"), CoinsetterTicker.class);
    Ticker ticker = CoinsetterAdapters.adaptTicker(coinsetterTicker);
    assertEquals(CurrencyPair.BTC_USD, ticker.getCurrencyPair());
    assertEquals(1392947181242L, ticker.getTimestamp().getTime());
    assertEquals(new BigDecimal("10.0"), ticker.getBid());
    assertEquals(new BigDecimal("12.0"), ticker.getAsk());
    assertEquals(new BigDecimal("10.0"), ticker.getLast());
    assertEquals(new BigDecimal("35.5"), ticker.getVolume());
  }

  @Test
  public void testAdaptOrderBook() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterPair[] coinsetterPairs = ObjectMapperHelper.readValue(getClass().getResource("dto/marketdata/depth-websockets.json"),
        CoinsetterPair[].class);
    OrderBook orderBook = CoinsetterAdapters.adaptOrderBook(coinsetterPairs);

    // asks should be sorted ascending
    List<LimitOrder> asks = orderBook.getAsks();

    assertEquals(new BigDecimal("514.49"), asks.get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.51"), asks.get(0).getTradableAmount());

    assertEquals(new BigDecimal("514.5"), asks.get(1).getLimitPrice());
    assertEquals(new BigDecimal("0.49"), asks.get(1).getTradableAmount());

    // bids should be sorted descending
    List<LimitOrder> bids = orderBook.getBids();

    assertEquals(new BigDecimal("512.51"), bids.get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.03"), bids.get(0).getTradableAmount());

    assertEquals(new BigDecimal("512.5"), bids.get(1).getLimitPrice());
    assertEquals(new BigDecimal("1.3"), bids.get(1).getTradableAmount());
  }

  @Test
  public void testAdaptOrderBookForDepth() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterListDepth coinsetterListDepth = ObjectMapperHelper.readValue(getClass().getResource("dto/marketdata/depth-list.json"),
        CoinsetterListDepth.class);
    OrderBook orderBook = CoinsetterAdapters.adaptOrderBook(coinsetterListDepth);

    // asks should be sorted ascending
    List<LimitOrder> asks = orderBook.getAsks();

    assertEquals(new BigDecimal("0.0"), asks.get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.0"), asks.get(0).getTradableAmount());

    assertEquals(new BigDecimal("1000.0"), asks.get(9).getLimitPrice());
    assertEquals(new BigDecimal("0.92"), asks.get(9).getTradableAmount());

    // bids should be sorted descending
    List<LimitOrder> bids = orderBook.getBids();

    assertEquals(new BigDecimal("703.0"), bids.get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.15"), bids.get(0).getTradableAmount());

    assertEquals(new BigDecimal("700.0"), bids.get(1).getLimitPrice());
    assertEquals(new BigDecimal("2.0"), bids.get(1).getTradableAmount());
  }

  @Test
  public void testAdaptOrderBookForFullDepth() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterListDepth coinsetterListDepth = ObjectMapperHelper.readValue(getClass().getResource("dto/marketdata/full_depth.json"),
        CoinsetterListDepth.class);
    OrderBook orderBook = CoinsetterAdapters.adaptOrderBook(coinsetterListDepth);

    assertEquals(1323424234L, orderBook.getTimeStamp().getTime());

    // asks should be sorted ascending
    List<LimitOrder> asks = orderBook.getAsks();

    assertEquals(new BigDecimal("0.0"), asks.get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.0"), asks.get(0).getTradableAmount());

    assertEquals(new BigDecimal("1000.0"), asks.get(9).getLimitPrice());
    assertEquals(new BigDecimal("0.92"), asks.get(9).getTradableAmount());

    // bids should be sorted descending
    List<LimitOrder> bids = orderBook.getBids();

    assertEquals(new BigDecimal("703.0"), bids.get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.15"), bids.get(0).getTradableAmount());

    assertEquals(new BigDecimal("700.0"), bids.get(1).getLimitPrice());
    assertEquals(new BigDecimal("2.0"), bids.get(1).getTradableAmount());
  }

}
