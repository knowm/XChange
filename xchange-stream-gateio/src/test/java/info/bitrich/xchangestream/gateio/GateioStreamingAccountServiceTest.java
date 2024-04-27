package info.bitrich.xchangestream.gateio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.balance.GateioMultipleSpotBalanceNotification;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GateioStreamingAccountServiceTest {

  @Mock
  GateioStreamingService gateioStreamingService;
  GateioStreamingAccountService gateioStreamingAccountService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();


  @BeforeEach
  public void setup() {
    gateioStreamingAccountService = new GateioStreamingAccountService(gateioStreamingService);
  }


  @Test
  void spot_balances() throws Exception {
    GateioWsNotification multipleNotification = readNotification("spot.balance.update.json");
    assertThat(multipleNotification).isInstanceOf(GateioMultipleSpotBalanceNotification.class);
    GateioWsNotification notification = ((GateioMultipleSpotBalanceNotification) multipleNotification).toSingleNotifications().get(0);

    when(gateioStreamingService.subscribeChannel(eq("spot.balances")))
        .thenReturn(Observable.just(notification));

    Observable<Balance> observable = gateioStreamingAccountService.getBalanceChanges(Currency.USDT);

    TestObserver<Balance> testObserver = observable.test();

    Balance actual = testObserver
        .assertSubscribed()
        .awaitCount(1)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    Balance expected = new Balance.Builder()
        .available(new BigDecimal("42.06583427604872431142"))
        .currency(Currency.USDT)
        .frozen(BigDecimal.ONE)
        .timestamp(Date.from(Instant.ofEpochMilli(1691707273890L)))
        .total(new BigDecimal("43.06583427604872431142"))
        .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }


  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName),
        GateioWsNotification.class
    );
  }


}