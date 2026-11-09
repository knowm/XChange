package org.knowm.xchange.coinmate.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.CoinmateUtils;
import org.knowm.xchange.coinmate.ExchangeUtils;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOrderHistory;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOrderHistoryEntry;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistoryEntry;
import org.knowm.xchange.currency.CurrencyPair;

class TradeServiceRawTest {

  @Test
  void transactionHistory() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if configuration is not available
    }
    assertThat(exchange).isNotNull();
    CoinmateTradeServiceRaw service = (CoinmateTradeServiceRaw) exchange.getTradeService();
    assertThat(service).isNotNull();
    CoinmateTransactionHistory transactionHistory =
        service.getCoinmateTransactionHistory(
            0, 1000, "DESC", 1612134000000L, 1614783942000L, null);
    assertThat(transactionHistory).isNotNull();
    assertThat(transactionHistory.getData()).isNotNull();
    //    System.out.println("Got " + transactionHistory.getData().size() + " transactions.");
    for (CoinmateTransactionHistoryEntry transaction : transactionHistory.getData()) {
      //      System.out.println(transaction.getAmount() + " " + transaction.getAmountCurrency());
    }
  }

  @Test
  void transactionHistoryNullTimestamp() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if configuration is not available
    }
    assertThat(exchange).isNotNull();
    CoinmateTradeServiceRaw service = (CoinmateTradeServiceRaw) exchange.getTradeService();
    assertThat(service).isNotNull();
    CoinmateTransactionHistory transactionHistory =
        service.getCoinmateTransactionHistory(0, 1000, "DESC", null, null, null);
    assertThat(transactionHistory).isNotNull();
    assertThat(transactionHistory.getData()).isNotNull();
    //    System.out.println("Got " + transactionHistory.getData().size() + " transactions.");
    for (CoinmateTransactionHistoryEntry transaction : transactionHistory.getData()) {
      //      System.out.println(transaction.getAmount() + " " + transaction.getAmountCurrency());
    }
  }

  @Test
  void orderHistory() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if configuration is not available
    }
    assertThat(exchange).isNotNull();
    CoinmateTradeServiceRaw service = (CoinmateTradeServiceRaw) exchange.getTradeService();
    assertThat(service).isNotNull();
    CoinmateOrderHistory orderHistory =
        service.getCoinmateOrderHistory(CoinmateUtils.getPair(CurrencyPair.BTC_CZK), null);
    assertThat(orderHistory).isNotNull();
    assertThat(orderHistory.getData()).isNotNull();
    //    System.out.println("Got " + orderHistory.getData().size() + " orders.");
    for (CoinmateOrderHistoryEntry transaction : orderHistory.getData()) {
      //      System.out.println(transaction);
    }
  }
}
