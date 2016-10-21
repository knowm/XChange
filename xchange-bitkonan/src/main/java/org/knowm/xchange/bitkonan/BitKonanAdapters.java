package org.knowm.xchange.bitkonan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanOrderBookElement;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanTicker;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BitKonanAdapters {

  /**
   * Singleton
   */
  private BitKonanAdapters() {

  }

  /**
   * Adapts a HitbtcTicker to a Ticker Object
   *
   * @param bitKonanTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitKonanTicker bitKonanTicker, CurrencyPair currencyPair) {

    BigDecimal bid = bitKonanTicker.getBid();
    BigDecimal ask = bitKonanTicker.getAsk();
    BigDecimal high = bitKonanTicker.getHigh();
    BigDecimal low = bitKonanTicker.getLow();
    BigDecimal last = bitKonanTicker.getLast();
    BigDecimal volume = bitKonanTicker.getVolume();
    // no timestamp from BitKonan

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();
  }

  public static OrderBook adaptOrderBook(BitKonanOrderBook bitKonanOrderBook, CurrencyPair pair) {

    // only BTCUSD available at BitKonan
    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(bitKonanOrderBook.getAsks(), OrderType.ASK, pair);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(bitKonanOrderBook.getBids(), OrderType.BID, pair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(BitKonanOrderBookElement[] bitkonanOrders, OrderType orderType,
      CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>(bitkonanOrders.length);

    for (BitKonanOrderBookElement bitKonanOrderBookElement : bitkonanOrders) {

      BigDecimal price = bitKonanOrderBookElement.getPrice();
      BigDecimal amount = bitKonanOrderBookElement.getVolume();

      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, null, price);
      orders.add(limitOrder);
    }

    return orders;
  }

  public static Trades adaptTrades(List<BitKonanTrade> bitKonanTrades, CurrencyPair currencyPair) {
    List<Trade> tradeList = new ArrayList<>();
    for (BitKonanTrade bitKonanTrade : bitKonanTrades) {
      tradeList.add(adaptTrade(bitKonanTrade, currencyPair));
    }
    return new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(BitKonanTrade konanTrade, CurrencyPair currencyPair) {
    return new Trade(null, konanTrade.getBtc(), currencyPair, konanTrade.getUsd(), konanTrade.getTime(), null);
  }
}
