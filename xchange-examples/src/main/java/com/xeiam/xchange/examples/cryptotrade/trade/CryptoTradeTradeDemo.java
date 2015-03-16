package com.xeiam.xchange.examples.cryptotrade.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeTransactions;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrder;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrderInfoReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradePlaceOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.cryptotrade.CryptoTradeExampleUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.utils.CertHelper;

public class CryptoTradeTradeDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange exchange = CryptoTradeExampleUtils.createExchange();
    PollingTradeService accountService = exchange.getPollingTradeService();

    generic(accountService);
    raw((CryptoTradeTradeServiceRaw) accountService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException, InterruptedException {

    Trades tradeHistory = tradeService.getTradeHistory();
    System.out.println(tradeHistory);

    Thread.sleep(4000);

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    Thread.sleep(4000);

    LimitOrder limitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(".01"), CurrencyPair.BTC_USD, null, null, new BigDecimal("1000.00"));
    String orderId = tradeService.placeLimitOrder(limitOrder);
    System.out.println(orderId);

    Thread.sleep(4000);

    openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    Thread.sleep(4000);

    boolean isCancelled = tradeService.cancelOrder(orderId);
    System.out.println(isCancelled);

    Thread.sleep(4000);

    openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

  }

  private static void raw(CryptoTradeTradeServiceRaw tradeService) throws IOException, InterruptedException {

    CryptoTradeHistoryQueryParams params = CryptoTradeHistoryQueryParams.getQueryParamsBuilder().withCount(3).build();

    CryptoTradeTrades tradeHistory = tradeService.getCryptoTradeTradeHistory(params);
    System.out.println(tradeHistory);

    Thread.sleep(4000);

    CryptoTradeTransactions transactions = tradeService.getCryptoTradeTransactionHistory(params);
    System.out.println(transactions);

    Thread.sleep(4000);

    CryptoTradeOrders orders = tradeService.getCryptoTradeOrderHistory(params);
    System.out.println(orders);

    Thread.sleep(4000);

    LimitOrder limitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(".01"), CurrencyPair.BTC_USD, null, null, new BigDecimal("1000.00"));
    CryptoTradePlaceOrderReturn orderReturn = tradeService.placeCryptoTradeLimitOrder(limitOrder);
    System.out.println(orderReturn);

    Thread.sleep(4000);

    orders = tradeService.getCryptoTradeOrderHistory(params);
    System.out.println(orders);

    Thread.sleep(4000);

    long orderId = Long.valueOf(limitOrder.getId());
    CryptoTradeOrderInfoReturn orderInfo = tradeService.getCryptoTradeOrderInfo(orderId);
    CryptoTradeOrder cryptoOrder = orderInfo.getOrder();
    System.out.println(cryptoOrder);

    Thread.sleep(4000);

    CryptoTradeCancelOrderReturn cancelOrderReturn = tradeService.cancelCryptoTradeOrder(orderReturn.getOrderId());
    System.out.println(cancelOrderReturn);

    Thread.sleep(4000);

    orders = tradeService.getCryptoTradeOrderHistory(params);
    System.out.println(orders);
  }
}
