package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.account.AccountSummary;
import org.knowm.xchange.deribit.v2.dto.account.Position;

public class DeribitAccountServiceRaw extends DeribitBaseService {

  public DeribitAccountServiceRaw(DeribitExchange exchange) {
    super(exchange);
  }

  public AccountSummary getAccountSummary(String currency, Boolean extended) throws IOException {
    return deribitAuthenticated.getAccountSummary(currency, extended, deribitAuth).getResult();
  }

  public List<Position> getPositions(String currency, Kind kind) throws IOException {
    return deribitAuthenticated.getPositions(currency, kind, deribitAuth).getResult();
  }
}
