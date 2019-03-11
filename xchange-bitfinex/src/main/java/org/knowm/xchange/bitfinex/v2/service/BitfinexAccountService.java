package org.knowm.xchange.bitfinex.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.service.BitfinexAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

public class BitfinexAccountService extends BitfinexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexAccountService(Exchange exchange) {
    super(exchange);
  }
}
