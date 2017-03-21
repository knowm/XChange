package org.knowm.xchange.bitkonan.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitkonan.BitKonan;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanBaseService extends BaseExchangeService implements BaseService {

  protected final BitKonan bitKonan;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitKonanBaseService(Exchange exchange) {

    super(exchange);

    this.bitKonan = RestProxyFactory.createProxy(BitKonan.class, exchange.getExchangeSpecification().getSslUri());
  }
}
