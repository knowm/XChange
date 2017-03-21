package org.knowm.xchange.bter.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.BTERAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;

public class BTERAccountService extends BTERAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERAccountService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BTERAdapters.adaptWallet(super.getBTERAccountInfo()));
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
