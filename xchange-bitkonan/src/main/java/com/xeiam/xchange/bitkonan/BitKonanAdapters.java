package com.xeiam.xchange.bitkonan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanOrderBookElement;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanAdapters {

  private static final BigDecimal LOT_MULTIPLIER = new BigDecimal("100");

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
    Date timestamp = null;

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
  }

  public static OrderBook adaptOrderBook(BitKonanOrderBook bitKonanOrderBook) {

    // only BTCUSD available at BitKonan
    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(bitKonanOrderBook.getAsks(), OrderType.ASK, CurrencyPair.BTC_USD);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(bitKonanOrderBook.getBids(), OrderType.BID, CurrencyPair.BTC_USD);

    return new OrderBook(new Date(), asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(BitKonanOrderBookElement[] bitkonanOrders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>(bitkonanOrders.length);

    for (BitKonanOrderBookElement bitKonanOrderBookElement : bitkonanOrders) {

      BigDecimal price = bitKonanOrderBookElement.getPrice();
      BigDecimal amount = bitKonanOrderBookElement.getVolume();

      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, new Date(), price);
      orders.add(limitOrder);
    }

    return orders;
  }

}
