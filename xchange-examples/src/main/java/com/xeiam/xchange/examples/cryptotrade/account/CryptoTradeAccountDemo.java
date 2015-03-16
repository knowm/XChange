package com.xeiam.xchange.examples.cryptotrade.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeAccountServiceRaw;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.cryptotrade.CryptoTradeExampleUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.utils.CertHelper;

public class CryptoTradeAccountDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange exchange = CryptoTradeExampleUtils.createExchange();
    PollingAccountService accountService = exchange.getPollingAccountService();

    generic(accountService);
    raw((CryptoTradeAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(CryptoTradeAccountServiceRaw accountService) throws IOException {

    CryptoTradeAccountInfo accountInfo = accountService.getCryptoTradeAccountInfo();
    System.out.println(accountInfo);
  }
}
