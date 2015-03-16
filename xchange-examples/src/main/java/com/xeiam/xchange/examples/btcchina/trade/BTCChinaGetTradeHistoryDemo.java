package com.xeiam.xchange.examples.btcchina.trade;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Demo for {@link BTCChinaTradeService#getTradeHistory(Object...)}.
 */
public class BTCChinaGetTradeHistoryDemo {

  private static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  private static PollingTradeService tradeService = btcchina.getPollingTradeService();
  private static BTCChinaTradeServiceRaw tradeServiceRaw = (BTCChinaTradeServiceRaw) tradeService;

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  private static void generic() throws IOException {

    Trades trades = tradeService.getTradeHistory(10);
    System.out.println("Trade count: " + trades.getTrades().size());
    for (Trade trade : trades.getTrades()) {
      System.out.println(trade);
    }
  }

  private static void raw() throws IOException {

    BTCChinaTransactionsResponse response = tradeServiceRaw.getTransactions("all", 10, null, null, null);
    System.out.println("BTCChinaTransactionsResponse: " + response);
    for (BTCChinaTransaction transaction : response.getResult().getTransactions()) {
      System.out.println(ToStringBuilder.reflectionToString(transaction));
    }
  }

}
