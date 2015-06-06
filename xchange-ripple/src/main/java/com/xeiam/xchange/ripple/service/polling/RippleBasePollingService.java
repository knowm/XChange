package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class RippleBasePollingService extends BaseExchangeService implements BasePollingService {

  public RippleBasePollingService(final Exchange exchange) {
    super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    return exchange.getMetaData().getCurrencyPairs();
  }

}
