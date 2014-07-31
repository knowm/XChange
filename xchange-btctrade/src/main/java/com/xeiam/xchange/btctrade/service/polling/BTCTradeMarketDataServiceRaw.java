package com.xeiam.xchange.btctrade.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;

public class BTCTradeMarketDataServiceRaw extends BTCTradeBasePollingService {

  /**
   * @param exchangeSpecification
   */
  protected BTCTradeMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public BTCTradeTicker getBTCTradeTicker() {

    return btcTrade.getTicker();
  }

  public BTCTradeDepth getBTCTradeDepth() {

    return btcTrade.getDepth();
  }

  public BTCTradeTrade[] getBTCTradeTrades() {

    return btcTrade.getTrades();
  }

  public BTCTradeTrade[] getBTCTradeTrades(long since) {

    return btcTrade.getTrades(since);
  }

}
