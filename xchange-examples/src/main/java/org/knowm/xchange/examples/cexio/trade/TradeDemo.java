package org.knowm.xchange.examples.cexio.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;
import org.knowm.xchange.cexio.service.CexIOTradeServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.cexio.CexIODemoUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;

/** Author: brox Since: 2/6/14 */
public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CexIODemoUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((CexIOTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService)
      throws NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.BID,
            BigDecimal.ONE,
            new CurrencyPair(Currency.GHs, Currency.BTC),
            "",
            null,
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

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }
}
