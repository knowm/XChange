package org.knowm.xchange.empoex.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.empoex.EmpoExAdapters;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;

public class EmpoExAccountService extends EmpoExAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    // TODO empoex also provides "pending" and "held" balances; perhaps they are depositing and frozen?
    return new AccountInfo(EmpoExAdapters.adaptBalances(super.getEmpoExBalances().get("available")));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
