package com.xeiam.xchange.examples.kraken.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Test placing a limit order at Kraken
 */
public class KrakenLimitOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    PollingTradeService tradeService = krakenExchange.getPollingTradeService();

    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("65.25");

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_LTC, "", null, price);

    String orderID = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order ID: " + orderID);
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getPollingTradeService();

    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("65.25");

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_LTC, "", null, price);

    KrakenOrderResponse orderResponse = tradeService.placeKrakenLimitOrder(limitOrder);
    System.out.println("Limit Order response: " + orderResponse);
  }
}
