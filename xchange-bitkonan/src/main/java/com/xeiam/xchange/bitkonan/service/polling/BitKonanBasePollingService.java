package com.xeiam.xchange.bitkonan.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitkonan.BitKonan;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BitKonan bitKonan;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitKonanBasePollingService(Exchange exchange) {

    super(exchange);

    this.bitKonan = RestProxyFactory.createProxy(BitKonan.class, exchange.getExchangeSpecification().getSslUri());
  }
}
