package org.knowm.xchange.btctrade.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTrade;

public class BTCTradeMarketDataServiceRaw extends BTCTradeBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BTCTradeTicker getBTCTradeTicker() throws IOException {

    return btcTrade.getTicker();
  }

  public BTCTradeDepth getBTCTradeDepth() throws IOException {

    return btcTrade.getDepth();
  }

  public BTCTradeTrade[] getBTCTradeTrades() throws IOException {

    return btcTrade.getTrades();
  }

  public BTCTradeTrade[] getBTCTradeTrades(long since) throws IOException {

    return btcTrade.getTrades(since);
  }
}
