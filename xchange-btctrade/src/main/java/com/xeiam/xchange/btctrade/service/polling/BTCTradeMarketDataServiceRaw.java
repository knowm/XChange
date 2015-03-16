package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;

public class BTCTradeMarketDataServiceRaw extends BTCTradeBasePollingService {

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
