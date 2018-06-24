package org.knowm.xchange.examples.coinone.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.dto.account.CoinoneBalancesResponse;
import org.knowm.xchange.coinone.service.CoinoneAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.coinone.CoinoneDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class CoinoneAccountInfoDemo {
  public static void main(String[] args) throws IOException {

    Exchange coinone = CoinoneDemoUtils.createExchange();
    AccountService accountService = coinone.getAccountService();

    generic(accountService);
    raw((CoinoneAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Wallet: " + accountInfo);
    System.out.println(
        "ETH balance: " + accountInfo.getWallet().getBalance(Currency.ETH).getAvailable());
  }

  private static void raw(CoinoneAccountServiceRaw accountService) throws IOException {
    CoinoneBalancesResponse coinoneBalancesResponse = accountService.getWallet();
    System.out.println("Coinone Avail Balance: " + coinoneBalancesResponse.getEth().getAvail());
    System.out.println("Coinone total Balance: " + coinoneBalancesResponse.getEth().getBalance());
  }
}
