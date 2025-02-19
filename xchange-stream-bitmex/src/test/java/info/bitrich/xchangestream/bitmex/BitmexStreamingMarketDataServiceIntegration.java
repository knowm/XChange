package info.bitrich.xchangestream.bitmex;


import static org.assertj.core.api.Assertions.assertThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public class BitmexStreamingMarketDataServiceIntegration extends BitmexStreamingExchangeIT {

  @Test
  void order_book() {
    Observable<OrderBook> observable =
        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USDT, "10");

    TestObserver<OrderBook> testObserver = observable.test();

    OrderBook orderBook = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(orderBook.getTimeStamp()).isNotNull();
    assertThat(orderBook.getBids()).hasSize(10);
    assertThat(orderBook.getAsks()).hasSize(10);

    // amounts are scaled
    assertThat(orderBook.getBids()).allSatisfy(limitOrder -> {
      assertThat(limitOrder.getOriginalAmount()).hasScaleOf(8);
    });
    assertThat(orderBook.getAsks()).allSatisfy(limitOrder -> {
      assertThat(limitOrder.getOriginalAmount()).hasScaleOf(8);
    });

    // bids should be lower than asks
    assertThat(orderBook.getBids().get(0).getLimitPrice())
        .isLessThan(orderBook.getAsks().get(0).getLimitPrice());
  }

  @Test
  void ticker() {
    Observable<Ticker> observable =
        exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    TestObserver<Ticker> testObserver = observable.test();

    Ticker ticker = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);

    assertThat(ticker.getBidSize()).hasScaleOf(8);
    assertThat(ticker.getAskSize()).hasScaleOf(8);

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void trades() {
    Observable<Trade> observable =
        exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USDT);

    TestObserver<Trade> testObserver = observable.test();

    Trade trade =
        testObserver
            //        .awaitDone(1, TimeUnit.MINUTES)
            .awaitCount(1)
            .values()
            .get(0);

    testObserver.dispose();

    assertThat(trade).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
    assertThat(trade.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
  }


}