package org.knowm.xchange.coinmate.service;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.ExchangeUtils;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistoryEntry;

public class TradeServiceRawTest {

  @Test
  public void testTransactionHistory() throws IOException {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if configuration is not available
    }
    assertNotNull(exchange);
    CoinmateTradeServiceRaw service = (CoinmateTradeServiceRaw) exchange.getTradeService();
    assertNotNull(service);
    CoinmateTransactionHistory transactionHistory =
        service.getCoinmateTransactionHistory(
            0, 1000, "DESC", 1612134000000L, 1614783942000L, null);
    assertNotNull(transactionHistory);
    assertNotNull(transactionHistory.getData());
    System.out.println("Got " + transactionHistory.getData().size() + " transactions.");
    for (CoinmateTransactionHistoryEntry transaction : transactionHistory.getData()) {
      System.out.println(transaction.getAmount() + " " + transaction.getAmountCurrency());
    }
  }

  @Test
  public void testTransactionHistoryNullTimestamp() throws IOException {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if configuration is not available
    }
    assertNotNull(exchange);
    CoinmateTradeServiceRaw service = (CoinmateTradeServiceRaw) exchange.getTradeService();
    assertNotNull(service);
    CoinmateTransactionHistory transactionHistory =
        service.getCoinmateTransactionHistory(0, 1000, "DESC", null, null, null);
    assertNotNull(transactionHistory);
    assertNotNull(transactionHistory.getData());
    System.out.println("Got " + transactionHistory.getData().size() + " transactions.");
    for (CoinmateTransactionHistoryEntry transaction : transactionHistory.getData()) {
      System.out.println(transaction.getAmount() + " " + transaction.getAmountCurrency());
    }
  }
}
