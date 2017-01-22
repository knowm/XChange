package org.knowm.xchange.jubi.service;

import java.util.HashMap;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.jubi.Jubi;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class JubiBaseService<T extends Jubi> extends BaseExchangeService implements BaseService {

  private static HashMap<String, CurrencyPair> CURRENCY_PAIR_MAP;
  private static List<CurrencyPair> CURRENCY_PAIR_LIST;
  protected final T jubi;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchange The {@link org.knowm.xchange.Exchange}
   */
  protected JubiBaseService(Class<T> type, Exchange exchange) {

    super(exchange);

    this.jubi = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }
}
