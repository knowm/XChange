package org.knowm.xchange.cointrader.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class CointraderBasePollingService extends BaseExchangeService implements BasePollingService {

  public CointraderBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
