package com.xeiam.xchange.examples.bleutrade.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.bleutrade.BleutradeDemoUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BleutradeTradeDemo {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException, InterruptedException {

    Exchange bleutrade = BleutradeDemoUtils.getExchange();
    PollingTradeService tradeService = bleutrade.getPollingTradeService();

    generic(tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException, InterruptedException {

    // LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, CurrencyPair.LTC_BTC).limitPrice(new BigDecimal("0.002")).tradableAmount(new BigDecimal("1")).build();
    //
    // String orderId = tradeService.placeLimitOrder(limitOrder);
    // System.out.println(orderId);
    //
    // System.out.println(tradeService.getOpenOrders());
    // Thread.sleep(1000);
    // System.out.println(tradeService.cancelOrder(orderId));
    // Thread.sleep(1000);
    // System.out.println(tradeService.getOpenOrders());

    System.out.println(tradeService.getTradeHistory());
  }

}
