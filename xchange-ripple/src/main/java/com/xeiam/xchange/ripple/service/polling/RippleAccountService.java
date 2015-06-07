package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.ripple.RippleAdapters;
import com.xeiam.xchange.ripple.dto.account.RippleAccount;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class RippleAccountService extends RippleAccountServiceRaw implements PollingAccountService {

  public RippleAccountService(final Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final RippleAccount account = getRippleAccount();
    final String username = exchange.getExchangeSpecification().getApiKey();
    return RippleAdapters.adaptAccountInfo(account, username);
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
