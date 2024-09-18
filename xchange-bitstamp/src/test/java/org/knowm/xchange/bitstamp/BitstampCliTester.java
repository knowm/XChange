package org.knowm.xchange.bitstamp;

import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;

public class BitstampCliTester {
  public static void main(String[] args) throws Exception{
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class, System.getenv("BITSTAMP_API_KEY"), System.getenv("BITSTAMP_SECRET_KEY"));

    AccountService accountService = exchange.getAccountService();

    DefaultTradeHistoryParamsTimeSpan defaultTradeHistoryParamsTimeSpan = new DefaultTradeHistoryParamsTimeSpan();
    defaultTradeHistoryParamsTimeSpan.setStartTime(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));

    List<FundingRecord> fundingRecordList=  accountService.getFundingHistory(defaultTradeHistoryParamsTimeSpan);

    System.out.println(fundingRecordList);


    AccountInfo accountInfo = accountService.getAccountInfo();

    System.out.println(accountInfo);

    TradeService tradeService = exchange.getTradeService();

    UserTrades userTrades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());

    System.out.println(userTrades);
  }
}
