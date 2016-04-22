package org.knowm.xchange.btccentral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralMarketOrder;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import org.knowm.xchange.btccentral.dto.marketdata.BTCCentralTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * @author kpysniak
 */
public class BTCCentralAdapters {

  /**
   * Singleton
   */
  private BTCCentralAdapters() {

  }

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
    BigDecimal last = btcCentralTicker.getPrice();
    BigDecimal volume = btcCentralTicker.getVolume();
    Date timestamp = new Date(btcCentralTicker.getAt() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).bid(bid).ask(ask).high(high).low(low).last(last).volume(volume).timestamp(timestamp)
        .build();
  }

  /**
   * @param marketDepth
   * @param currencyPair
   * @return new order book
   */
  public static OrderBook adaptMarketDepth(BTCCentralMarketDepth marketDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(marketDepth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(marketDepth.getBids(), OrderType.BID, currencyPair);
    Collections.reverse(bids);

    return new OrderBook(null, asks, bids);
  }

  /**
   * @param btcCentralMarketOrders
   * @param orderType
   * @param currencyPair
   */
  private static List<LimitOrder> adaptMarketOrderToLimitOrder(List<BTCCentralMarketOrder> btcCentralMarketOrders, OrderType orderType,
      CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>(btcCentralMarketOrders.size());

    for (BTCCentralMarketOrder btcCentralMarketOrder : btcCentralMarketOrders) {
      LimitOrder limitOrder = new LimitOrder(orderType, btcCentralMarketOrder.getAmount(), currencyPair, null,
          new Date(btcCentralMarketOrder.getTimestamp()), btcCentralMarketOrder.getPrice());
      orders.add(limitOrder);
    }

    return orders;
  }

  public static Trades adaptTrade(BTCCentralTrade[] btcCentralTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();

    for (BTCCentralTrade btcCentralTrade : btcCentralTrades) {
      Trade trade = new Trade(null, btcCentralTrade.getTraded_btc(), currencyPair, btcCentralTrade.getPrice(),
          new Date(btcCentralTrade.getCreated_at_int()), btcCentralTrade.getUuid().toString());

      trades.add(trade);
    }

    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }
}
