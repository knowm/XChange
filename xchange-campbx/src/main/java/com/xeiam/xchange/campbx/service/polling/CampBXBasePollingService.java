package com.xeiam.xchange.campbx.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

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
}
