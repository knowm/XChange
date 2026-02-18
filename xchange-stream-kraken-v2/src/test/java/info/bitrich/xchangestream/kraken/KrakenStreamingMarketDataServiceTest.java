package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.kraken.config.Config;
import info.bitrich.xchangestream.kraken.dto.response.KrakenMessage;
import io.reactivex.rxjava3.core.Observable;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trade;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KrakenStreamingMarketDataServiceTest {

  @Mock KrakenStreamingService krakenStreamingService;

  KrakenStreamingMarketDataService krakenStreamingMarketDataService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @BeforeEach
  public void init() {
    krakenStreamingMarketDataService = new KrakenStreamingMarketDataService(krakenStreamingService);
  }

  @Test
  void trades() throws Exception {
    KrakenMessage notification = readMessage("sample-messages/trade.json");

    when(krakenStreamingService.subscribeChannel(eq("trade"), eq(CurrencyPair.BTC_USD)))
        .thenReturn(Observable.just(notification));

    var observable = krakenStreamingMarketDataService.getTrades(CurrencyPair.BTC_USD);

    var testObserver = observable.test();

    var actual = testObserver.awaitCount(1).values();

    testObserver.dispose();

    Trade expected =
        Trade.builder()
            .instrument(CurrencyPair.BTC_USD)
            .id("86881079")
            .originalAmount(new BigDecimal("0.00037711"))
            .price(new BigDecimal("111399.1"))
            .timestamp(Date.from(Instant.parse("2025-09-09T21:31:50.716Z")))
            .type(OrderType.BID)
            .build();

    assertThat(actual).hasSize(1);

    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  private KrakenMessage readMessage(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), KrakenMessage.class);
  }
}
