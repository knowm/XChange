package com.xeiam.xchange.cointrader.service.polling;

import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CointraderBasePollingService extends BaseExchangeService implements BasePollingService {

  public CointraderBasePollingService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {
    return exchange.getMetaData().getCurrencyPairs();
  }
}
