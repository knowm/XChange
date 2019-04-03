package org.knowm.xchange.examples.upbit.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.upbit.UpbitDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

/** Demonstrate requesting limit order at Upbit */
public class UpbitLimitOrderDemo {

  public static void main(String[] args) throws Exception {
    Exchange upbit = UpbitDemoUtils.createExchange();
    generic(upbit);
  }

  private static void generic(Exchange upbit) throws IOException, InterruptedException {
    // create
    TradeService tradeService = upbit.getTradeService();
    BigDecimal amount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("200000");
    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.BID, amount, new CurrencyPair(Currency.ETH, Currency.KRW), null, null, price);
    String id = tradeService.placeLimitOrder(limitOrder);
    Thread.sleep(3000);
    Collection<Order> orders = tradeService.getOrder(id);
    orders.forEach(order -> System.out.println(order));
    tradeService.cancelOrder(id);
  }
}
