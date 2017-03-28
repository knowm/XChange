package org.knowm.xchange.examples.okcoin.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.examples.okcoin.OkCoinExampleUtils;
import org.knowm.xchange.examples.util.AccountServiceTestUtil;
import org.knowm.xchange.okcoin.service.OkCoinAccountService;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.List;

public class OkCoinAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange okcoinExchange = OkCoinExampleUtils.createTestExchange();
    generic(okcoinExchange);
  }

  private static void generic(Exchange xchange) throws IOException {
    fundingHistory(xchange.getAccountService());
  }

  private static void fundingHistory(AccountService accountService) throws IOException {
    // Get the funds information
    OkCoinAccountService.OkCoinFundingHistoryParams histParams =
            new OkCoinAccountService.OkCoinFundingHistoryParams(1, 50, "btc_cny");
    final List<FundingRecord> fundingRecords = accountService.getFundingHistory(histParams);
    AccountServiceTestUtil.printFundingHistory(fundingRecords);
  }
}
