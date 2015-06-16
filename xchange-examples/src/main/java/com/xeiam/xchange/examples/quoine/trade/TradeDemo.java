package com.xeiam.xchange.examples.quoine.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.quoine.QuoineExamplesUtils;
import com.xeiam.xchange.quoine.dto.trade.Model;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrderResponse;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrdersList;
import com.xeiam.xchange.quoine.service.polling.QuoineTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = QuoineExamplesUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((QuoineTradeServiceRaw) tradeService);
  }

  private static void raw(QuoineTradeServiceRaw tradeServiceRaw) throws IOException {

    // place a limit buy order
    QuoineOrderResponse quoinePlaceOrderResponse = tradeServiceRaw.placeLimitOrder(CurrencyPair.BTC_USD, "sell", new BigDecimal(".1"),
        new BigDecimal("250.00"));
    System.out.println("QuoineOrderResponse return value: " + quoinePlaceOrderResponse.toString());

    // cancel the order
    String orderID = quoinePlaceOrderResponse.getId();
    QuoineOrderResponse quoineOrderResponse = tradeServiceRaw.cancelQuoineOrder(orderID);
    System.out.println(quoineOrderResponse.toString());

    // list all orders for BTC/USD
    QuoineOrdersList quoineOrdersList = tradeServiceRaw.listQuoineOrders("BTCUSD");
    for (Model model : quoineOrdersList.getModels()) {
      System.out.println(model.toString());
    }
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal(".1"), CurrencyPair.BTC_USD, "", null, new BigDecimal("200.00"));
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
