package org.knowm.xchange.examples.poloniex.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.examples.poloniex.PoloniexExamplesUtils;
import org.knowm.xchange.poloniex.service.PoloniexAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.utils.CertHelper;

/** @author Zach Holmes */
public class PoloniexAccountDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange poloniex = PoloniexExamplesUtils.getExchange();
    AccountService accountService = poloniex.getAccountService();

    generic(accountService);
    raw((PoloniexAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    System.out.println("----------GENERIC----------");
    System.out.println(accountService.requestDepositAddress(Currency.BTC));
    System.out.println(accountService.getAccountInfo());

    System.out.println(accountService.withdrawFunds(Currency.BTC, new BigDecimal("0.03"), "XXX"));

    final TradeHistoryParams params = accountService.createFundingHistoryParams();
    ((TradeHistoryParamsTimeSpan) params)
        .setStartTime(new Date(System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000));

    final List<FundingRecord> fundingHistory = accountService.getFundingHistory(params);
    for (FundingRecord fundingRecord : fundingHistory) {
      System.out.println(fundingRecord);
    }
  }

  private static void raw(PoloniexAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW------------");
    System.out.println(accountService.getDepositAddress("BTC"));
    System.out.println(accountService.getWallets());
  }
}
