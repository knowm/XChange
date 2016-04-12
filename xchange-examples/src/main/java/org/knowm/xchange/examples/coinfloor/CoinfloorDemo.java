package org.knowm.xchange.examples.coinfloor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorExchangeEvent;
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
 * This class shows the ExcutorService way of processing returns from the server. While it does look neat, it suffers from the problem that the
 * requests and responses come on different streams, and responses have to be matched with requests before the data can be used. See the cancel all
 * orders section of this demo. To cancel all orders, a request has to first be made to get all open orders, the response of which will be caught by
 * the eventCatcherThread (MarketDataRunnable). Then, it will process that event, print it out, and then store the event in a secondary queue for
 * retrival by the cancel-all-orders part of this program. This is vastly different from the polling services, where data retrieved is returned
 * directly from the method. This CoinfloorStreamingExchangeService allows for the same retrival methods as the polling services. Please see
 * CoinfloorDemo2 for example code implementing that route of data retrival. (Note: It is possible to mix both.)
 *
 * @author obsessiveOrange
 */
public class CoinfloorDemo {

  public static BlockingQueue<CoinfloorExchangeEvent> secondaryQueue = new LinkedBlockingQueue<CoinfloorExchangeEvent>();

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
    ExchangeStreamingConfiguration exchangeStreamingConfiguration = new CoinfloorStreamingConfiguration(10, 10000, 30000, false, true, true);
    StreamingExchangeService streamingExchangeService = coinfloorExchange.getStreamingExchangeService(exchangeStreamingConfiguration);

    // connect, and authenicate using username/cookie/password provided in exSpec
    streamingExchangeService.connect();

    // start handler for events
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<?> eventCatcherThread = executorService.submit(new MarketDataRunnable(streamingExchangeService, secondaryQueue));

    // request Wallet data (balances)
    ((CoinfloorStreamingExchangeService) streamingExchangeService).getBalances();
    TimeUnit.MILLISECONDS.sleep(1000);

    // request OpenOrders data
    ((CoinfloorStreamingExchangeService) streamingExchangeService).getOrders();
    TimeUnit.MILLISECONDS.sleep(1000);

    // request 30-day trading volume for this user
    ((CoinfloorStreamingExchangeService) streamingExchangeService).getTradeVolume("BTC");
    TimeUnit.MILLISECONDS.sleep(1000);

    // subscribe to ticker feed
    ((CoinfloorStreamingExchangeService) streamingExchangeService).watchTicker("BTC", "GBP");
    TimeUnit.MILLISECONDS.sleep(1000);

    // subscribe to orderbook
    ((CoinfloorStreamingExchangeService) streamingExchangeService).watchOrders("BTC", "GBP");
    TimeUnit.MILLISECONDS.sleep(1000);

    // send two orders, that will (partially) fulfill each other, to generate a trade.
    LimitOrder buyLimitOrder = new LimitOrder(OrderType.BID, new BigDecimal(1), new CurrencyPair("BTC", "GBP"), null, null, new BigDecimal(320));
    ((CoinfloorStreamingExchangeService) streamingExchangeService).placeOrder(buyLimitOrder);
    TimeUnit.MILLISECONDS.sleep(1000);

    LimitOrder sellLimitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(1.52321512784), new CurrencyPair("BTC", "GBP"), null, null,
        new BigDecimal(319));
    ((CoinfloorStreamingExchangeService) streamingExchangeService).placeOrder(sellLimitOrder);
    TimeUnit.MILLISECONDS.sleep(1000);

    // then send another order, that will never be fulfilled
    LimitOrder bigLimitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(1.152), new CurrencyPair("BTC", "GBP"), null, null, new BigDecimal(500));
    ((CoinfloorStreamingExchangeService) streamingExchangeService).placeOrder(bigLimitOrder);
    TimeUnit.MILLISECONDS.sleep(1000);

    // request outcome of theoretical marketOrder
    MarketOrder estMarketOrder = new MarketOrder(OrderType.ASK, new BigDecimal(1), new CurrencyPair("BTC", "GBP"));
    ((CoinfloorStreamingExchangeService) streamingExchangeService).estimateMarketOrder(estMarketOrder);
    TimeUnit.MILLISECONDS.sleep(1000);

    // get user's current open orders, cancel all of them.
    CoinfloorOpenOrders openOrders = (CoinfloorOpenOrders) ((CoinfloorStreamingExchangeService) streamingExchangeService).getOrders()
        .getPayloadItem("raw");
    for (CoinfloorOrder order : openOrders.getOrders()) {
      ((CoinfloorStreamingExchangeService) streamingExchangeService).cancelOrder(order.getId());
      TimeUnit.MILLISECONDS.sleep(1000);
    }

    // unsubscribe to ticker feed
    ((CoinfloorStreamingExchangeService) streamingExchangeService).unwatchTicker("BTC", "GBP");
    TimeUnit.MILLISECONDS.sleep(1000);

    // unsubscribe to orderbook
    ((CoinfloorStreamingExchangeService) streamingExchangeService).unwatchOrders("BTC", "GBP");
    TimeUnit.MILLISECONDS.sleep(1000);

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

    // the thread waits here until the Runnable is done.
    eventCatcherThread.get();

    executorService.shutdown();

    // Disconnect and exit
    System.out.println(Thread.currentThread().getName() + ": Disconnecting...");
    streamingExchangeService.disconnect();
    System.exit(0);
  }

  /**
   * Encapsulates some market data monitoring behavior
   */
  static class MarketDataRunnable implements Runnable {

    private final StreamingExchangeService streamingExchangeService;
    private final BlockingQueue<CoinfloorExchangeEvent> secondaryQueue;

    /**
     * Constructor
     *
     * @param streamingExchangeService
     */
    public MarketDataRunnable(StreamingExchangeService streamingExchangeService, BlockingQueue<CoinfloorExchangeEvent> secondaryQueue) {

      this.streamingExchangeService = streamingExchangeService;
      this.secondaryQueue = secondaryQueue;
    }

    @Override
    public void run() {

      try {

        while (true) {

          CoinfloorExchangeEvent exchangeEvent = ((CoinfloorStreamingExchangeService) streamingExchangeService).getNextEvent();
          System.out.print("\n\n\n\n\n" + new Date() + ":\n");

          switch (exchangeEvent.getEventType()) {
          case AUTHENTICATION:
            System.out.println("Authentication Successful. Server's return: " + exchangeEvent.getData());
            break;

          case USER_WALLET:
            System.out.println("User balances returned: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case USER_TRADE_VOLUME:
            System.out.println("User's trade volume returned: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case SUBSCRIBE_ORDERS:
            System.out.println("Successfully subscribed/unsubscribed to order feed: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case SUBSCRIBE_TICKER:
            System.out.println("Successfully subscribed/unsubscribed to ticker feed: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case USER_ORDERS_LIST:
            System.out.println("User's open orders returned: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case USER_ORDER:
            System.out.println("Successfully placed a new order: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case USER_ORDER_CANCELED:
            System.out.println("Successfully canceled an order: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case USER_MARKET_ORDER_EST:
            System.out.println("Estimated market order result returned: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case USER_WALLET_UPDATE:
            System.out.println("User Balances updated: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case ORDER_ADDED:
            System.out.println("Order added to orderbook: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case TRADE:
            System.out.println("Trade occurred: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case ORDER_CANCELED:
            System.out.println("Order removed from orderbook: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          case TICKER:
            System.out.println("Ticker updated: ");
            System.out.println("Raw Object: " + exchangeEvent.getPayloadItem("raw"));
            System.out.println("Generic Object: " + exchangeEvent.getPayloadItem("generic"));
            break;

          default:
            break;
          }
          secondaryQueue.add(exchangeEvent);

        }
      } catch (InterruptedException e) {
        System.out.println("ERROR in Runnable!!!");
      }

    }
  }
}
