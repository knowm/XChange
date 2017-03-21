package org.knowm.xchange.therock.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.therock.TheRockAdapters;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;

/**
 * @author Matija Mazi
 */
public class TheRockAccountService extends TheRockAccountServiceRaw implements AccountService {

  public TheRockAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return TheRockAdapters.adaptAccountInfo(balances(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    final TheRockWithdrawalResponse response = withdrawDefault(currency, amount, address);
    return String.format("%d", response.getTransactionId());
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
}
