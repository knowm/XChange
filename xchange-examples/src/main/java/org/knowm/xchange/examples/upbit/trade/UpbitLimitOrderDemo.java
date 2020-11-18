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
    generic(upbit.getTradeService());
  }

  private static void generic(TradeService tradeService) throws IOException, InterruptedException {

    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, new CurrencyPair(Currency.ETH, Currency.KRW))
            .originalAmount(new BigDecimal("0.01"))
            .limitPrice(new BigDecimal("200000"))
            .build();
    String id = tradeService.placeLimitOrder(limitOrder);
    Thread.sleep(3000);
    Collection<Order> orders = tradeService.getOrder(id);
    orders.forEach(order -> System.out.println(order));
    System.out.println(tradeService.cancelOrder(id));
  }
}
