package com.xeiam.xchange.service.polling;

import com.xeiam.xchange.BTCCentral;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.service.BTCCentralBaseService;
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

  public BTCCentralTicker getTicker() throws IOException {

    return btcCentral.getBTCCentralTicker();
  }


}
