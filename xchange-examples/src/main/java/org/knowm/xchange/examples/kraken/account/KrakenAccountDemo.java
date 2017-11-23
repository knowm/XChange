package org.knowm.xchange.examples.kraken.account;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.examples.util.AccountServiceTestUtil;
import org.knowm.xchange.kraken.service.KrakenAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencies;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Kraken exchange with authentication</li>
 * <li>View account balance</li>
 * </ul>
 */
public class KrakenAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    AccountInfo accountInfo = krakenExchange.getAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo.toString());

    fundingHistory(krakenExchange.getAccountService());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    KrakenAccountServiceRaw rawKrakenAcctService = (KrakenAccountServiceRaw) krakenExchange.getAccountService();
    System.out.println("Balance Info: " + rawKrakenAcctService.getKrakenBalance());
  }

  private static void fundingHistory(AccountService accountService) throws IOException {
    // Get the funds information
    TradeHistoryParams params = accountService.createFundingHistoryParams();
    if (params instanceof TradeHistoryParamsTimeSpan) {
      final TradeHistoryParamsTimeSpan timeSpanParam = (TradeHistoryParamsTimeSpan) params;
      timeSpanParam.setStartTime(new Date(System.currentTimeMillis() - (1 * 12 * 30 * 24 * 60 * 60 * 1000L)));
    }

    if (params instanceof HistoryParamsFundingType) {
      ((HistoryParamsFundingType) params).setType(FundingRecord.Type.DEPOSIT);
    }

    if (params instanceof TradeHistoryParamCurrencies) {
      final TradeHistoryParamCurrencies currenciesParam = (TradeHistoryParamCurrencies) params;
      currenciesParam.setCurrencies(new Currency[]{Currency.BTC, Currency.USD});
    }

    List<FundingRecord> fundingRecords = accountService.getFundingHistory(params);
    AccountServiceTestUtil.printFundingHistory(fundingRecords);
  }
}
