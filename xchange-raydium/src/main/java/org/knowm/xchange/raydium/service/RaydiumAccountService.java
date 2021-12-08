package org.knowm.xchange.raydium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.account.AccountService;

public class RaydiumAccountService extends RaydiumAccountServiceRaw implements AccountService {

  public RaydiumAccountService(Exchange exchange) {
    super(exchange);
  }
}
