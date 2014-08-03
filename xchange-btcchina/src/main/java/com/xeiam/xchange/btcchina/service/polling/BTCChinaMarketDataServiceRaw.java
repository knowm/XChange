package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTickerObject;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the market data service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to various market data values</li>
 *         </ul>
 */
public class BTCChinaMarketDataServiceRaw extends BTCChinaBasePollingService<BTCChina> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTCChina.class, exchangeSpecification);
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

  public List<BTCChinaTrade> getBTCChinaTrades(String market) throws IOException {

    return btcChina.getTrades(market);
  }

  public List<BTCChinaTrade> getBTCChinaTrades(String market, int limit) throws IOException {

    return btcChina.getTrades(market, limit);
  }

  public List<BTCChinaTrade> getBTCChinaTrades(String market, long since) throws IOException {

    return btcChina.getTrades(market, since);
  }

  public List<BTCChinaTrade> getBTCChinaTrades(String market, long since, int limit) throws IOException {

    return btcChina.getTrades(market, since, limit);
  }

}
