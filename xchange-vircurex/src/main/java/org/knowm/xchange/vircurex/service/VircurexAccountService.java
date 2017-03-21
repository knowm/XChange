package org.knowm.xchange.vircurex.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.vircurex.VircurexAdapters;

public class VircurexAccountService extends VircurexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return VircurexAdapters.adaptAccountInfo(getVircurexAccountInfo());
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String withdrawFunds(Currency currecny, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();

  }
}
