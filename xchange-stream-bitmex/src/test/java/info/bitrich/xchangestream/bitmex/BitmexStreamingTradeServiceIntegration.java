package info.bitrich.xchangestream.bitmex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

@Slf4j
class BitmexStreamingTradeServiceIntegration extends BitmexStreamingExchangeIT {

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    assumeThat(exchange.getExchangeSpecification().getApiKey()).isNotEmpty();
    assumeThat(exchange.getExchangeSpecification().getSecretKey()).isNotEmpty();
  }


  @Test
  void order_changes() {
    Observable<Order> observable = exchange.getStreamingTradeService().getOrderChanges(new CurrencyPair("SOL/USDT"));

    TestObserver<Order> testObserver = observable.test();

    Order order = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("SOL/USDT"));
    assertThat(order.getType()).isNotNull();
    assertThat(order.getId()).isNotNull();
    assertThat(order.getTimestamp()).isNotNull();
    assertThat(order.getStatus()).isNotNull();

  }
}
