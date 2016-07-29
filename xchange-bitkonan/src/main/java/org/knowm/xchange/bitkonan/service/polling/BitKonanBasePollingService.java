package org.knowm.xchange.bitkonan.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitkonan.BitKonan;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
