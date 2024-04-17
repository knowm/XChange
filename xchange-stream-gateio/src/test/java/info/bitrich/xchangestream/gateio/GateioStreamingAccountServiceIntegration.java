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
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

@Disabled("Needs authenticated exchange and real balance change. Set env vars GATEIO_API_KEY/GATEIO_API_SECRET")
class GateioStreamingAccountServiceIntegration extends GateioStreamingExchangeIT {

  @BeforeEach
  void authConfigured() {
    assumeTrue(StringUtils.isNotEmpty(exchange.getExchangeSpecification().getApiKey()), "Needs auth");
    assumeTrue(StringUtils.isNotEmpty(exchange.getExchangeSpecification().getSecretKey()), "Needs auth");
  }


  @Test
  void spot_balances() {
    Observable<Balance> observable = exchange
        .getStreamingAccountService()
        .getBalanceChanges(Currency.USDT);

    TestObserver<Balance> testObserver = observable.test();

    Balance balance = testObserver
        .assertSubscribed()
        .awaitCount(1, TestWaitStrategy.SLEEP_10MS, 2000000)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    assertThat(balance).hasNoNullFieldsOrProperties();
  }


}