package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.account.AccountSummary;

public class DeribitAccountServiceRaw extends DeribitBaseService {

  public DeribitAccountServiceRaw(DeribitExchange exchange) {
    super(exchange);
  }

  public AccountSummary getAccountSummary(String currency, Boolean extended)
      throws DeribitException, IOException {
    return deribitAuthenticated.getAccountSummary(currency, extended, deribitAuth).getResult();
  }
}
