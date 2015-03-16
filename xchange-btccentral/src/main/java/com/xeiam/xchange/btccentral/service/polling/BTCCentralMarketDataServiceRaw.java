package com.xeiam.xchange.btccentral.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTrade;

/**
 * @author kpysniak
 */
public class BTCCentralMarketDataServiceRaw extends BTCCentralBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BTCCentralMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BTCCentralTicker getBTCCentralTicker() throws IOException {

    return btcCentral.getBTCCentralTicker();
  }

  public BTCCentralMarketDepth getBTCCentralMarketDepth() throws IOException {

    return btcCentral.getOrderBook();
  }

  public BTCCentralTrade[] getBTCCentralTrades() throws IOException {

    return btcCentral.getTrades();
  }

}
