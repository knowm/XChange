package com.xeiam.xchange.btctrade.service.polling;

import java.util.Arrays;
import java.util.Collection;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BTCTradeBasePollingService extends BaseExchangeService implements BasePollingService {

  private final Collection<CurrencyPair> symbols;

  protected final BTCTrade btcTrade;

  /**
   * @param exchangeSpecification
   */
  protected BTCTradeBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    symbols = Arrays.asList(CurrencyPair.BTC_CNY);
    String baseUrl = exchangeSpecification.getSslUri();
    btcTrade = RestProxyFactory.createProxy(BTCTrade.class, baseUrl);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CurrencyPair> getExchangeSymbols() {

    return symbols;
  }

  protected long toLong(Object object) {

    final long since;
    if (object instanceof Integer) {
      since = (Integer) object;
    }
    else if (object instanceof Long) {
      since = (Long) object;
    }
    else {
      since = Long.parseLong(object.toString());
    }
    return since;
  }

}
