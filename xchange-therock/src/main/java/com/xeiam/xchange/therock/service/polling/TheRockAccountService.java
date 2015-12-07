package com.xeiam.xchange.therock.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.therock.TheRockAdapters;
import com.xeiam.xchange.therock.dto.account.TheRockWithdrawalResponse;

/**
 * @author Matija Mazi
 */
public class TheRockAccountService extends TheRockAccountServiceRaw implements PollingAccountService {

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
