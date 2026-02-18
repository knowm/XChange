package info.bitrich.xchangestream.bitfinex;

import static org.assertj.core.api.Assumptions.assumeThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

@Slf4j
public class BitfinexStreamingAccountServiceIntegration extends BitfinexStreamingExchangeIT {

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    assumeThat(exchange.getExchangeSpecification().getApiKey()).isNotEmpty();
    assumeThat(exchange.getExchangeSpecification().getSecretKey()).isNotEmpty();
  }

  @Test
  void spot_balances() {
    Observable<Balance> observable =
        exchange.getStreamingAccountService().getBalanceChanges(Currency.USDT, "exchange");

    TestObserver<Balance> testObserver = observable.test();

    List<Balance> balances = testObserver.awaitDone(5, TimeUnit.SECONDS).awaitCount(1).values();

    testObserver.dispose();

    log.info("Received balances: {}", balances);

    assumeThat(balances).overridingErrorMessage("Received nothing").isNotEmpty();
  }
}
