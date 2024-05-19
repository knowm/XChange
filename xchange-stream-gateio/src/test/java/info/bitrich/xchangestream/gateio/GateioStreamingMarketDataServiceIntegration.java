package info.bitrich.xchangestream.gateio;

import static org.assertj.core.api.Assertions.assertThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public class GateioStreamingMarketDataServiceIntegration extends GateioStreamingExchangeIT {

  @Test
  void order_book() {
    Observable<OrderBook> observable = exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_USDT, 10, Duration.ofMillis(100));

    TestObserver<OrderBook> testObserver = observable.test();

    OrderBook orderBook = testObserver
        .awaitCount(1)
        .values().get(0);

    testObserver.dispose();

    assertThat(orderBook).hasNoNullFieldsOrProperties();
    assertThat(orderBook.getBids()).hasSize(10);
    assertThat(orderBook.getAsks()).hasSize(10);

    // bids should be lower than asks
    assertThat(orderBook.getBids().get(0).getLimitPrice()).isLessThan(orderBook.getAsks().get(0).getLimitPrice());
  }


  @Test
  void trades() {
    Observable<Trade> observable = exchange
        .getStreamingMarketDataService()
        .getTrades(CurrencyPair.BTC_USDT);

    TestObserver<Trade> testObserver = observable.test();

    Trade trade = testObserver
        .awaitCount(1)
        .values().get(0);

    testObserver.dispose();

    assertThat(trade).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
    assertThat(trade.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);

  }


  @Test
  void ticker() {
    Observable<Ticker> observable = exchange
        .getStreamingMarketDataService()
        .getTicker(CurrencyPair.BTC_USDT);

    TestObserver<Ticker> testObserver = observable.test();

    Ticker ticker = testObserver
        .awaitCount(1)
        .values()
        .get(0);

    testObserver.dispose();

    assertThat(ticker).hasNoNullFieldsOrPropertiesExcept("open", "vwap", "bidSize", "askSize");
    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
  }


}