package com.xeiam.xchange.coinsetter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Various adapters for converting from Coinsetter DTOs to XChange DTOs.
 */
public final class CoinsetterAdapters {

  private CoinsetterAdapters() {

  }

  /**
   * Adapts {@link CoinsetterTicker} to {@link Ticker}.
   *
   * @param coinsetterTicker the {@link CoinsetterTicker}
   * @return the {@link Ticker}
   */
  public static Ticker adaptTicker(CoinsetterTicker coinsetterTicker) {

    return TickerBuilder.newInstance().withCurrencyPair(CurrencyPair.BTC_USD).withTimestamp(new Date(coinsetterTicker.getLast().getTimeStamp())).withAsk(coinsetterTicker.getAsk().getPrice()).withBid(
        coinsetterTicker.getBid().getPrice()).withLast(coinsetterTicker.getLast().getPrice()).withVolume(coinsetterTicker.getVolume()).build();
  }

  /**
   * Adapts {@link CoinsetterListDepth} to {@link OrderBook}
   *
   * @param coinsetterListDepth the {@link CoinsetterListDepth}.
   * @return the {@link OrderBook}.
   */
  public static OrderBook adaptOrderBook(CoinsetterListDepth coinsetterListDepth) {

    Date timeStamp = coinsetterListDepth.getTimeStamp() == null ? new Date() : new Date(coinsetterListDepth.getTimeStamp());

    BigDecimal[][] asks = coinsetterListDepth.getAsks();
    BigDecimal[][] bids = coinsetterListDepth.getBids();

    int asksLength = asks.length;
    int bidsLength = bids.length;

    List<LimitOrder> askOrders = new ArrayList<LimitOrder>(asksLength);
    List<LimitOrder> bidOrders = new ArrayList<LimitOrder>(bidsLength);

    for (int i = asksLength - 1; i >= 0; i--) {
      BigDecimal[] ask = asks[i];
      LimitOrder order = new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD).setLimitPrice(ask[0]).setTradableAmount(ask[1]).build();
      askOrders.add(order);
    }

    for (int i = 0; i < bidsLength; i++) {
      BigDecimal[] bid = bids[i];
      LimitOrder order = new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD).setLimitPrice(bid[0]).setTradableAmount(bid[1]).build();
      bidOrders.add(order);
    }

    return new OrderBook(timeStamp, askOrders, bidOrders);
  }
}
