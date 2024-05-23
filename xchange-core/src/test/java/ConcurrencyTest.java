import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.var;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class ConcurrencyTest {

  static Instrument inst = new CurrencyPair("BTC/USDT");

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();
    OrderBook orderBook;
    asks.add(new LimitOrder(OrderType.ASK, new BigDecimal(1), inst, "", new Date(),
        new BigDecimal(103)));
    asks.add(new LimitOrder(OrderType.ASK, new BigDecimal(1), inst, "", new Date(),
        new BigDecimal(102)));
    asks.add(new LimitOrder(OrderType.ASK, new BigDecimal(1), inst, "", new Date(),
        new BigDecimal(101)));
    bids.add(
        new LimitOrder(OrderType.BID, new BigDecimal(1), inst, "", new Date(), new BigDecimal(99)));
    bids.add(
        new LimitOrder(OrderType.BID, new BigDecimal(1), inst, "", new Date(), new BigDecimal(98)));
    bids.add(
        new LimitOrder(OrderType.BID, new BigDecimal(1), inst, "", new Date(), new BigDecimal(97)));
    orderBook = new OrderBook(new Date(), asks, bids, true);
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
    newWay(orderBook, executor);
    executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
    oldWay(orderBook,executor);

  }

  private static void newWay(OrderBook orderBook, ThreadPoolExecutor executor) throws InterruptedException {
    System.out.printf("OrderBook before %s%n", orderBook);
    for (int i = 0; i < 50; i++) {
      executor.execute(() -> updateOrderBook1(orderBook, false));
      executor.execute(() -> readOrderBook(orderBook, false));
      executor.execute(() -> updateOrderBook2(orderBook, false));
      System.out.println("thread running " + i);
    }
    executor.shutdown();
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    System.out.printf("OrderBook after %s%n", orderBook);
  }

  private static void oldWay(OrderBook orderBook, ThreadPoolExecutor executor) throws InterruptedException {
    System.out.printf("OrderBook before %s%n", orderBook);
    for (int i = 0; i < 50; i++) {
      executor.execute(() -> updateOrderBook1(orderBook, true));
      executor.execute(() -> readOrderBook(orderBook, true));
      executor.execute(() -> updateOrderBook2(orderBook, true));
      System.out.println("thread running " + i);
    }
    executor.shutdown();
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    System.out.printf("OrderBook after %s%n", orderBook);
  }

  public static void updateOrderBook1(OrderBook orderBook, boolean oldWay) {
    Random rand = new Random();
    System.out.println("updateOrderBook1 method start");
    for (int i = 0; i < 100000; i++) {
      OrderBookUpdate orderBookUpdateAsk = new OrderBookUpdate(OrderType.ASK, new BigDecimal(0),
          inst, new BigDecimal(101), new Date(), new BigDecimal(rand.nextInt()));
      OrderBookUpdate orderBookUpdateBid = new OrderBookUpdate(OrderType.BID, new BigDecimal(0),
          inst, new BigDecimal(99), new Date(), new BigDecimal(rand.nextInt()));
      if (oldWay) {
        synchronized (orderBook) {
          orderBook.update(orderBookUpdateAsk);
          orderBook.update(orderBookUpdateBid);
        }
      } else {
        orderBook.update(orderBookUpdateAsk);
        orderBook.update(orderBookUpdateBid);
      }
    }
    System.out.println("updateOrderBook1 method end");
  }

  private static void updateOrderBook2(OrderBook orderBook, boolean oldWay) {
    Random rand = new Random();
    System.out.println("updateOrderBook2 method start");
    for (int i = 0; i < 100000; i++) {
      LimitOrder bookUpdateAsk = new LimitOrder(OrderType.ASK, new BigDecimal(rand.nextInt()),
          inst, "", new Date(), new BigDecimal(101));
      LimitOrder bookUpdateBid = new LimitOrder(OrderType.BID, new BigDecimal(rand.nextInt()),
          inst, "", new Date(), new BigDecimal(99));
      if (oldWay) {
        synchronized (orderBook) {
          orderBook.update(bookUpdateAsk);
          orderBook.update(bookUpdateBid);
        }
      } else {
        orderBook.update(bookUpdateAsk);
        orderBook.update(bookUpdateBid);
      }
    }
    System.out.println("updateOrderBook2 method end");
  }

  private static void readOrderBook(OrderBook orderBook, boolean oldWay) {
    System.out.println("readOrderBook method start");
    for (int i = 0; i < 1200000; i++) {
      int temp = 0;
      if (oldWay) {
        synchronized (orderBook) {
          for (LimitOrder ask : orderBook.getAsks()) {
            temp += ask.hashCode();
          }
          for (LimitOrder bid : orderBook.getBids()) {
            temp += bid.hashCode();
          }
        }
      } else {
        var stamp = orderBook.lock.readLock();
        for (LimitOrder ask : orderBook.getAsks()) {
          temp += ask.hashCode();
        }
        for (LimitOrder bid : orderBook.getBids()) {
          temp += bid.hashCode();
        }
        orderBook.lock.unlockRead(stamp);
      }
    }
    System.out.println("readOrderBook method end");
  }

}
