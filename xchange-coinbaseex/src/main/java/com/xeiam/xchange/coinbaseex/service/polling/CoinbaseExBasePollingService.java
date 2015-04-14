package com.xeiam.xchange.coinbaseex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseEx;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExBasePollingService<T extends CoinbaseEx> extends BaseExchangeService implements BasePollingService {

  protected final T coinbaseEx;

  protected CoinbaseExBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.coinbaseEx = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }
}
