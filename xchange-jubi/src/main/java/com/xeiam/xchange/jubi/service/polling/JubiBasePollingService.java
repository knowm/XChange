package com.xeiam.xchange.jubi.service.polling;

import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.jubi.Jubi;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class JubiBasePollingService<T extends Jubi> extends BaseExchangeService implements BasePollingService {

  private static HashMap<String, CurrencyPair> CURRENCY_PAIR_MAP;
  private static List<CurrencyPair> CURRENCY_PAIR_LIST;
  protected final T jubi;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchange The {@link com.xeiam.xchange.Exchange}
   */
  protected JubiBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);

    this.jubi = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }
}
