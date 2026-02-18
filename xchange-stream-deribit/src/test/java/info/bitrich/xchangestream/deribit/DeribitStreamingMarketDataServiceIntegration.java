package info.bitrich.xchangestream.deribit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

class DeribitStreamingMarketDataServiceIntegration extends DeribitStreamingExchangeIT {

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

  @Test
  void trades() {
    Observable<Trade> observable =
        exchange
            .getStreamingMarketDataService()
            .getTrades(new FuturesContract(new CurrencyPair("BTC/USD"), "PERPETUAL"));

    TestObserver<Trade> testObserver = observable.test();

    List<Trade> values =
        testObserver
            //                    .awaitDone(3, TimeUnit.MINUTES)
            .awaitCount(1)
            .values();

    assumeThat(values).isNotEmpty();

    var trade = values.get(0);

    testObserver.dispose();

    assertThat(trade).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
    assertThat(trade.getInstrument())
        .isEqualTo(new FuturesContract(new CurrencyPair("BTC/USD"), "PERPETUAL"));
  }
}
