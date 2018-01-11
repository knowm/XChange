package org.knowm.xchange.examples.binance.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceCancelOrderParams;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.binance.BinanceDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.utils.StreamUtils;

public class BinanceTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BinanceDemoUtils.createExchange();

    generic(exchange);
    raw((BinanceExchange) exchange);
  }

  public static void generic(Exchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.EOS_ETH;
    TradeService tradeService = exchange.getTradeService();
    
    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(pair));
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }

  public static void raw(BinanceExchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.EOS_ETH;
    BinanceTradeService tradeService = (BinanceTradeService) exchange.getTradeService();
    BinanceMarketDataService marketService = (BinanceMarketDataService) exchange.getMarketDataService();
    BinanceAccountService accountService = (BinanceAccountService) exchange.getAccountService();
    
    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(pair);
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
 
    // Get EOS/ETH market price
    BigDecimal tickerPrice = marketService.tickerPrice(pair).getPrice();
    System.out.println("TickerPrice: " + String.format("%.6f", tickerPrice));

    // Set the StopLoss to 5% below the distro price
    BigDecimal stopPrice = new BigDecimal(String.format("%.6f", tickerPrice.doubleValue() * 0.95)); 
    System.out.println("StopLoss:    " + stopPrice);

    // Get the available EOS amount
    Balance balance = accountService.getAccountInfo().getWallet().getBalance(pair.base);
    System.out.println("Balance:    " + String.format("%.6f %s", balance.getAvailable(), balance.getCurrency()));
    
    BigDecimal amount = new BigDecimal("10");
    if (amount.compareTo(balance.getAvailable()) <= 0) {
      
      // Build the StopLimit order
      LimitOrder limitOrder = new LimitOrder.Builder(OrderType.ASK, pair)
          .originalAmount(amount)
          .limitPrice(tickerPrice)
          .stopPrice(stopPrice)
          .build();
      
      // Place the StopLimit order
      String orderId = tradeService.placeStopLimitOrder(limitOrder);
      System.out.println("New orderId: " + orderId);
      
      // Cancel the order again
      BinanceCancelOrderParams params = new BinanceCancelOrderParams(pair, orderId);
      System.out.println("Cancel order: " + tradeService.cancelOrder(params));
    }
  }
}

