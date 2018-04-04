package org.knowm.xchange.examples.quoine.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.quoine.QuoineExamplesUtils;
import org.knowm.xchange.quoine.dto.trade.Model;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;
import org.knowm.xchange.quoine.service.QuoineTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = QuoineExamplesUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((QuoineTradeServiceRaw) tradeService);
  }

  private static void raw(QuoineTradeServiceRaw tradeServiceRaw) throws IOException {

    // place a limit buy order
    QuoineOrderResponse quoinePlaceOrderResponse =
        tradeServiceRaw.placeLimitOrder(
            CurrencyPair.BTC_USD, "sell", new BigDecimal(".1"), new BigDecimal("250.00"));
    System.out.println("QuoineOrderResponse return value: " + quoinePlaceOrderResponse.toString());

    // cancel the order
    String orderID = quoinePlaceOrderResponse.getId();
    QuoineOrderResponse quoineOrderResponse = tradeServiceRaw.cancelQuoineOrder(orderID);
    System.out.println(quoineOrderResponse.toString());

    // list all orders
    QuoineOrdersList quoineOrdersList = tradeServiceRaw.listQuoineOrders();
    for (Model model : quoineOrdersList.getModels()) {
      System.out.println(model.toString());
    }
  }

  private static void generic(TradeService tradeService) throws IOException {

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal(".1"),
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("200.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    // cancel the order
    boolean success = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Order cancelled? " + success);

    // list open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    for (LimitOrder openOrder : openOrders.getOpenOrders()) {
      System.out.println(openOrder.toString());
    }
  }
}
