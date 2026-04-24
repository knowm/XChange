package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioSingleOrderNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioMultipleUserTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GateioStreamingTradeServiceTest {

  @Mock GateioStreamingService gateioStreamingService;
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
    GateioWsNotification notification =
        ((GateioMultipleUserTradeNotification) multipleNotification).toSingleNotifications().get(0);

    when(gateioStreamingService.subscribeChannel(eq("spot.usertrades"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<UserTrade> observable =
        gateioStreamingTradeService.getUserTrades(CurrencyPair.BTC_USDT);

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    UserTrade expected =
        UserTrade.builder()
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

  @Test
  void order_changes_btc() throws Exception {
    GateioWsNotification notification = readNotification("spot.orders.update.json");
    assertThat(notification).isInstanceOf(GateioSingleOrderNotification.class);

    when(gateioStreamingService.subscribeChannel(eq("spot.orders"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<Order> observable =
        gateioStreamingTradeService.getOrderChanges(CurrencyPair.BTC_USDT);

    TestObserver<Order> testObserver = observable.test();

    Order actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(actual.getId()).isEqualTo("373824296029");
    assertThat(actual.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(actual.getType()).isEqualTo(OrderType.ASK);
    assertThat(actual.getOriginalAmount()).isEqualTo("0.00068");
  }

  @Test
  void order_changes_futures_btc() throws Exception {
    GateioWsNotification notification = readNotification("spot.orders.update.json");
    assertThat(notification).isInstanceOf(GateioSingleOrderNotification.class);

    FuturesContract futuresContract = new FuturesContract(CurrencyPair.BTC_USDT, "BTC_USDT");

    when(gateioStreamingService.subscribeChannel(eq("futures.orders"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<Order> observable =
        gateioStreamingTradeService.getOrderChanges(futuresContract);

    TestObserver<Order> testObserver = observable.test();

    Order actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(actual.getId()).isEqualTo("373824296029");
    assertThat(actual.getInstrument()).isEqualTo(futuresContract.getCurrencyPair());
    assertThat(actual.getType()).isEqualTo(OrderType.ASK);
    assertThat(actual.getOriginalAmount()).isEqualTo("0.00068");
  }

  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), GateioWsNotification.class);
  }
}
