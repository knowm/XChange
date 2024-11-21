package info.bitrich.xchangestream.bitget;

import static org.assertj.core.api.Assertions.assertThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

class BitgetStreamingMarketDataServiceIntegration extends BitgetStreamingExchangeIT {

  @Test
  void order_book() {
    Observable<OrderBook> observable =
        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USDT, 5);

    TestObserver<OrderBook> testObserver = observable.test();

    OrderBook orderBook = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(orderBook).hasNoNullFieldsOrProperties();
    assertThat(orderBook.getBids()).hasSize(5);
    assertThat(orderBook.getAsks()).hasSize(5);

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
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }
}
