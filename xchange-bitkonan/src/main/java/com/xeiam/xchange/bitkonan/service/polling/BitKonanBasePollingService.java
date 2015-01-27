package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitkonan.BitKonan;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

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

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
