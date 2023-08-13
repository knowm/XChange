package info.bitrich.xchangestream.gateio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioMultipleUserTradeNotification;
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
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GateioStreamingTradeServiceTest {

  @Mock
  GateioStreamingService gateioStreamingService;
  GateioStreamingTradeService gateioStreamingTradeService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @BeforeEach
  public void setup() {
    gateioStreamingTradeService = new GateioStreamingTradeService(gateioStreamingService);
  }


  @Test
  void user_trades_btc() throws Exception {
    GateioWsNotification multipleNotification = readNotification("spot.usertrades.update.json");
    assertThat(multipleNotification).isInstanceOf(GateioMultipleUserTradeNotification.class);
    GateioWsNotification notification = ((GateioMultipleUserTradeNotification) multipleNotification).toSingleNotifications().get(0);

    when(gateioStreamingService.subscribeChannel(eq("spot.usertrades"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<UserTrade> observable = gateioStreamingTradeService.getUserTrades(CurrencyPair.BTC_USDT);

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade actual = testObserver
        .assertSubscribed()
        .awaitCount(1)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    UserTrade expected = new UserTrade.Builder()
        .instrument(CurrencyPair.BTC_USDT)
        .id("6068323582")
        .orderId("381004078014")
        .orderUserReference("3")
        .originalAmount(new BigDecimal("0.00003"))
        .feeAmount(new BigDecimal("0.00000006"))
        .feeCurrency(Currency.BTC)
        .price(new BigDecimal("29441.1"))
        .timestamp(Date.from(Instant.ofEpochMilli(1691692159330L)))
        .type(OrderType.BID)
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