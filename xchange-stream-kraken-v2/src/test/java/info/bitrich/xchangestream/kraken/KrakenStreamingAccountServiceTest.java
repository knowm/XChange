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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KrakenStreamingAccountServiceTest {

  @Mock KrakenStreamingService krakenStreamingService;

  KrakenStreamingAccountService krakenStreamingAccountService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @BeforeEach
  public void init() {
    krakenStreamingAccountService = new KrakenStreamingAccountService(krakenStreamingService);
  }

  @Test
  void balances() throws Exception {
    KrakenMessage notification = readMessage("sample-messages/balance.json");

    when(krakenStreamingService.subscribeChannel(eq("balances")))
        .thenReturn(Observable.just(notification));

    var observable = krakenStreamingAccountService.getBalanceChanges(null);

    var testObserver = observable.test();

    var actual = testObserver.awaitCount(1).values();

    testObserver.dispose();

    Balance expected =
        Balance.builder()
            .available(new BigDecimal("42.06583427604872431142"))
            .currency(Currency.EUR)
            .total(new BigDecimal("31.7815"))
            .available(new BigDecimal("31.7815"))
            .build();

    assertThat(actual).hasSize(1);

    assertThat(actual)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  private KrakenMessage readMessage(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), KrakenMessage.class);
  }
}
