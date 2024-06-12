package org.knowm.xchange.dto.marketdata;

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
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class ConcurrencyTest {

  static Instrument inst = new CurrencyPair("BTC/USDT");

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    OrderBook orderBook1 = new OrderBook(new Date(), initOrderBookAsks(), initOrderBookBids(),
        true);
    OrderBook orderBook2 = new OrderBook(new Date(), initOrderBookAsks(), initOrderBookBids(),
        true);
    OrderBookOld orderBookOld = new OrderBookOld(new Date(), initOrderBookAsks(),
        initOrderBookBids(), true);
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
    newWay(orderBook1, executor);
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
    oldWay(orderBook2, executor);
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
    oldOB(orderBookOld, executor);
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    if (orderBook1.getAsks().get(0).getOriginalAmount()
        .equals(orderBook2.getAsks().get(0).getOriginalAmount())
        && orderBook1.getAsks().get(0).getOriginalAmount()
        .equals(orderBookOld.getAsks().get(0).getOriginalAmount())) {
      System.out.println("OrderBooks equals");
    }
  }


  private static List<LimitOrder> initOrderBookAsks() {
    List<LimitOrder> asks = new ArrayList<>();
    asks.add(new LimitOrder(OrderType.ASK, new BigDecimal(1), inst, "", new Date(),
        new BigDecimal(103)));
    asks.add(new LimitOrder(OrderType.ASK, new BigDecimal(1), inst, "", new Date(),
        new BigDecimal(102)));
    asks.add(new LimitOrder(OrderType.ASK, new BigDecimal(1), inst, "", new Date(),
        new BigDecimal(101)));
    return asks;
  }

  private static List<LimitOrder> initOrderBookBids() {
    List<LimitOrder> bids = new ArrayList<>();
    bids.add(
        new LimitOrder(OrderType.BID, new BigDecimal(1), inst, "", new Date(), new BigDecimal(99)));
    bids.add(
        new LimitOrder(OrderType.BID, new BigDecimal(1), inst, "", new Date(), new BigDecimal(98)));
    bids.add(
        new LimitOrder(OrderType.BID, new BigDecimal(1), inst, "", new Date(), new BigDecimal(97)));
    return bids;
  }

  private static void newWay(OrderBook orderBook, ThreadPoolExecutor executor)
      throws InterruptedException {
    System.out.printf("OrderBook before %s%n", orderBook);
    for (int i = 0; i < 50; i++) {
      executor.execute(() -> updateOrderBook1(orderBook, false));
      executor.execute(() -> readOrderBook(orderBook, false));
      executor.execute(() -> updateOrderBook2(orderBook, false));
    }
    executor.shutdown();
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    System.out.printf("OrderBook after %s%n", orderBook);
  }

  private static void oldWay(OrderBook orderBook, ThreadPoolExecutor executor)
      throws InterruptedException {
    System.out.printf("OrderBook before %s%n", orderBook);
    for (int i = 0; i < 50; i++) {
      executor.execute(() -> updateOrderBook1(orderBook, true));
      executor.execute(() -> readOrderBook(orderBook, true));
      executor.execute(() -> updateOrderBook2(orderBook, true));
    }
    executor.shutdown();
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    System.out.printf("OrderBook after %s%n", orderBook);
  }

  private static void oldOB(OrderBookOld orderBookOld, ThreadPoolExecutor executor)
      throws InterruptedException {
    System.out.printf("OrderBookOld before %s%n", orderBookOld);
    for (int i = 0; i < 50; i++) {
      executor.execute(() -> updateOrderBookOld1(orderBookOld));
      executor.execute(() -> readOrderBookOld(orderBookOld));
      executor.execute(() -> updateOrderBookOld2(orderBookOld));
    }
    executor.shutdown();
    executor.awaitTermination(100L, TimeUnit.SECONDS);
    System.out.printf("OrderBookOld after %s%n", orderBookOld);
  }

  public static void updateOrderBook1(OrderBook orderBook, boolean oldWay) {
    Random rand = new Random(123);
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
  }

  public static void updateOrderBookOld1(OrderBookOld orderBookOld) {
    Random rand = new Random(123);
    for (int i = 0; i < 100000; i++) {
      OrderBookUpdate orderBookUpdateAsk = new OrderBookUpdate(OrderType.ASK, new BigDecimal(0),
          inst, new BigDecimal(101), new Date(), new BigDecimal(rand.nextInt()));
      OrderBookUpdate orderBookUpdateBid = new OrderBookUpdate(OrderType.BID, new BigDecimal(0),
          inst, new BigDecimal(99), new Date(), new BigDecimal(rand.nextInt()));
      synchronized (orderBookOld) {
        orderBookOld.update(orderBookUpdateAsk);
        orderBookOld.update(orderBookUpdateBid);
      }
    }
  }

  private static void updateOrderBook2(OrderBook orderBook, boolean oldWay) {
    Random rand = new Random(123);
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
  }

  private static void updateOrderBookOld2(OrderBookOld orderBookOld) {
    Random rand = new Random(123);
    for (int i = 0; i < 100000; i++) {
      LimitOrder bookUpdateAsk = new LimitOrder(OrderType.ASK, new BigDecimal(rand.nextInt()),
          inst, "", new Date(), new BigDecimal(101));
      LimitOrder bookUpdateBid = new LimitOrder(OrderType.BID, new BigDecimal(rand.nextInt()),
          inst, "", new Date(), new BigDecimal(99));
      synchronized (orderBookOld) {
        orderBookOld.update(bookUpdateAsk);
        orderBookOld.update(bookUpdateBid);
      }
    }
  }

  private static void readOrderBook(OrderBook orderBook, boolean oldWay) {
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
  }

  private static void readOrderBookOld(OrderBookOld orderBookOld) {
    for (int i = 0; i < 1200000; i++) {
      int temp = 0;
      synchronized (orderBookOld) {
        for (LimitOrder ask : orderBookOld.getAsks()) {
          temp += ask.hashCode();
        }
        for (LimitOrder bid : orderBookOld.getBids()) {
          temp += bid.hashCode();
        }
      }
    }
  }

}

