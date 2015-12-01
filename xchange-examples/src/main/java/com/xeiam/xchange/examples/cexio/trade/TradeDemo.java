package com.xeiam.xchange.examples.cexio.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;
import com.xeiam.xchange.cexio.service.polling.CexIOTradeServiceRaw;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.cexio.CexIODemoUtils;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Author: brox Since: 2/6/14
 */

public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CexIODemoUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((CexIOTradeServiceRaw) tradeService);

  }

  private static void generic(PollingTradeService tradeService)
      throws NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.BID, BigDecimal.ONE, new CurrencyPair(Currency.GHs, Currency.BTC), "", null,
        new BigDecimal("0.00015600"));
    System.out.println("Trying to place: " + limitOrder);
    String orderId = "0";
    try {
      orderId = tradeService.placeLimitOrder(limitOrder);
      System.out.println("New Limit Order ID: " + orderId);
    } catch (ExchangeException e) {
      System.out.println(e);
    }
    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(orderId);
    System.out.println("Canceling order id=" + orderId + " returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void raw(CexIOTradeServiceRaw tradeService) throws IOException {

    List<CexIOOrder> openOrders = tradeService.getCexIOOpenOrders(new CurrencyPair("NMC", "BTC"));
    System.out.println(openOrders);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }
}
