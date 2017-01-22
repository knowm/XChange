package org.knowm.xchange.hitbtc.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.service.account.AccountService;

public class HitbtcAccountService extends HitbtcAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    HitbtcBalance[] walletRaw = getWalletRaw();

    return new AccountInfo(HitbtcAdapters.adaptWallet(walletRaw));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return getDepositAddress(currency.toString());
  }

}
