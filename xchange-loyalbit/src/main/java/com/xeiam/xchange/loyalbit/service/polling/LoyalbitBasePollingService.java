package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class LoyalbitBasePollingService extends BaseExchangeService implements BasePollingService {

  public LoyalbitBasePollingService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    return exchange.getMetaData().getCurrencyPairs();
  }

}
