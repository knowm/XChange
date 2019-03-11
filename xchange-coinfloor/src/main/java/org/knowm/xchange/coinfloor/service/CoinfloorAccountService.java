package org.knowm.xchange.coinfloor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinfloor.CoinfloorAdapters;
import org.knowm.xchange.coinfloor.dto.account.CoinfloorBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class CoinfloorAccountService extends CoinfloorAccountServiceRaw implements AccountService {
  public CoinfloorAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    Collection<CoinfloorBalance> rawBalances = new ArrayList<>();
    for (CurrencyPair pair : exchange.getExchangeSymbols()) {
      CoinfloorBalance balance = getCoinfloorBalance(pair);
      rawBalances.add(balance);
    }
    return CoinfloorAdapters.adaptAccountInfo(
        exchange.getExchangeMetaData().getCurrencies().keySet(), rawBalances);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws NotAvailableFromExchangeException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args)
      throws NotAvailableFromExchangeException {
    throw new NotAvailableFromExchangeException();
  }
}
