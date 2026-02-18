package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class KrakenStreamingAccountServiceIntegration extends KrakenStreamingExchangeIT {

  @Test
  void balances() {
    var observable = exchange.getStreamingAccountService().getBalanceChanges(null);

    var testObserver = observable.test();
    var balances = testObserver.awaitCount(1).values();
    testObserver.dispose();
    log.info("Received balances: {}", balances);

    assumeThat(balances).overridingErrorMessage("Received nothing").isNotEmpty();
    assertThat(balances).first().hasNoNullFieldsOrPropertiesExcept("timestamp");
  }
}
