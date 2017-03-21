package org.knowm.xchange.examples.okcoin.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.FundsInfo;
import org.knowm.xchange.examples.okcoin.OkCoinExampleUtils;
import org.knowm.xchange.examples.util.AccountServiceTestUtil;
import org.knowm.xchange.okcoin.service.OkCoinAccountService;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class OkCoinAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange okcoinExchange = OkCoinExampleUtils.createTestExchange();
    generic(okcoinExchange);
  }

  private static void generic(Exchange xchange) throws IOException {
    fundsInfo(xchange.getAccountService());
  }

  private static void fundsInfo(AccountService accountService) throws IOException {
    // Get the funds information
    OkCoinAccountService.OkCoinFundsInfoHistoryParams histParams =
            new OkCoinAccountService.OkCoinFundsInfoHistoryParams(1, 50, "btc_cny");
    FundsInfo fundsInfo = accountService.getFundsInfo(histParams);
    AccountServiceTestUtil.printFundsInfo(fundsInfo);
  }
}
