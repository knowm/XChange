package org.knowm.xchange.examples.anx.v2.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

/** Test placing a limit order at ANX */
public class LimitOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    TradeService tradeService = anx.getTradeService();

    // place a limit order for a random amount of BTC at USD 1.25
    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("2");
    BigDecimal limitPrice = new BigDecimal("921");

    LimitOrder limitOrder =
        new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_USD, "", null, limitPrice);

    String orderID = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order ID: " + orderID);

    // get open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    for (LimitOrder openOrder : openOrders.getOpenOrders()) {
      System.out.println(openOrder.toString());
    }
  }
}
