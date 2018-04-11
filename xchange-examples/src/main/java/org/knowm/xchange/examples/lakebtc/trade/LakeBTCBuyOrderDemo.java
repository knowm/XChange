package org.knowm.xchange.examples.lakebtc.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import org.knowm.xchange.lakebtc.service.LakeBTCTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

/** Created by Cristi on 12/22/2014. */
public class LakeBTCBuyOrderDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    TradeService tradeService = lakebtcExchange.getTradeService();

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (Order.OrderType.BID),
            new BigDecimal(".01"),
            CurrencyPair.BTC_LTC,
            "",
            null,
            new BigDecimal("51.25"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    // place a market buy order
    MarketOrder marketOrder =
        new MarketOrder(
            (Order.OrderType.BID), new BigDecimal(".01"), CurrencyPair.BTC_LTC, "", null);
    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Limit Order return value: " + marketOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());
  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCTradeServiceRaw tradeService =
        (LakeBTCTradeServiceRaw) lakeBtcExchange.getTradeService();

    System.out.println("Open Orders: " + Arrays.toString(tradeService.getLakeBTCOrders()));

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (Order.OrderType.BID),
            new BigDecimal(".01"),
            CurrencyPair.BTC_LTC,
            "",
            null,
            new BigDecimal("51.25"));
    LakeBTCOrderResponse limitOrderReturnValue = tradeService.placeLakeBTCLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    // place a market buy order
    MarketOrder marketOrder =
        new MarketOrder(
            (Order.OrderType.BID), new BigDecimal(".01"), CurrencyPair.BTC_LTC, "", null);
    LakeBTCOrderResponse marketOrderReturnValue = tradeService.placeLakeBTCMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + marketOrderReturnValue);

    System.out.println("Open Orders: " + Arrays.toString(tradeService.getLakeBTCOrders()));
  }
}
