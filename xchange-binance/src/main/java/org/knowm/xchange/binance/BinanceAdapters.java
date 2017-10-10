package org.knowm.xchange.binance;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.knowm.xchange.binance.dto.marketdata.BinanceOrderBook;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderBookEntry;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BinanceAdapters {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private BinanceAdapters() {
  }

  public static Ticker adaptTicker(BinanceTicker ticker, CurrencyPair currencyPair) {
    BigDecimal last = ticker.getLastPrice();
    BigDecimal bid = ticker.getBidPrice();
    BigDecimal ask = ticker.getAskPrice();
    BigDecimal high = ticker.getHighPrice();
    BigDecimal low = ticker.getLowPrice();
    BigDecimal volume = ticker.getVolume();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();
  }

  public static OrderBook adaptOrderBook(BinanceOrderBook orderBook, CurrencyPair currencyPair) {
    BinanceOrderBookEntry[] asks = orderBook.getAsks();
    BinanceOrderBookEntry[] bids = orderBook.getBids();
    return new OrderBook(null,
        convertBookEntryToLimitOrder(currencyPair, asks),
        convertBookEntryToLimitOrder(currencyPair, bids));
  }

  private static List<LimitOrder> convertBookEntryToLimitOrder(CurrencyPair currencyPair, BinanceOrderBookEntry[] bookEntry) {
    List<LimitOrder> limitOrders = new ArrayList<>(bookEntry.length);
    for (BinanceOrderBookEntry ask : bookEntry) {
      LimitOrder order = new LimitOrder(Order.OrderType.ASK, ask.getQuantity(), currencyPair, null, null, ask.getPrice());
      limitOrders.add(order);
    }
    return limitOrders;
  }
}
