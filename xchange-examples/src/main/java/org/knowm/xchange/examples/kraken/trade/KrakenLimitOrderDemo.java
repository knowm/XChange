package org.knowm.xchange.examples.kraken.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.service.KrakenTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

/** Test placing a limit order at Kraken */
public class KrakenLimitOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    TradeService tradeService = krakenExchange.getTradeService();

    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("65.25");

    LimitOrder limitOrder =
        new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_LTC, "", null, price);

    String orderID = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order ID: " + orderID);
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getTradeService();

    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("65.25");

    LimitOrder limitOrder =
        new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_LTC, "", null, price);

    KrakenOrderResponse orderResponse = tradeService.placeKrakenLimitOrder(limitOrder);
    System.out.println("Limit Order response: " + orderResponse);
  }
}
