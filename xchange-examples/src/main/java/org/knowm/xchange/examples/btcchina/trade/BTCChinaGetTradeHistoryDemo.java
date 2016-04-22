package org.knowm.xchange.examples.btcchina.trade;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaTransaction;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import org.knowm.xchange.btcchina.service.polling.BTCChinaTradeService;
import org.knowm.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.btcchina.BTCChinaExamplesUtils;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

/**
 * Demo for {@link BTCChinaTradeService#getTradeHistory(org.knowm.xchange.service.polling.trade.params.TradeHistoryParams)}.
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

    BTCChinaTradeService.BTCChinaTradeHistoryParams params = new BTCChinaTradeService.BTCChinaTradeHistoryParams();
    params.setPageLength(10);
    Trades trades = tradeService.getTradeHistory(params);
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
