package org.knowm.xchange.btce.v3.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEAdapters;
import org.knowm.xchange.btce.v3.dto.account.BTCEAccountInfo;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;

/**
 * @author Matija Mazi
 */
public class BTCEAccountService extends BTCEAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BTCEAccountInfo info = getBTCEAccountInfo(null, null, null, null, null, null, null);
    return new AccountInfo(BTCEAdapters.adaptWallet(info));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    String s = withdraw(currency.toString(), amount, address);
    return s;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
