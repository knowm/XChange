package org.knowm.xchange.coinfloor.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.BaseExchangeService;

public abstract class CoinfloorService extends BaseExchangeService {
  protected CoinfloorService(Exchange exchange) {
    super(exchange);
  }

  protected Currency normalise(Currency xchange) {
    if (xchange == Currency.BTC) {
      return Currency.XBT;
    } else {
      return xchange;
    }
  }
}
