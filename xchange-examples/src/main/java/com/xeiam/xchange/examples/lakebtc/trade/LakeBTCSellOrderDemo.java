package com.xeiam.xchange.examples.lakebtc.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Created by Cristi on 12/22/2014.
 */
public class LakeBTCSellOrderDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    PollingTradeService tradeService = lakebtcExchange.getPollingTradeService();

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((Order.OrderType.ASK), new BigDecimal(".01"), CurrencyPair.BTC_LTC, "", null, new BigDecimal("51.25"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());
  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCTradeServiceRaw tradeService = (LakeBTCTradeServiceRaw) lakeBtcExchange.getPollingTradeService();

    System.out.println("Open Orders: " + tradeService.getLakeBTCOrders());

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((Order.OrderType.ASK), new BigDecimal(".01"), CurrencyPair.BTC_LTC, "", null, new BigDecimal("51.25"));
    LakeBTCOrderResponse limitOrderReturnValue = tradeService.placeLakeBTCLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getLakeBTCOrders());
  }
}
