package org.knowm.xchange.bitcoincore.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoincore.BitcoinCoreAdapters;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;

public class BitcoinCoreAccountService extends BitcoinCoreAccountServiceRaw implements AccountService {

  public BitcoinCoreAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final BitcoinCoreBalanceResponse balance = getBalance();
    final BitcoinCoreBalanceResponse unconfirmed = getUnconfirmedBalance();
    return BitcoinCoreAdapters.adaptAccountInfo(balance, unconfirmed);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    throw new UnsupportedOperationException();
  }
}
