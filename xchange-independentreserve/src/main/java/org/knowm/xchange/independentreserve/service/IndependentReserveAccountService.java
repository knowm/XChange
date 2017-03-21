package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.independentreserve.IndependentReserveAdapters;
import org.knowm.xchange.service.account.AccountService;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveAccountService extends IndependentReserveAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public IndependentReserveAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), IndependentReserveAdapters.adaptWallet(getIndependentReserveBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new UnsupportedOperationException();
  }
}
