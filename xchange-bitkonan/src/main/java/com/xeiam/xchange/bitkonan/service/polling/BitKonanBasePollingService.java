package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.xeiam.xchange.bitkonan.BitKonan;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author Piotr Ładyżyński
 */
public abstract class BitKonanBasePollingService<T extends BitKonan> extends BaseExchangeService implements BasePollingService {

  protected final SynchronizedValueFactory<Long> valueFactory;

  protected final T bitKonan;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BitKonanBasePollingService(Class<T> bitkonanType, ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification);

    this.valueFactory = nonceFactory;
    this.bitKonan = RestProxyFactory.createProxy(bitkonanType, exchangeSpecification.getSslUri());
    this.currencyPairs = new HashSet<CurrencyPair>();
  }


}
