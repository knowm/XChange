package com.xeiam.xchange.examples.anx.v2.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Test placing a limit order at ANX
 */
public class LimitOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = anx.getPollingTradeService();

    // place a limit order for a random amount of BTC at USD 1.25
    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("2");
    BigDecimal limitPrice = new BigDecimal("921");

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_USD, "", null, limitPrice);

    String orderID = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order ID: " + orderID);

    // get open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    for (LimitOrder openOrder : openOrders.getOpenOrders()) {
      System.out.println(openOrder.toString());
    }

  }
}
