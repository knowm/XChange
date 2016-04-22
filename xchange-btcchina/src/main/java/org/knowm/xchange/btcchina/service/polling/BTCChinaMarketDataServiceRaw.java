package org.knowm.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.BTCChina;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTickerObject;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTrade;

/**
 * Implementation of the market data service for BTCChina.
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaMarketDataServiceRaw extends BTCChinaBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCChinaMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<String, BTCChinaTickerObject> getBTCChinaTickers() throws IOException {

    return btcChina.getTicker(BTCChinaExchange.ALL_MARKET);
  }

  public BTCChinaTicker getBTCChinaTicker(String market) throws IOException {

    return btcChina.getTicker(market);
  }

  public BTCChinaDepth getBTCChinaOrderBook(String market) throws IOException {

    return btcChina.getFullDepth(market);
  }

  public BTCChinaDepth getBTCChinaOrderBook(String market, int limit) throws IOException {

    return btcChina.getOrderBook(market, limit);
  }

  /**
   * @see BTCChina#getHistoryData(String)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market) throws IOException {

    return btcChina.getHistoryData(market);
  }

  /**
   * @see BTCChina#getHistoryData(String, int)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, int limit) throws IOException {

    return btcChina.getHistoryData(market, limit);
  }

  /**
   * @see BTCChina#getHistoryData(String, long)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, long since) throws IOException {

    return btcChina.getHistoryData(market, since);
  }

  /**
   * @see BTCChina#getHistoryData(String, long, int)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, long since, int limit) throws IOException {

    return btcChina.getHistoryData(market, since, limit);
  }

  /**
   * @see BTCChina#getHistoryData(String, long, int)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, long since, int limit, String sinceType) throws IOException {

    return btcChina.getHistoryData(market, since, limit, sinceType);
  }

}
