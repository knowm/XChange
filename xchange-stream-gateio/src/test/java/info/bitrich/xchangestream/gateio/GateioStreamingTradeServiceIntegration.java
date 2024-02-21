package info.bitrich.xchangestream.gateio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import io.reactivex.Observable;
import io.reactivex.observers.BaseTestConsumer.TestWaitStrategy;
import io.reactivex.observers.TestObserver;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrade;

@Disabled("Needs authenticated exchange and real user trade. Set env vars GATEIO_API_KEY/GATEIO_API_SECRET")
class GateioStreamingTradeServiceIntegration extends GateioStreamingExchangeIT {

  @BeforeEach
  void authConfigured() {
    assumeTrue(StringUtils.isNotEmpty(exchange.getExchangeSpecification().getApiKey()), "Needs auth");
    assumeTrue(StringUtils.isNotEmpty(exchange.getExchangeSpecification().getSecretKey()), "Needs auth");
  }


  @Test
  void user_trades_btc() {
    Observable<UserTrade> observable = exchange
        .getStreamingTradeService()
        .getUserTrades(CurrencyPair.BTC_USDT);

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade userTrade = testObserver
        .assertSubscribed()
        .awaitCount(1, TestWaitStrategy.SLEEP_10MS, 2000000)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    assertThat(userTrade).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
    assertThat(userTrade.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
  }


  @Test
  void user_trades_all() {
    Observable<UserTrade> observable = exchange
        .getStreamingTradeService()
        .getUserTrades();

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade userTrade = testObserver
        .assertSubscribed()
        .awaitCount(1, TestWaitStrategy.SLEEP_10MS, 2000000)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    assertThat(userTrade).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
  }

}