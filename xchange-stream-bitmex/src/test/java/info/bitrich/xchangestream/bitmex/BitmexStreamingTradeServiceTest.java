package info.bitrich.xchangestream.bitmex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitmex.config.Config;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketTransaction;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BitmexStreamingTradeServiceTest {

  @Mock
  BitmexStreamingService bitmexStreamingService;

  BitmexStreamingTradeService bitmexStreamingTradeService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @BeforeAll
  public static void initAdapters() {
    BitmexAdapters.putSymbolMapping("XBT_USDT", CurrencyPair.BTC_USDT);
    BitmexAdapters.putCurrencyScale(Currency.BTC, 8);
    BitmexAdapters.putCurrencyScale(Currency.USDT, 6);
  }

  @BeforeEach
  public void setup() {
    bitmexStreamingTradeService = new BitmexStreamingTradeService(bitmexStreamingService);
  }


  @Test
  void execution_market_sell() throws Exception {

    BitmexWebSocketTransaction notification = readNotification("info/bitrich/xchangestream/bitmex/dto/execution_market-sell.json");

    when(bitmexStreamingService.subscribeBitmexChannel(eq("execution")))
        .thenReturn(Observable.just(notification));

    Observable<UserTrade> observable = bitmexStreamingTradeService.getUserTrades(CurrencyPair.BTC_USDT);

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade actual = testObserver
        .awaitCount(1)
        .values().get(0);

    testObserver.dispose();

    UserTrade expected = UserTrade.builder()
        .id("00000000-006d-1000-0000-0010f2cc63dc")
        .orderId("082dd28b-7e7d-4643-9fd3-273af75de813")
        .feeCurrency(Currency.USDT)
        .type(OrderType.ASK)
        .instrument(CurrencyPair.BTC_USDT)
        .feeAmount(new BigDecimal("0.008535"))
        .originalAmount(new BigDecimal("0.00010000"))
        .price(new BigDecimal("94834.5"))
        .timestamp(Date.from(Instant.parse("2025-01-08T23:33:24.603Z")))
        .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

  }

  @Test
  void execution_market_buy() throws Exception {

    BitmexWebSocketTransaction notification = readNotification("info/bitrich/xchangestream/bitmex/dto/execution_market-buy.json");

    when(bitmexStreamingService.subscribeBitmexChannel(eq("execution")))
        .thenReturn(Observable.just(notification));

    Observable<UserTrade> observable = bitmexStreamingTradeService.getUserTrades(CurrencyPair.BTC_USDT);

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade actual = testObserver
        .awaitCount(1)
        .values().get(0);

    testObserver.dispose();

    UserTrade expected = UserTrade.builder()
        .id("00000000-006d-1000-0000-0010f2cbdbb0")
        .orderId("a4f9b79f-90b3-4756-bad1-6f2db0919fef")
        .feeCurrency(Currency.USDT)
        .type(OrderType.BID)
        .instrument(CurrencyPair.BTC_USDT)
        .feeAmount(new BigDecimal("0.008545"))
        .originalAmount(new BigDecimal("0.00010000"))
        .price(new BigDecimal("94952"))
        .timestamp(Date.from(Instant.parse("2025-01-08T23:33:04.091Z")))
        .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

  }

  private BitmexWebSocketTransaction readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), BitmexWebSocketTransaction.class);
  }


}
