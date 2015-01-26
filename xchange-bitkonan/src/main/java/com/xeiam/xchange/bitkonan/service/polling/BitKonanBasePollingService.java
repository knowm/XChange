package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitkonan.BitKonan;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanBasePollingService<T extends BitKonan> extends BaseExchangeService implements BasePollingService {

  protected final SynchronizedValueFactory<Long> valueFactory;

  protected final T bitKonan;

  /**
   * Constructor
   *
   * @param bitkonanType
   * @param exchange
   * @param nonceFactory
   */
  protected BitKonanBasePollingService(Class<T> bitkonanType, Exchange exchange, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchange);

    this.valueFactory = nonceFactory;
    this.bitKonan = RestProxyFactory.createProxy(bitkonanType, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
