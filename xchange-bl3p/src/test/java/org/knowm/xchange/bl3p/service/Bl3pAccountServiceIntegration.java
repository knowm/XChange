package org.knowm.xchange.bl3p.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bl3p.Bl3pExchange;
import org.knowm.xchange.bl3p.service.params.Bl3pWithdrawFundsParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;

public class Bl3pAccountServiceIntegration {

  Exchange exchange = ExchangeFactory.INSTANCE.createExchange(Bl3pExchange.class);
  AccountService accountService = exchange.getAccountService();

  @Test
  public void getAccountInfo() throws IOException {
    AccountInfo accountInfo = accountService.getAccountInfo();

    System.out.println(accountInfo);
  }

  @Test
  public void requestDepositAddress() throws IOException {
    String newDepositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println(newDepositAddress);
  }

  @Test
  public void getFundingHistory() throws IOException {
    try {
      List<FundingRecord> fundingRecords =
          accountService.getFundingHistory(accountService.createFundingHistoryParams());

      for (FundingRecord f : fundingRecords) {
        System.out.println(f);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void withdrawFunds() throws IOException {
    Bl3pWithdrawFundsParams.Coins withdrawCoins =
        new Bl3pWithdrawFundsParams.Coins(
            "BTC", "1P6Wyq83s7CJSd9s82xJ8HszUa4qUV7EgM", new BigDecimal("0.001"));

    String id = accountService.withdrawFunds(withdrawCoins);
    System.out.println(id);
  }
}
