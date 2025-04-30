package info.bitrich.xchangestream.gateio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

@Slf4j
class GateioStreamingAccountServiceIntegration extends GateioStreamingExchangeIT {

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    assumeThat(exchange.getExchangeSpecification().getApiKey()).isNotEmpty();
    assumeThat(exchange.getExchangeSpecification().getSecretKey()).isNotEmpty();
  }

  @Test
  void spot_balances() {
    Observable<Balance> observable =
        exchange.getStreamingAccountService().getBalanceChanges(Currency.USDT);

    TestObserver<Balance> testObserver = observable.test();

    List<Balance> balances =
        testObserver
            //        .awaitDone(10, TimeUnit.MINUTES)
            .awaitCount(1)
            .values();

    testObserver.dispose();

    log.info("Received balances: {}", balances);

    assumeThat(balances).overridingErrorMessage("Received nothing").isNotEmpty();

    assertThat(balances).first().hasNoNullFieldsOrProperties();
  }
}
