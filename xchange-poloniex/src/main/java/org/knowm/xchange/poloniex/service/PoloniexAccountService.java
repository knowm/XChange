package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountService extends PoloniexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    List<Balance> balances = getWallets();
    return new AccountInfo(new Wallet(balances));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdrawFunds(currency, amount, address, null);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return getDepositAddress(currency.toString());
  }

}
