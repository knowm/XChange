package info.bitrich.xchangestream.bitget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dto.trade.UserTrade;

@Slf4j
class BitgetStreamingTradeServiceIntegration extends BitgetStreamingExchangeIT {

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    assumeThat(exchange.getExchangeSpecification().getApiKey()).isNotEmpty();
    assumeThat(exchange.getExchangeSpecification().getSecretKey()).isNotEmpty();
    assumeThat(exchange.getExchangeSpecification().getPassword()).isNotEmpty();
  }

  @Test
  void user_trades_all() {
    Observable<UserTrade> observable = exchange.getStreamingTradeService().getUserTrades();

    TestObserver<UserTrade> testObserver = observable.test();

    List<UserTrade> userTrades =
        testObserver
            //        .awaitDone(10, TimeUnit.MINUTES)
            .awaitCount(1)
            .values();

    testObserver.dispose();

    log.info("Received usertrades: {}", userTrades);

    assumeThat(userTrades).overridingErrorMessage("No trades happened").isNotEmpty();

    assertThat(userTrades.get(0).getInstrument()).isNotNull();
    assertThat(userTrades.get(0).getId()).isNotNull();
    assertThat(userTrades.get(0).getOrderId()).isNotNull();
  }

  @Test
  void user_trades_single_instrument() {
    Observable<UserTrade> observable = exchange.getStreamingTradeService().getUserTrades(BTC_USDT);

    TestObserver<UserTrade> testObserver = observable.test();

    List<UserTrade> userTrades =
        testObserver
            //        .awaitDone(1, TimeUnit.MINUTES)
            .awaitCount(1)
            .values();

    testObserver.dispose();

    log.info("Received usertrades: {}", userTrades);

    assumeThat(userTrades).overridingErrorMessage("No trades happened").isNotEmpty();

    assertThat(userTrades.get(0).getInstrument()).isEqualTo(BTC_USDT);
    assertThat(userTrades.get(0).getId()).isNotNull();
    assertThat(userTrades.get(0).getOrderId()).isNotNull();
  }
}
