package org.knowm.xchange.examples.coinfloor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorOrder;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorStreamingConfiguration;
import org.knowm.xchange.coinfloor.dto.streaming.trade.CoinfloorOpenOrders;
import org.knowm.xchange.coinfloor.streaming.CoinfloorStreamingExchangeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

/**
 * The purpose of this class is to show an alternative way. The methods themselves DO return data, and thus allows interaction much in the same way as
 * the polling data services. It looks messier, since the request and output are all clustered together, but it allows for much more flexibility when
 * retrieving data (Specifically, look at the section that cancels all orders). Doing it this way removes the need for a secondary queue to pull data
 * that has been handled already by the executorService.
 *
 * @author obsessiveOrange
 */
public class CoinfloorDemo2 {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification exSpec = new ExchangeSpecification(CoinfloorExchange.class);
    exSpec.setUserName("163");
    exSpec.setExchangeSpecificParametersItem("cookie", "X1UC55QE4WXNZMKfP4FfCsxKVfw=");
    exSpec.setPassword("2QvxAyUvPTIX8mrCvH");
    exSpec.setPlainTextUriStreaming("ws://api.coinfloor.co.uk");
    exSpec.setSslUriStreaming("wss://api.coinfloor.co.uk");
    exSpec.setHost("coinfloor.co.uk");
    exSpec.setPort(80);

    Exchange coinfloorExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    // Streaming exchange service can be instantiated in one of two ways:
    // //Using default vars (commented out to prevent conflicts for this demo)
    // StreamingExchangeService streamingExchangeServiceDefault = ((CoinfloorExchange)coinfloorExchange).getStreamingExchangeService();

    // //Or with new vars:
    ExchangeStreamingConfiguration exchangeStreamingConfiguration = new CoinfloorStreamingConfiguration(10, 10000, 30000, false, true, false);
    final StreamingExchangeService streamingExchangeService = coinfloorExchange.getStreamingExchangeService(exchangeStreamingConfiguration);

    // connect, and authenicate using username/cookie/password provided in exSpec
    streamingExchangeService.connect();
    Map<String, Object> resultMap;

    // subscribe to ticker feed
    resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).watchTicker("BTC", "GBP").getPayload();
    System.out.println("\n\n\n\n\nSubscribed to Ticker feed: ");
    System.out.println("Raw Object: " + resultMap.get("raw"));
    System.out.println("Generic Object: " + resultMap.get("generic"));
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    // authenticate for rest of the demo
    ((CoinfloorStreamingExchangeService) streamingExchangeService).authenticate();

    // request Wallet data (balances)
    resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).getBalances().getPayload();
    System.out.println("\n\n\n\n\nUser balances returned: ");
    System.out.println("Raw Object: " + resultMap.get("raw"));
    System.out.println("Generic Object: " + resultMap.get("generic"));
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    // request OpenOrders data
    resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).getOrders().getPayload();
    System.out.println("\n\n\n\n\nUser Open Orders: ");
    System.out.println("Raw Object: " + resultMap.get("raw"));
    System.out.println("Generic Object: " + resultMap.get("generic"));
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    // request 30-day trading volume for this user
    resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).getTradeVolume("BTC").getPayload();
    System.out.println("\n\n\n\n\nUser 30-Day Trade Volume: ");
    System.out.println("Raw Object: " + resultMap.get("raw"));
    System.out.println("Generic Object: " + resultMap.get("generic"));
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    // subscribe to orderbook
    resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).watchOrders("BTC", "GBP").getPayload();
    System.out.println("\n\n\n\n\nSubscribed to OrderBook feed: ");
    System.out.println("Raw Object: " + resultMap.get("raw"));
    System.out.println("Generic Object: " + resultMap.get("generic"));
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    final long startTime = System.currentTimeMillis();
    ArrayList<Thread> threads = new ArrayList<Thread>();

    for (int i = 0; i < 5; i++) {
      threads.add(new Thread(new Runnable() {

        @Override
        public void run() {

          Map<String, Object> resultMap;

          try {
            TimeUnit.MILLISECONDS.sleep((startTime + 500) - System.currentTimeMillis());
          } catch (InterruptedException e) {
          }

          // send two orders, that will (partially) fulfill each other, to generate a trade.
          LimitOrder buyLimitOrder = new LimitOrder(OrderType.BID, new BigDecimal(1), new CurrencyPair("BTC", "GBP"), null, null,
              new BigDecimal(3.20));
          resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).placeOrder(buyLimitOrder).getPayload();
          System.out.println("\n\n\n\n\nBuy Limit Order Placed: ");
          System.out.println("Raw Object: " + resultMap.get("raw"));
          System.out.println("Generic Object: " + resultMap.get("generic"));
          try {
            TimeUnit.MILLISECONDS.sleep(1000);
          } catch (InterruptedException e) {
          }

          // check for new balance before submitting second order
          System.out.println("\n\n\n\n\nCached Account Info: ");
          System.out.println(((CoinfloorStreamingExchangeService) streamingExchangeService).getCachedAccountInfo());

          LimitOrder sellLimitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(1.52321512784), new CurrencyPair("BTC", "GBP"), null, null,
              new BigDecimal(3.19));
          resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).placeOrder(sellLimitOrder).getPayload();
          System.out.println("\n\n\n\n\nSell Limit Order Placed: ");
          System.out.println("Raw Object: " + resultMap.get("raw"));
          System.out.println("Generic Object: " + resultMap.get("generic"));
          try {
            TimeUnit.MILLISECONDS.sleep(1000);
          } catch (InterruptedException e) {
          }

          // check for new balance again
          System.out.println("\n\n\n\n\nCached Account Info: ");
          System.out.println(((CoinfloorStreamingExchangeService) streamingExchangeService).getCachedAccountInfo());

          // then send another order, that will never be fulfilled
          LimitOrder bigLimitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(1.152), new CurrencyPair("BTC", "GBP"), null, null,
              new BigDecimal(500));
          resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).placeOrder(bigLimitOrder).getPayload();
          System.out.println("\n\n\n\n\nBig Limit Order Placed: ");
          System.out.println("Raw Object: " + resultMap.get("raw"));
          System.out.println("Generic Object: " + resultMap.get("generic"));
          try {
            TimeUnit.MILLISECONDS.sleep(1000);
          } catch (InterruptedException e) {
          }

          // check for new balance before submitting second order
          System.out.println("\n\n\n\n\nCached Account Info: ");
          System.out.println(((CoinfloorStreamingExchangeService) streamingExchangeService).getCachedAccountInfo());

          // request outcome of theoretical marketOrder
          MarketOrder estMarketOrder = new MarketOrder(OrderType.ASK, new BigDecimal(1), new CurrencyPair("BTC", "GBP"));
          ((CoinfloorStreamingExchangeService) streamingExchangeService).estimateMarketOrder(estMarketOrder).getPayload();
          System.out.println("\n\n\n\n\nEstimated Market Order: ");
          System.out.println("Raw Object: " + resultMap.get("raw"));
          System.out.println("Generic Object: " + resultMap.get("generic"));
          try {
            TimeUnit.MILLISECONDS.sleep(1000);
          } catch (InterruptedException e) {
          }

        }
      }));
      threads.get(threads.size() - 1).start();
    }

    for (Thread t : threads) {
      t.join();
    }

    // get user's current open orders, cancel all of them.
    CoinfloorOpenOrders openOrders = (CoinfloorOpenOrders) ((CoinfloorStreamingExchangeService) streamingExchangeService).getOrders()
        .getPayloadItem("raw");
    for (CoinfloorOrder order : openOrders.getOrders()) {
      resultMap = ((CoinfloorStreamingExchangeService) streamingExchangeService).cancelOrder(order.getId()).getPayload();
      System.out.println("\n\n\n\n\nCancelled order: ");
      System.out.println("Raw Object: " + resultMap.get("raw"));
      System.out.println("Generic Object: " + resultMap.get("generic"));

    }
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    // check for new balance after orders cancelled
    System.out.println("\n\n\n\n\nCached Account Info: ");
    System.out.println(((CoinfloorStreamingExchangeService) streamingExchangeService).getCachedAccountInfo());

    // unsubscribe to ticker feed
    ((CoinfloorStreamingExchangeService) streamingExchangeService).unwatchTicker("BTC", "GBP").getPayload();
    System.out.println("\n\n\n\n\nUnwatched Ticker: ");
    System.out.println("Raw Object: " + resultMap.get("raw"));
    System.out.println("Generic Object: " + resultMap.get("generic"));
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    // unsubscribe to orderbook
    ((CoinfloorStreamingExchangeService) streamingExchangeService).unwatchOrders("BTC", "GBP").getPayload();
    System.out.println("\n\n\n\n\nUnwatched Orders: ");
    System.out.println("Raw Object: " + resultMap.get("raw"));
    System.out.println("Generic Object: " + resultMap.get("generic"));
    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
    }

    TimeUnit.MINUTES.sleep(1);

    // These next three methods cache all the relevant information, and store them in memory. As new data comes in, it gets updated.
    // This way, the user is not required to update the accountInfo, orderbook, or trades history.
    // These methods are experimental, but still provided for convenience.

    System.out.println("\n\n\n\n\nCached Account Info: ");
    System.out.println(((CoinfloorStreamingExchangeService) streamingExchangeService).getCachedAccountInfo());

    System.out.println("\n\n\n\n\nCached OrderBook: ");
    System.out.println(((CoinfloorStreamingExchangeService) streamingExchangeService).getCachedOrderBook());

    System.out.println("\n\n\n\n\nCached Trades: ");
    System.out.println(((CoinfloorStreamingExchangeService) streamingExchangeService).getCachedTrades());

    // Disconnect and exit
    System.out.println(Thread.currentThread().getName() + ": Disconnecting...");
    streamingExchangeService.disconnect();
    System.exit(0);
  }
}
