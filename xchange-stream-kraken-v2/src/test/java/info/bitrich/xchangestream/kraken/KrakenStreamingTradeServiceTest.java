package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.kraken.config.Config;
import info.bitrich.xchangestream.kraken.dto.response.KrakenMessage;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
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
public class KrakenStreamingTradeServiceTest {

  @Mock KrakenPrivateStreamingService krakenPrivateStreamingService;

  KrakenStreamingTradeService krakenStreamingTradeService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @BeforeEach
  public void init() {
    krakenStreamingTradeService = new KrakenStreamingTradeService(krakenPrivateStreamingService);
  }

  @Test
  void userTrade() throws Exception {
    KrakenMessage notification = readMessage("sample-messages/usertrade.json");

    when(krakenPrivateStreamingService.subscribeChannel(eq("executions")))
        .thenReturn(Observable.just(notification));

    Observable<UserTrade> observable = krakenStreamingTradeService.getUserTrades();

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    UserTrade expected =
        UserTrade.builder()
            .id("847415")
            .orderId("OPJUXZ-EPCO3-LBMAK6")
            .feeCurrency(Currency.EUR)
            .type(OrderType.ASK)
            .instrument(new CurrencyPair("KEEP/EUR"))
            .feeAmount(new BigDecimal("0.006778"))
            .originalAmount(new BigDecimal("49.90000000"))
            .price(new BigDecimal("0.06792"))
            .timestamp(Date.from(Instant.parse("2025-09-13T21:29:11.377Z")))
            .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  private KrakenMessage readMessage(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), KrakenMessage.class);
  }
}
