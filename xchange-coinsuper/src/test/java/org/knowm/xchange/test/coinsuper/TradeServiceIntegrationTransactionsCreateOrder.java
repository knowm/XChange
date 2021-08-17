package org.knowm.xchange.test.coinsuper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsuper.CoinsuperExchange;
import org.knowm.xchange.coinsuper.service.CoinsuperTradeHistoryParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class TradeServiceIntegrationTransactionsCreateOrder {
  public static void main(String[] args) {
    try {
      getTradeHistory();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createLimitOrder() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    TradeService tradeService = coinsuper.getTradeService();

    try {
      // place a limit buy order
      LimitOrder limitOrder =
          new LimitOrder(
              (OrderType.ASK),
              new BigDecimal("1.5"), // qty
              CurrencyPair.ETH_BTC,
              null,
              null,
              new BigDecimal("0.08000000"));

      String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
      System.out.println("======createLimitOrder======");
      System.out.println("Limit Order return order id: " + limitOrderReturnValue);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createMarketOrder() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    TradeService tradeService = coinsuper.getTradeService();

    try {
      // place a limit buy order
      MarketOrder marketOrder =
          new MarketOrder(
              (OrderType.BID),
              new BigDecimal("30513299.8408"),
              CurrencyPair.XRP_BTC,
              null,
              null,
              null,
              null,
              null,
              null);

      String result = tradeService.placeMarketOrder(marketOrder);
      System.out.println("createMarketOrder return value: " + result);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void cancelOrder() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    TradeService tradeService = coinsuper.getTradeService();

    try {
      // Cancel the added order
      String orderId = "1608586238226747393";
      boolean cancelResult = tradeService.cancelOrder(orderId);
      System.out.println("Canceling returned " + cancelResult);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getOpenOrders() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    TradeService tradeService = coinsuper.getTradeService();

    try {
      final OpenOrdersParamCurrencyPair openOrdersParamsOne =
          (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
      openOrdersParamsOne.setCurrencyPair(CurrencyPair.XRP_BTC);

      final OpenOrdersParamCurrencyPair openOrdersParamsBtcUsd =
          (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
      openOrdersParamsBtcUsd.setCurrencyPair(CurrencyPair.ETC_BTC);

      final OpenOrdersParams openOrdersParamsAll = tradeService.createOpenOrdersParams();

      printOpenOrders(tradeService, openOrdersParamsAll);
      printOpenOrders(tradeService, openOrdersParamsOne);
      // printOpenOrders(tradeService, openOrdersParamsBtcUsd);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void printOpenOrders(TradeService tradeService, OpenOrdersParams openOrdersParams)
      throws IOException {
    OpenOrders openOrders = tradeService.getOpenOrders(openOrdersParams);
    System.out.printf("Open Orders for %s: %s%n", openOrdersParams, openOrders);
  }

  // getOrder(String... orderIds)
  // trades
  private static void getOrder() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    TradeService tradeService = coinsuper.getTradeService();

    try {
      String orderNoList = "1608661340536594433,1608661662038384641";

      Collection<Order> openOrders = tradeService.getOrder(orderNoList);
      System.out.printf("Open Orders for %s: %s%n", orderNoList, openOrders);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getTradeHistory() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    TradeService tradeService = coinsuper.getTradeService();

    try {
      String orderNoList = "1608755608149188609,1608755698308898817";
      CoinsuperTradeHistoryParams params =
          (CoinsuperTradeHistoryParams) tradeService.createTradeHistoryParams();
      params.setOrderNoList(orderNoList);

      Trades trades = tradeService.getTradeHistory(params);

      System.out.println(trades);

      System.out.printf("Open Orders for %s: %s%n", orderNoList, trades);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
} // end class
