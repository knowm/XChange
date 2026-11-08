package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.XBT_USD;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.mockito.InOrder;

class KrakenStreamingMarketDataServiceTest {

  private final KrakenStreamingService service = mock(KrakenStreamingService.class);
  private final KrakenStreamingMarketDataService marketDataService =
      new KrakenStreamingMarketDataService(service, false);
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  @Test
  void resubscriptionStartsWithItsOwnSnapshot() throws Exception {
    JsonNode firstSnapshot =
        mapper.readTree(
            "[1,{\"bs\":[[\"100\",\"1\",\"1\"]],\"as\":[[\"101\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]");
    JsonNode secondSnapshot =
        mapper.readTree(
            "[1,{\"bs\":[[\"200\",\"3\",\"2\"]],\"as\":[[\"201\",\"4\",\"2\"]]},\"book-10\",\"XBT/USD\"]");
    AtomicInteger subscriptions = new AtomicInteger();
    when(service.subscribeChannel("book-XBT/USD", 10))
        .thenReturn(
            Observable.defer(
                () ->
                    Observable.just(
                            subscriptions.getAndIncrement() == 0 ? firstSnapshot : secondSnapshot)
                        .concatWith(Observable.never())));

    Observable<OrderBook> orderBooks = marketDataService.getOrderBook(XBT_USD, 10);
    TestObserver<OrderBook> first = orderBooks.test();
    first.assertValueCount(1).assertNoErrors();
    first.dispose();

    TestObserver<OrderBook> second = orderBooks.test();
    try {
      second.assertValueCount(1).assertNoErrors();
      OrderBook book = second.values().get(0);
      assertThat(book.getBids()).hasSize(1);
      assertThat(book.getBids().get(0).getLimitPrice()).isEqualByComparingTo("200");
      assertThat(book.getBids().get(0).getOriginalAmount()).isEqualByComparingTo("3");
      assertThat(book.getAsks()).hasSize(1);
      assertThat(book.getAsks().get(0).getLimitPrice()).isEqualByComparingTo("201");
      assertThat(book.getAsks().get(0).getOriginalAmount()).isEqualByComparingTo("4");
      assertThat(book.getTimeStamp()).isEqualTo(Instant.ofEpochSecond(2));
      verify(service).subscribeChannel("book-XBT/USD", 10);
    } finally {
      second.dispose();
    }
  }

  // Defensive check only. The real channel is single-threaded: NettyStreamingService delivers
  // every message on the connection's event loop thread, so subscribers on the shared channel
  // never run the adapter concurrently. This mock emits from two executor threads to show that
  // each subscription keeps its own book even if that ever changes; it does not reproduce #4943.
  @Test
  void concurrentSubscriptionsKeepIndependentBooks() throws Exception {
    JsonNode firstSnapshot =
        mapper.readTree(
            "[1,{\"bs\":[[\"100\",\"1\",\"1\"]],\"as\":[[\"101\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]");
    JsonNode secondSnapshot =
        mapper.readTree(
            "[1,{\"bs\":[[\"200\",\"1\",\"1\"]],\"as\":[[\"201\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]");
    JsonNode firstUpdate =
        mapper.readTree(
            "[1,{\"b\":[[\"100\",\"3\",\"2\"]],\"a\":[[\"101\",\"4\",\"2\"]],\"c\":332123687},\"book-10\",\"XBT/USD\"]");
    JsonNode secondUpdate =
        mapper.readTree(
            "[1,{\"b\":[[\"200\",\"5\",\"2\"]],\"a\":[[\"201\",\"6\",\"2\"]],\"c\":475172991},\"book-10\",\"XBT/USD\"]");
    AtomicInteger subscriptions = new AtomicInteger();
    CountDownLatch snapshotsReceived = new CountDownLatch(2);
    when(service.subscribeChannel("book-XBT/USD", 10))
        .thenReturn(
            Observable.create(
                emitter -> {
                  boolean first = subscriptions.getAndIncrement() == 0;
                  emitter.onNext(first ? firstSnapshot : secondSnapshot);
                  snapshotsReceived.countDown();
                  if (!snapshotsReceived.await(5, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Timed out waiting for both snapshots");
                  }
                  emitter.onNext(first ? firstUpdate : secondUpdate);
                  emitter.onComplete();
                }));
    Observable<OrderBook> orderBooks = marketDataService.getOrderBook(XBT_USD, 10);
    ExecutorService executor = Executors.newFixedThreadPool(2);
    try {
      Future<TestObserver<OrderBook>> first = executor.submit(() -> orderBooks.test());
      Future<TestObserver<OrderBook>> second = executor.submit(() -> orderBooks.test());
      TestObserver<OrderBook> firstObserver = first.get(10, TimeUnit.SECONDS);
      TestObserver<OrderBook> secondObserver = second.get(10, TimeUnit.SECONDS);
      firstObserver.assertValueCount(2).assertComplete().assertNoErrors();
      secondObserver.assertValueCount(2).assertComplete().assertNoErrors();
      assertThat(
              Arrays.asList(
                  firstObserver.values().get(1).getBids().get(0).getLimitPrice().toPlainString(),
                  secondObserver.values().get(1).getBids().get(0).getLimitPrice().toPlainString()))
          .containsExactlyInAnyOrder("100", "200");
      for (TestObserver<OrderBook> observer : Arrays.asList(firstObserver, secondObserver)) {
        OrderBook book = observer.values().get(1);
        boolean firstBook = book.getBids().get(0).getLimitPrice().intValue() == 100;
        assertThat(book.getBids()).hasSize(1);
        assertThat(book.getAsks()).hasSize(1);
        assertThat(book.getAsks().get(0).getLimitPrice())
            .isEqualByComparingTo(firstBook ? "101" : "201");
        assertThat(book.getBids().get(0).getOriginalAmount())
            .isEqualByComparingTo(firstBook ? "3" : "5");
        assertThat(book.getAsks().get(0).getOriginalAmount())
            .isEqualByComparingTo(firstBook ? "4" : "6");
        assertThat(book.getTimeStamp()).isEqualTo(Instant.ofEpochSecond(2));
      }
      verify(service).subscribeChannel("book-XBT/USD", 10);
    } finally {
      executor.shutdownNow();
      assertThat(executor.awaitTermination(5, TimeUnit.SECONDS)).isTrue();
    }
  }

  @Test
  void sharedChannelKeepsDeliveringAfterOneSubscriberDisposes() throws Exception {
    PublishSubject<JsonNode> channel = PublishSubject.create();
    when(service.subscribeChannel("book-XBT/USD", 10)).thenReturn(channel.share());
    Observable<OrderBook> orderBooks = marketDataService.getOrderBook(XBT_USD, 10);
    TestObserver<OrderBook> first = orderBooks.test();
    TestObserver<OrderBook> second = orderBooks.test();
    try {
      channel.onNext(
          mapper.readTree(
              "[1,{\"bs\":[[\"100\",\"1\",\"1\"],[\"99\",\"1\",\"1\"],[\"98\",\"1\",\"1\"],[\"97\",\"1\",\"1\"],[\"96\",\"1\",\"1\"],[\"95\",\"1\",\"1\"],[\"94\",\"1\",\"1\"],[\"93\",\"1\",\"1\"],[\"92\",\"1\",\"1\"],[\"91\",\"1\",\"1\"],[\"90\",\"1\",\"1\"]],\"as\":[[\"101\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]"));
      first.assertValueCount(1).assertNoErrors();
      second.assertValueCount(1).assertNoErrors();
      OrderBook retained = first.values().get(0);
      channel.onNext(
          mapper.readTree(
              "[1,{\"b\":[[\"100\",\"3\",\"2\"],[\"99\",\"0\",\"2\"]],\"a\":[[\"101\",\"4\",\"2\"]],\"c\":3657881334},\"book-10\",\"XBT/USD\"]"));
      first.assertValueCount(2).assertNoErrors();
      second.assertValueCount(2).assertNoErrors();
      OrderBook updated = second.values().get(1);
      assertThat(updated.getBids())
          .extracting(order -> order.getLimitPrice().toPlainString())
          .containsExactly("100", "98", "97", "96", "95", "94", "93", "92", "91");
      assertThat(updated.getBids().get(0).getOriginalAmount()).isEqualByComparingTo("3");
      assertThat(updated.getAsks().get(0).getOriginalAmount()).isEqualByComparingTo("4");
      assertThat(updated.getTimeStamp()).isEqualTo(Instant.ofEpochSecond(2));
      assertThat(retained.getBids()).hasSize(10);
      assertThat(retained.getBids().get(0).getOriginalAmount()).isEqualByComparingTo("1");
      assertThat(retained.getAsks().get(0).getOriginalAmount()).isEqualByComparingTo("2");
      first.dispose();
      channel.onNext(
          mapper.readTree("[1,{\"b\":[[\"100\",\"5\",\"3\"]]},\"book-10\",\"XBT/USD\"]"));
      first.assertValueCount(2);
      second.assertValueCount(3).assertNoErrors();
      assertThat(second.values().get(2).getBids().get(0).getOriginalAmount())
          .isEqualByComparingTo("5");
      verify(service).subscribeChannel("book-XBT/USD", 10);
      verify(service, never()).sendMessage(anyString());
    } finally {
      first.dispose();
      second.dispose();
    }
  }

  @Test
  void checksumRecoveryClearsOnlyTheAffectedSubscription() throws Exception {
    PublishSubject<JsonNode> affectedChannel = PublishSubject.create();
    PublishSubject<JsonNode> unaffectedChannel = PublishSubject.create();
    AtomicInteger subscriptions = new AtomicInteger();
    when(service.subscribeChannel("book-XBT/USD", 10))
        .thenReturn(
            Observable.defer(
                () -> subscriptions.getAndIncrement() == 0 ? affectedChannel : unaffectedChannel));
    when(service.getUnsubscribeMessage("book-XBT/USD", 10)).thenReturn("unsubscribe");
    when(service.getSubscribeMessage("book-XBT/USD", 10)).thenReturn("subscribe");
    Observable<OrderBook> orderBooks = marketDataService.getOrderBook(XBT_USD, 10);
    TestObserver<OrderBook> affected = orderBooks.test();
    TestObserver<OrderBook> unaffected = orderBooks.test();
    try {
      JsonNode snapshot =
          mapper.readTree(
              "[1,{\"bs\":[[\"100\",\"1\",\"1\"]],\"as\":[[\"101\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]");
      affectedChannel.onNext(snapshot);
      unaffectedChannel.onNext(snapshot);
      affectedChannel.onNext(mapper.readTree("[1,{\"c\":1},\"book-10\",\"XBT/USD\"]"));
      JsonNode update =
          mapper.readTree("[1,{\"b\":[[\"100\",\"3\",\"2\"]]},\"book-10\",\"XBT/USD\"]");
      affectedChannel.onNext(update);
      affected.assertValueCount(1).assertNoErrors();
      unaffectedChannel.onNext(update);
      unaffected.assertValueCount(2).assertNoErrors();
      assertThat(unaffected.values().get(1).getBids().get(0).getOriginalAmount())
          .isEqualByComparingTo("3");
      affectedChannel.onNext(
          mapper.readTree(
              "[1,{\"bs\":[[\"200\",\"5\",\"3\"]],\"as\":[[\"201\",\"6\",\"3\"]]},\"book-10\",\"XBT/USD\"]"));
      affected.assertValueCount(2).assertNoErrors();
      assertThat(affected.values().get(1).getBids().get(0).getLimitPrice())
          .isEqualByComparingTo("200");
      assertThat(affected.values().get(1).getAsks().get(0).getLimitPrice())
          .isEqualByComparingTo("201");
      verify(service).getUnsubscribeMessage("book-XBT/USD", 10);
      verify(service).getSubscribeMessage("book-XBT/USD", 10);
      InOrder messages = org.mockito.Mockito.inOrder(service);
      messages.verify(service).sendMessage("unsubscribe");
      messages.verify(service).sendMessage("subscribe");
    } finally {
      affected.dispose();
      unaffected.dispose();
    }
  }

  @Test
  void lateSubscriberRequestsSnapshotAndExistingSubscriberReplacesBook() throws Exception {
    PublishSubject<JsonNode> channel = PublishSubject.create();
    when(service.subscribeChannel("book-XBT/USD", 10)).thenReturn(channel.share());
    when(service.getUnsubscribeMessage("book-XBT/USD", 10)).thenReturn("unsubscribe");
    when(service.getSubscribeMessage("book-XBT/USD", 10)).thenReturn("subscribe");
    Observable<OrderBook> orderBooks = marketDataService.getOrderBook(XBT_USD, 10);
    TestObserver<OrderBook> early = orderBooks.test();
    TestObserver<OrderBook> late = null;
    try {
      channel.onNext(
          mapper.readTree(
              "[1,{\"bs\":[[\"100\",\"1\",\"1\"]],\"as\":[[\"101\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]"));
      early.assertValueCount(1).assertNoErrors();
      verify(service, never()).sendMessage(anyString());

      late = orderBooks.test();
      InOrder messages = org.mockito.Mockito.inOrder(service);
      messages.verify(service).sendMessage("unsubscribe");
      messages.verify(service).sendMessage("subscribe");

      channel.onNext(
          mapper.readTree(
              "[1,{\"bs\":[[\"200\",\"3\",\"2\"]],\"as\":[[\"201\",\"4\",\"2\"]]},\"book-10\",\"XBT/USD\"]"));
      late.assertValueCount(1).assertNoErrors();
      early.assertValueCount(2).assertNoErrors();
      for (OrderBook book : Arrays.asList(late.values().get(0), early.values().get(1))) {
        assertThat(book.getBids())
            .extracting(order -> order.getLimitPrice().toPlainString())
            .containsExactly("200");
        assertThat(book.getAsks())
            .extracting(order -> order.getLimitPrice().toPlainString())
            .containsExactly("201");
      }
      verify(service).subscribeChannel("book-XBT/USD", 10);
    } finally {
      early.dispose();
      if (late != null) {
        late.dispose();
      }
    }
  }

  @Test
  void lateSubscriberFromSeparateGetOrderBookCallRequestsSnapshot() throws Exception {
    PublishSubject<JsonNode> channel = PublishSubject.create();
    when(service.subscribeChannel("book-XBT/USD", 10)).thenReturn(channel.share());
    when(service.getUnsubscribeMessage("book-XBT/USD", 10)).thenReturn("unsubscribe");
    when(service.getSubscribeMessage("book-XBT/USD", 10)).thenReturn("subscribe");
    TestObserver<OrderBook> early = marketDataService.getOrderBook(XBT_USD, 10).test();
    TestObserver<OrderBook> late = null;
    try {
      channel.onNext(
          mapper.readTree(
              "[1,{\"bs\":[[\"100\",\"1\",\"1\"]],\"as\":[[\"101\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]"));
      early.assertValueCount(1).assertNoErrors();
      verify(service, never()).sendMessage(anyString());

      // A second getOrderBook call for the same pair and depth shares the Kraken channel, so it
      // must also learn that a book is already live and ask for a snapshot.
      late = marketDataService.getOrderBook(XBT_USD, 10).test();
      InOrder messages = org.mockito.Mockito.inOrder(service);
      messages.verify(service).sendMessage("unsubscribe");
      messages.verify(service).sendMessage("subscribe");

      channel.onNext(
          mapper.readTree(
              "[1,{\"bs\":[[\"200\",\"3\",\"2\"]],\"as\":[[\"201\",\"4\",\"2\"]]},\"book-10\",\"XBT/USD\"]"));
      late.assertValueCount(1).assertNoErrors();
      early.assertValueCount(2).assertNoErrors();
      assertThat(late.values().get(0).getBids().get(0).getLimitPrice()).isEqualByComparingTo("200");
      assertThat(early.values().get(1).getBids().get(0).getLimitPrice())
          .isEqualByComparingTo("200");
    } finally {
      early.dispose();
      if (late != null) {
        late.dispose();
      }
    }
  }

  @Test
  void subscriberJoiningBeforeFirstSnapshotDoesNotResubscribe() throws Exception {
    PublishSubject<JsonNode> channel = PublishSubject.create();
    when(service.subscribeChannel("book-XBT/USD", 10)).thenReturn(channel.share());
    Observable<OrderBook> orderBooks = marketDataService.getOrderBook(XBT_USD, 10);
    TestObserver<OrderBook> first = orderBooks.test();
    TestObserver<OrderBook> second = marketDataService.getOrderBook(XBT_USD, 10).test();
    try {
      // Nobody holds a book yet, so the pending initial snapshot serves both subscribers.
      verify(service, never()).sendMessage(anyString());
      channel.onNext(
          mapper.readTree(
              "[1,{\"bs\":[[\"100\",\"1\",\"1\"]],\"as\":[[\"101\",\"2\",\"1\"]]},\"book-10\",\"XBT/USD\"]"));
      first.assertValueCount(1).assertNoErrors();
      second.assertValueCount(1).assertNoErrors();
      verify(service, never()).sendMessage(anyString());
    } finally {
      first.dispose();
      second.dispose();
    }
  }
}
