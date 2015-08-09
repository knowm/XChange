package com.xeiam.xchange.examples.cointrader;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cointrader.service.polling.CointraderTradeService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class CointraderTradeDemo {

  public static void main(String[] args) throws Exception {
    Exchange cointraderExchange = CointraderExampleUtils.createTestExchange();
    PollingTradeService tradeService = cointraderExchange.getPollingTradeService();

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // Place a limit sell order at high price
    LimitOrder sellOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.03"), CurrencyPair.BTC_USD, null, null, new BigDecimal("2000"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(sellOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // Cancel the added order.
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // Place a limit sell order at low price
    LimitOrder lowSellOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.03"), CurrencyPair.BTC_USD, null, null, new BigDecimal("150.00"));
    String lowOrderReturnValue = tradeService.placeLimitOrder(lowSellOrder);
    System.out.println("Limit Order return value: " + lowOrderReturnValue);

    // An example of a sell market order: set the price to null
    LimitOrder sellMarketOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.03"), CurrencyPair.BTC_USD, null, null, null);
    String marketSellOrderId = tradeService.placeLimitOrder(sellMarketOrder);
    System.out.println("Market Order return value: " + marketSellOrderId);

    // An example of a buy limit order.
    LimitOrder buyOrder = new LimitOrder((OrderType.BID), new BigDecimal("0.028"), CurrencyPair.BTC_USD, null, null, new BigDecimal("230"));
    String buyLimiOrderId = tradeService.placeLimitOrder(buyOrder);
    System.out.println("Limit Order return value: " + buyLimiOrderId);

    // An example of a buy market order: set the price to null.
    LimitOrder buyMarketOrder = new LimitOrder((OrderType.BID), new BigDecimal("0.028"), CurrencyPair.BTC_USD, null, null, null);
    String buyMarketOrderId = tradeService.placeLimitOrder(buyMarketOrder);
    System.out.println("Market Order return value: " + buyMarketOrderId);

    CointraderTradeService.HistoryParams params = (CointraderTradeService.HistoryParams) tradeService.createTradeHistoryParams();
    params.setPageLength(1);
    params.setPageNumber(2);
    //    params.setStartId("33373");
    final UserTrades tradeHistory = tradeService.getTradeHistory(params);

    System.out.println("Trade history: " + tradeHistory);
  }
}
