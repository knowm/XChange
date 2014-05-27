package com.xeiam.xchange.btccentral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralMarketOrder;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;

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
    
    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withLast(last)
        .withVolume(volume).withTimestamp(timestamp).build();
  }
  
  /**
   * 
   * @param marketDepth
   * @param currencyPair
   * @return new order book
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
  private static List<LimitOrder> adaptMarketOrderToLimitOrder(List<BTCCentralMarketOrder> btcCentralMarketOrders, OrderType orderType,
      CurrencyPair currencyPair) {
  
    List<LimitOrder> orders = new ArrayList<LimitOrder>(btcCentralMarketOrders.size());
    
    for (BTCCentralMarketOrder btcCentralMarketOrder : btcCentralMarketOrders) {
      LimitOrder limitOrder =
          new LimitOrder(orderType, btcCentralMarketOrder.getAmount(), currencyPair, null, new Date(btcCentralMarketOrder.getTimestamp()),
              btcCentralMarketOrder.getPrice());
      orders.add(limitOrder);
    }
    
    return orders;
  }
  
  public static Trades adaptTrade(BTCCentralTrade[] btcCentralTrades, CurrencyPair currencyPair) {
  
    List<Trade> trades = new ArrayList<Trade>();
    
    for (BTCCentralTrade btcCentralTrade : btcCentralTrades) {
      Trade trade =
          new Trade(null, btcCentralTrade.getTraded_btc(), currencyPair, btcCentralTrade.getPrice(), new Date(btcCentralTrade.getCreated_at_int()),
              btcCentralTrade.getUuid().toString(), btcCentralTrade.getUuid().toString());
      
      trades.add(trade);
    }
    
    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }
}
