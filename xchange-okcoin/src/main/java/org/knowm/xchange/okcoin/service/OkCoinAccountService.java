package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OKCoinWithdraw;
import org.knowm.xchange.service.account.AccountService;

public class OkCoinAccountService extends OkCoinAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinAccountService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return OkCoinAdapters.adaptAccountInfo(getUserInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    String okcoinCurrency = currency == Currency.BTC ? "btc_usd" : "btc_ltc";

    OKCoinWithdraw result = withdraw(null, okcoinCurrency, address, amount);

    if (result != null)
      return result.getWithdrawId();

    return "";
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
