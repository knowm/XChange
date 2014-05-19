package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kpysniak
 */
public class BTCCentralAdapters {

  /**
   * Singleton
   */
  private BTCCentralAdapters() { }

  /**
   * Adapts a BTCCentralTicker to a Ticker Object
   *
   * @param btcCentralTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BTCCentralTicker btcCentralTicker, CurrencyPair currencyPair) {

    BigDecimal bid = btcCentralTicker.getBid();
    BigDecimal ask = btcCentralTicker.getAsk();
    BigDecimal high = btcCentralTicker.getHigh();
    BigDecimal low = btcCentralTicker.getLow();
    BigDecimal volume = btcCentralTicker.getVolume();
    Date timestamp = new Date(btcCentralTicker.getAt() * 1000L);

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair)
        .withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume)
        .withTimestamp(timestamp).build();
  }

  /**
   *
   * @param marketDepth
   * @param currencyPair
   * @return
   */
  public static OrderBook adaptMarketDepth(BTCCentralMarketDepth marketDepth, CurrencyPair currencyPair) {
    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(marketDepth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(marketDepth.getBids(), OrderType.BID, currencyPair);

    // TODO
    // What timestamp should be used? Latest/Earliest/Current one?
    return new OrderBook(new Date(), asks, bids);
  }

  /**
   *
   * @param btcCentralMarketOrders
   * @param orderType
   * @param currencyPair
   */
  private static List<LimitOrder> adaptMarketOrderToLimitOrder(BTCCentralMarketOrder[] btcCentralMarketOrders,
                                                               OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>(btcCentralMarketOrders.length);

    for (BTCCentralMarketOrder btcCentralMarketOrder : btcCentralMarketOrders) {
      LimitOrder limitOrder = new LimitOrder(orderType, btcCentralMarketOrder.getAmount(), currencyPair, null, new Date(btcCentralMarketOrder.getTimestamp()), btcCentralMarketOrder.getPrice());
      orders.add(limitOrder);
    }

    return orders;
  }

  public static Trades adaptTrade(BTCCentralTrade[] btcCentralTrades, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<Trade>();

    for (BTCCentralTrade btcCentralTrade : btcCentralTrades) {
      Trade trade = new Trade(null, btcCentralTrade.getTraded_btc(), currencyPair,
          btcCentralTrade.getPrice(), new Date(btcCentralTrade.getCreated_at_int()),
          btcCentralTrade.getUuid().toString(), btcCentralTrade.getUuid().toString());

      trades.add(trade);
    }

    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }
}
