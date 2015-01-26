package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.BTCTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BTCTradeBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BTCTrade btcTrade;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeBasePollingService(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    btcTrade = RestProxyFactory.createProxy(BTCTrade.class, baseUrl);
  }

  protected long toLong(Object object) {

    final long since;
    if (object instanceof Integer) {
      since = (Integer) object;
    } else if (object instanceof Long) {
      since = (Long) object;
    } else {
      since = Long.parseLong(object.toString());
    }
    return since;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
