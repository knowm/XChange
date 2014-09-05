package com.xeiam.xchange.examples.bittrex.v1.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.bittrex.v1.BittrexExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BittrexTradeDemo {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange exchange = BittrexExamplesUtils.getExchange();

    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((BittrexTradeServiceRaw) tradeService);

  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    CurrencyPair pair = new CurrencyPair("ZET", "BTC");
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, pair).setLimitPrice(new BigDecimal("0.00001000")).setTradableAmount(new BigDecimal("100")).build();

    try {
      String uuid = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders());

      System.out.println("Attempting to cancel order " + uuid);
      boolean cancelled = tradeService.cancelOrder(uuid);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      }
      else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void raw(BittrexTradeServiceRaw tradeService) throws IOException {

    CurrencyPair pair = new CurrencyPair("ZET", "BTC");
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, pair).setLimitPrice(new BigDecimal("0.00001000")).setTradableAmount(new BigDecimal("100")).build();

    try {
      String uuid = tradeService.placeBittrexLimitOrder(limitOrder);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getBittrexOpenOrders());

      System.out.println("Attempting to cancel order " + uuid);
      boolean cancelled = tradeService.cancelBittrexLimitOrder(uuid);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      }
      else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getBittrexOpenOrders());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
