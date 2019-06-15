package org.knowm.xchange.deribit.v2.service;

import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.service.account.AccountService;

public class DeribitAccountService extends DeribitAccountServiceRaw implements AccountService {

  public DeribitAccountService(DeribitExchange exchange) {

    super(exchange);
  }
}
