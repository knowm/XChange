package com.xeiam.xchange.clevercoin.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class CleverCoinBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CleverCoinBasePollingService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
