package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTickerObject;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;

/**
 * Implementation of the market data service for BTCChina.
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaMarketDataServiceRaw extends BTCChinaBasePollingService<BTCChina> {

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaMarketDataServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> tonceFactory) {

    super(BTCChina.class, exchangeSpecification, tonceFactory);
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
   * @deprecated Use {@link #getBTCChinaHistoryData(String)} instead.
   */
  @Deprecated
  public List<BTCChinaTrade> getBTCChinaTrades(String market) throws IOException {

    return btcChina.getTrades(market);
  }

  /**
   * @see BTCChina#getHistoryData(String)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market) throws IOException {

    return btcChina.getHistoryData(market);
  }

  /**
   * @deprecated Use {@link #getBTCChinaHistoryData(String, int)} instead.
   */
  @Deprecated
  public List<BTCChinaTrade> getBTCChinaTrades(String market, int limit) throws IOException {

    return btcChina.getTrades(market, limit);
  }

  /**
   * @see BTCChina#getHistoryData(String, int)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, int limit) throws IOException {

    return btcChina.getHistoryData(market, limit);
  }

  /**
   * @deprecated Use {@link #getBTCChinaHistoryData(String, long)} instead.
   */
  @Deprecated
  public List<BTCChinaTrade> getBTCChinaTrades(String market, long since) throws IOException {

    return btcChina.getTrades(market, since);
  }

  /**
   * @see BTCChina#getHistoryData(String, long)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, long since) throws IOException {

    return btcChina.getHistoryData(market, since);
  }

  /**
   * @deprecated Use {@link #getBTCChinaHistoryData(String, long, int)} instead.
   */
  @Deprecated
  public List<BTCChinaTrade> getBTCChinaTrades(String market, long since, int limit) throws IOException {

    return btcChina.getTrades(market, since, limit);
  }

  /**
   * @see BTCChina#getHistoryData(String, long, int)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, long since, int limit) throws IOException {

    return btcChina.getHistoryData(market, since, limit);
  }

  /**
   * @deprecated Use {@link #getBTCChinaHistoryData(String, long, int, String)} instead.
   */
  @Deprecated
  public List<BTCChinaTrade> getBTCChinaTrades(String market, long since, int limit, String sinceType) throws IOException {

    return btcChina.getTrades(market, since, limit, sinceType);
  }

  /**
   * @see BTCChina#getHistoryData(String, long, int)
   */
  public BTCChinaTrade[] getBTCChinaHistoryData(String market, long since, int limit, String sinceType) throws IOException {

    return btcChina.getHistoryData(market, since, limit, sinceType);
  }

}
