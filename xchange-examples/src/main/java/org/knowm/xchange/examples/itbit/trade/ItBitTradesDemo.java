package org.knowm.xchange.examples.itbit.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.itbit.v1.ItBitExchange;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Tests all calls to itBit
 */
public class ItBitTradesDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange itbit = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());
    ExchangeSpecification defaultExchangeSpecification = itbit.getDefaultExchangeSpecification();

    defaultExchangeSpecification.setUserName("userId/walletId");
    defaultExchangeSpecification.setApiKey("xxx");
    defaultExchangeSpecification.setSecretKey("xxx");

    itbit.applySpecification(defaultExchangeSpecification);

    // get all services
    MarketDataService marketDataService = itbit.getMarketDataService();
    AccountService accout = itbit.getAccountService();
    TradeService trades = itbit.getTradeService();

    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair("XBT", "USD"));
    System.out.println("BIDS: " + orderBook.getBids());
    System.out.println("ASKS: " + orderBook.getAsks());

    Trades trades2 = marketDataService.getTrades(new CurrencyPair("XBT", "USD"), 22233);
    System.out.println("Current trades:" + trades2);

    AccountInfo accountInfo = accout.getAccountInfo();

    System.out.println(accountInfo);
    OpenOrders openOrders = trades.getOpenOrders();
    System.out.println("open orders: " + openOrders);

    for (LimitOrder o : openOrders.getOpenOrders()) {
      System.out.println("Order " + o.getId() + " .. cancelling!");
      System.out.println(trades.cancelOrder(o.getId()));

    }

    String placeLimitOrder1 = trades.placeLimitOrder(
        new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(300)));
    String placeLimitOrder2 = trades.placeLimitOrder(
        new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(350)));
    String placeLimitOrder3 = trades.placeLimitOrder(
        new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(360)));
    String placeLimitOrder4 = trades.placeLimitOrder(
        new LimitOrder(OrderType.BID, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(370)));

    System.out.println("limit order id " + placeLimitOrder1);
    System.out.println("limit order id " + placeLimitOrder2);
    System.out.println("limit order id " + placeLimitOrder3);
    System.out.println("limit order id " + placeLimitOrder4);

    trades.placeLimitOrder(
        new LimitOrder(OrderType.ASK, BigDecimal.valueOf(10), new CurrencyPair("XBT", "USD"), "0", new Date(), BigDecimal.valueOf(770)));

    System.out.println("Cancelling " + placeLimitOrder1);
    trades.cancelOrder(placeLimitOrder1);

    Trades tradeHistory = trades.getTradeHistory(trades.createTradeHistoryParams());
    System.out.println("Trade history: " + tradeHistory);
  }
}