package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

@Slf4j
class KrakenStreamingMarketDataServiceIntegration extends KrakenStreamingExchangeIT {

  @Test
  void ticker() {
    Observable<Ticker> observable =
        exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD);

    TestObserver<Ticker> testObserver = observable.test();

    Ticker ticker = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void trades() {
    Observable<Trade> observable =
        exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD);

    TestObserver<Trade> testObserver = observable.test();

    var trades =
        testObserver
            //        .awaitDone(1, TimeUnit.MINUTES)
            .awaitCount(1)
            .values();

    testObserver.dispose();

    log.info("Received trades: {}", trades);

    assumeThat(trades).overridingErrorMessage("No trades happened").isNotEmpty();

    assertThat(trades.get(0)).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
    assertThat(trades.get(0).getInstrument()).isEqualTo(CurrencyPair.BTC_USD);
  }
}
