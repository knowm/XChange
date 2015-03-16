package com.xeiam.xchange.bitso.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitsoBasePollingService extends BaseExchangeService implements BasePollingService {

  public BitsoBasePollingService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    return exchange.getMetaData().getCurrencyPairs();
  }

}
