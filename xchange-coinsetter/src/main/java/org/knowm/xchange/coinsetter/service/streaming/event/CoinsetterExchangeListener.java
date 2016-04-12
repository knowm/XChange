package org.knowm.xchange.coinsetter.service.streaming.event;

import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLevel;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLevels;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPair;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTrade;
import org.knowm.xchange.coinsetter.dto.trade.CoinsetterOrderStatus;

/**
 * The listener interface for receiving Coinsetter exchange events.
 */
public interface CoinsetterExchangeListener {

  /**
   * Invoked when got the latest transaction in real time.
   *
   * @param last the latest transaction.
   * @see <a href="https://www.coinsetter.com/api/websockets/last">Market Data: Last</a>
   */
  void onLast(CoinsetterTrade last);

  /**
   * Invoked when got the real time top bid and top ask changes.
   *
   * @param ticker the real time top bid and top ask changes as well as the last price.
   * @see <a href="https://www.coinsetter.com/api/websockets/ticker">Market Data: Ticker</a>
   */
  void onTicker(CoinsetterTicker ticker);

  /**
   * Invoked when got the top 10 bids and asks in real time.
   *
   * @param depth the top 10 bids and asks in real time.
   * @see <a href="https://www.coinsetter.com/api/websockets/depth">Market Data: Depth</a>
   */
  void onDepth(CoinsetterPair[] depth);

  /**
   * Invoked when got the cumulative available quantity in real time based on price level.
   *
   * @param levels the cumulative available quantity in real time based on price level.
   * @see <a href="https://www.coinsetter.com/api/websockets/levels">Market Data: Levels</a>
   */
  void onLevels(CoinsetterLevels levels);

  /**
   * Invoked when got the cumulative available quantity in real time based on price level.
   *
   * @param level the cumulative available quantity in real time based on price level.
   * @see <a href="https://www.coinsetter.com/api/websockets/levels">Market Data: Levels</a>
   */
  void onLevel(CoinsetterLevel level);

  /**
   * Invoked when got the real time updates on the status of your orders.
   *
   * @param orderStatus the real time updates on the status of your orders.
   * @see <a href="https://www.coinsetter.com/api/websockets/orders">Order Status</a>
   */
  void onOrderStatus(CoinsetterOrderStatus orderStatus);

}
