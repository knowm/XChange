package com.xeiam.xchange.jubi.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.jubi.Jubi;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yingzhe on 3/16/2015.
 */
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

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }
}
