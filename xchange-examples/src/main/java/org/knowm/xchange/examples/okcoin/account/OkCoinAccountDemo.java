package org.knowm.xchange.examples.okcoin.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.examples.okcoin.OkCoinExampleUtils;
import org.knowm.xchange.examples.util.AccountServiceTestUtil;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

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
    TradeHistoryParams params = accountService.createFundingHistoryParams();
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      pagingParams.setPageLength(50);
      pagingParams.setPageNumber(1);
    }
    if (params instanceof TradeHistoryParamCurrency) {
       ((TradeHistoryParamCurrency) params).setCurrency(Currency.CNY);
    }

    final List<FundingRecord> fundingRecords = accountService.getFundingHistory(params);
    AccountServiceTestUtil.printFundingHistory(fundingRecords);
  }
}
