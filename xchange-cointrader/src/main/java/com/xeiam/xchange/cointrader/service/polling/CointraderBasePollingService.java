package com.xeiam.xchange.cointrader.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CointraderBasePollingService extends BaseExchangeService implements BasePollingService {

  public CointraderBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
