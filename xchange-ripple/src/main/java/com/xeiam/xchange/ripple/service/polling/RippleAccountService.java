package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.ripple.RippleAdapters;
import com.xeiam.xchange.ripple.dto.account.RippleAccountBalances;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class RippleAccountService extends RippleAccountServiceRaw implements PollingAccountService {

  public RippleAccountService(final Exchange exchange) {
    super(exchange);
  }

  /**
   * A wallet's currency will be prefixed with the issuing counterparty address for all currencies other than XRP.
   */
  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final RippleAccountBalances account = getRippleAccountBalances();
    final String username = exchange.getExchangeSpecification().getApiKey();
    return RippleAdapters.adaptAccountInfo(account, username);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
