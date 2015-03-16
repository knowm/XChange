package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class CampBXBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final CampBX campBX;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXBasePollingService(Exchange exchange) {

    super(exchange);
    this.campBX = RestProxyFactory.createProxy(CampBX.class, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
