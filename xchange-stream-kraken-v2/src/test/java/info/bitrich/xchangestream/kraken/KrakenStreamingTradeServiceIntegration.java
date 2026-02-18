package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dto.trade.UserTrade;

@Slf4j
public class KrakenStreamingTradeServiceIntegration extends KrakenStreamingExchangeIT {

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    assumeThat(exchange.getExchangeSpecification().getApiKey()).isNotEmpty();
    assumeThat(exchange.getExchangeSpecification().getSecretKey()).isNotEmpty();
  }

  @Test
  void user_trades_all() {
    Observable<UserTrade> observable = exchange.getStreamingTradeService().getUserTrades();

    TestObserver<UserTrade> testObserver = observable.test();

    List<UserTrade> userTrades = testObserver.awaitCount(1).values();

    testObserver.dispose();

    log.info("Received usertrades: {}", userTrades);

    assumeThat(userTrades).overridingErrorMessage("No trades happened").isNotEmpty();

    assertThat(userTrades.get(0).getInstrument()).isNotNull();
    assertThat(userTrades.get(0).getId()).isNotNull();
    assertThat(userTrades.get(0).getOrderId()).isNotNull();
  }
}
