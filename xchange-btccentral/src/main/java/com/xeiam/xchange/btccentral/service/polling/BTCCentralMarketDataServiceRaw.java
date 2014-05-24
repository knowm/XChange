package com.xeiam.xchange.btccentral.service.polling;

import com.xeiam.xchange.btccentral.BTCCentral;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTrade;
import com.xeiam.xchange.btccentral.service.BTCCentralBaseService;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

/**
 * @author kpysniak
 */
public class BTCCentralMarketDataServiceRaw extends BTCCentralBaseService {

  private final BTCCentral btcCentral;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BTCCentralMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    this.btcCentral = RestProxyFactory.createProxy(BTCCentral.class, exchangeSpecification.getSslUri());
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
