package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.DeribitAdapters;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.account.AccountSummary;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;

public class DeribitAccountService extends DeribitAccountServiceRaw implements AccountService {

  public DeribitAccountService(DeribitExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() {
    List<Balance> balances =
        currencies().stream()
            .map(Currency::getCurrencyCode)
            .parallel()
            .map(
                c -> {
                  try {
                    AccountSummary accountSummary = super.getAccountSummary(c, false);
                    return DeribitAdapters.adapt(accountSummary);
                  } catch (IOException e) {
                    throw new ExchangeException(e);
                  }
                })
            .collect(Collectors.toList());
    Wallet wallet = Wallet.Builder.from(balances).build();
    return new AccountInfo(null, null, Collections.singleton(wallet), openPositions(), null);
  }

  List<OpenPosition> openPositions() {
    return currencies().stream()
        .map(Currency::getCurrencyCode)
        .parallel()
        .flatMap(
            c -> {
              try {
                return super.getPositions(c, null).stream().map(DeribitAdapters::adapt);
              } catch (IOException e) {
                throw new ExchangeException(e);
              }
            })
        .collect(Collectors.toList());
  }

  Collection<Currency> currencies() {
    return exchange.getExchangeMetaData().getCurrencies().keySet();
  }
}
