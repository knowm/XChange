package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.DeribitAdapters;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class DeribitAccountService extends DeribitAccountServiceRaw implements AccountService {

  public DeribitAccountService(DeribitExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    Wallet wallet = Wallet.Builder.from(balances()).build();
    return new AccountInfo(null, null, Collections.singleton(wallet), openPositions(), null);
  }

  List<Balance> balances() throws IOException {
    List<Balance> balances = new ArrayList<>();
    for (Currency c : currencies()) {
      balances.add(DeribitAdapters.adapt(super.getAccountSummary(c.getCurrencyCode(), false)));
    }
    return balances;
  }

  List<OpenPosition> openPositions() throws IOException {
    List<OpenPosition> openPositions = new ArrayList<>();
    for (Currency c : currencies()) {
      super.getPositions(c.getCurrencyCode(), null).stream()
          .map(DeribitAdapters::adapt)
          .forEach(openPositions::add);
    }
    return openPositions;
  }

  Collection<Currency> currencies() {
    return exchange.getExchangeMetaData().getCurrencies().keySet();
  }
}
