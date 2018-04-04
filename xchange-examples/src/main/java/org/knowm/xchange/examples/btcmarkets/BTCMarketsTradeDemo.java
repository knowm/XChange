package org.knowm.xchange.examples.btcmarkets;

import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.service.BTCMarketsTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

public class BTCMarketsTradeDemo {

  public static void main(String[] args) throws Exception {
    Exchange btcMarketsExchange = BTCMarketsExampleUtils.createTestExchange();
    TradeService tradeService = btcMarketsExchange.getTradeService();

    final OpenOrdersParamCurrencyPair openOrdersParams =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    openOrdersParams.setCurrencyPair(CurrencyPair.BTC_AUD);
    System.out.println("Open Orders: " + tradeService.getOpenOrders(openOrdersParams));

    // Place a limit sell order at a high price
    LimitOrder sellOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal("0.003"),
            CurrencyPair.BTC_AUD,
            null,
            null,
            new BigDecimal("2000"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(sellOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Waiting a while for the order to get registered...");
    Thread.sleep(2000);

    System.out.println("Open Orders: " + tradeService.getOpenOrders(openOrdersParams));
    openOrdersParams.setCurrencyPair(CurrencyPair.LTC_BTC);
    System.out.println("Open Orders for LTC/BTC: " + tradeService.getOpenOrders(openOrdersParams));

    // Cancel the added order.
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    System.out.println("Open Orders: " + tradeService.getOpenOrders(openOrdersParams));

    // An example of a sell market order
    MarketOrder sellMarketOrder =
        new MarketOrder((OrderType.ASK), new BigDecimal("0.003"), CurrencyPair.BTC_AUD, null, null);
    String marketSellOrderId = tradeService.placeMarketOrder(sellMarketOrder);
    System.out.println("Market Order return value: " + marketSellOrderId);

    // An example of a buy limit order.
    LimitOrder buyOrder =
        new LimitOrder(
            (OrderType.BID),
            new BigDecimal("0.002"),
            CurrencyPair.BTC_AUD,
            null,
            null,
            new BigDecimal("240"));
    String buyLimiOrderId = tradeService.placeLimitOrder(buyOrder);
    System.out.println("Limit Order return value: " + buyLimiOrderId);

    // An example of a buy market order
    MarketOrder buyMarketOrder =
        new MarketOrder((OrderType.BID), new BigDecimal("0.004"), CurrencyPair.BTC_AUD, null, null);
    String buyMarketOrderId = tradeService.placeMarketOrder(buyMarketOrder);
    System.out.println("Market Order return value: " + buyMarketOrderId);

    // Get history of executed trades.
    BTCMarketsTradeService.HistoryParams params =
        (BTCMarketsTradeService.HistoryParams) tradeService.createTradeHistoryParams();
    params.setPageLength(10);
    params.setCurrencyPair(CurrencyPair.BTC_AUD);
    final UserTrades tradeHistory = tradeService.getTradeHistory(params);

    System.out.println("Trade history: " + tradeHistory);
  }
}
