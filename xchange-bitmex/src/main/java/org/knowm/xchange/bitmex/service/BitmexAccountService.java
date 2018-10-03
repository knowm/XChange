package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class BitmexAccountService extends BitmexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexAccountService(BitmexExchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    BitmexAccount account = super.getBitmexAccountInfo();
    BitmexWallet bitmexWallet = getBitmexWallet();
    String username = account.getUsername();
    BigDecimal amount = bitmexWallet.getAmount();
    BigDecimal amt = amount.divide(BigDecimal.valueOf(100_000_000L));
    Balance balance = new Balance(Currency.BTC, amt);
    Wallet wallet = new Wallet(Currency.BTC.getSymbol(), balance);
    AccountInfo accountInfo = new AccountInfo(username, wallet);
    return accountInfo;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(currency.getCurrencyCode(), amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    String currencyCode = currency.getCurrencyCode();

    // bitmex seems to use a lowercase 't' in XBT
    // can test this here - https://testnet.bitmex.com/api/explorer/#!/User/User_getDepositAddress
    // uppercase 'T' will return 'Unknown currency code'
    if (currencyCode.equals("XBT")) {
      currencyCode = "XBt";
    }
    return requestDepositAddress(currencyCode);
  }
}
