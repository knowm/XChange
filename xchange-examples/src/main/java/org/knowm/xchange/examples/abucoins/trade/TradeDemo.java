package org.knowm.xchange.examples.abucoins.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsOrderRequest;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.abucoins.service.AbucoinsTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.abucoins.AbucoinsDemoUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;

/** Author: bryant_harris */
public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = AbucoinsDemoUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((AbucoinsTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService)
      throws NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.BID,
            BigDecimal.ONE,
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("100"));
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

    UserTrades userTrades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println(userTrades);
  }

  private static void raw(AbucoinsTradeServiceRaw tradeService) throws IOException {

    AbucoinsOrder[] openOrders =
        tradeService.getAbucoinsOrders(
            new AbucoinsOrderRequest(
                AbucoinsAdapters.adaptCurrencyPairToProductID(CurrencyPair.BTC_USD)));
    System.out.println(Arrays.asList(openOrders));
  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }
}
