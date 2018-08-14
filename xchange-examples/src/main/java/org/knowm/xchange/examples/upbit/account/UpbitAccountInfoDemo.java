package org.knowm.xchange.examples.upbit.account;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.upbit.UpbitDemoUtils;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.upbit.dto.account.UpbitBalance;
import org.knowm.xchange.upbit.service.UpbitAccountServiceRaw;

public class UpbitAccountInfoDemo {
  public static void main(String[] args) throws IOException {

    Exchange upbit = UpbitDemoUtils.createExchange();
    AccountService accountService = upbit.getAccountService();

    generic(accountService);
    raw((UpbitAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Wallet: " + accountInfo);
    System.out.println(
        "ETH balance: " + accountInfo.getWallet().getBalance(Currency.ETH).getAvailable());
  }

  private static void raw(UpbitAccountServiceRaw accountService) throws IOException {
    UpbitBalance[] upbitBalance = accountService.getWallet().getBalances();
    Arrays.stream(upbitBalance)
        .forEach(
            balance -> {
              System.out.println(
                  "UPBIT Currency : "
                      + balance.getCurrency()
                      + " balance :"
                      + balance.getBalance());
            });
  }
}
