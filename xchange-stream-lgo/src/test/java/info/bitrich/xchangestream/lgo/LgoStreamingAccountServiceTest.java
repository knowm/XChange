package info.bitrich.xchangestream.lgo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.*;

public class LgoStreamingAccountServiceTest {

  @Test
  public void it_gives_initial_wallet_snapshot() throws IOException {
    JsonNode snapshot = TestUtils.getJsonContent("/account/balance-snapshot.json");
    LgoStreamingService streamingService = mock(LgoStreamingService.class);
    LgoStreamingAccountService service = new LgoStreamingAccountService(streamingService);
    Flowable<JsonNode> source = Flowable.just(snapshot);
    when(streamingService.subscribeChannel(anyString())).thenReturn(source);

    TestSubscriber<Wallet> wallet = service.getWallet().test();

    verify(streamingService).subscribeChannel("balance");
    wallet.assertValueCount(1);
    wallet
        .values()
        .contains(
            buildWallet(
                new Balance(
                    Currency.BTC,
                    new BigDecimal("2301.01329566"),
                    new BigDecimal("2297.01329566"),
                    new BigDecimal("4.00000000")),
                new Balance(
                    Currency.USD,
                    new BigDecimal("453616.3125"),
                    new BigDecimal("453616.3125"),
                    new BigDecimal("0.0000"))));
  }

  @Test
  public void it_handles_update() throws IOException {
    JsonNode snapshot = TestUtils.getJsonContent("/account/balance-snapshot.json");
    JsonNode update = TestUtils.getJsonContent("/account/balance-update.json");
    LgoStreamingService streamingService = mock(LgoStreamingService.class);
    LgoStreamingAccountService service = new LgoStreamingAccountService(streamingService);
    Flowable<JsonNode> source = Flowable.just(snapshot, update);
    when(streamingService.subscribeChannel(anyString())).thenReturn(source);

    service.getWallet().subscribe();

    TestSubscriber<Wallet> wallet = service.getWallet().test();
    wallet.assertValueAt(
        1,
        buildWallet(
            new Balance(
                Currency.BTC,
                new BigDecimal("2299.01329566"),
                new BigDecimal("2295.01329566"),
                new BigDecimal("4.00000000")),
            new Balance(
                Currency.USD,
                new BigDecimal("453616.3125"),
                new BigDecimal("453616.3125"),
                new BigDecimal("0.0000"))));
    verify(streamingService).subscribeChannel("balance");
  }

  private Wallet buildWallet(Balance... balances) {
    return Wallet.Builder.from(Arrays.asList(balances)).build();
  }

  @Test
  public void it_connects_one_time_and_filters_by_currency() throws IOException {
    JsonNode snapshot = TestUtils.getJsonContent("/account/balance-snapshot.json");
    JsonNode update = TestUtils.getJsonContent("/account/balance-update.json");
    LgoStreamingService streamingService = mock(LgoStreamingService.class);
    LgoStreamingAccountService service = new LgoStreamingAccountService(streamingService);
    when(streamingService.subscribeChannel(anyString()))
        .thenReturn(Flowable.just(snapshot, update));

    TestSubscriber<Balance> btcChanges = service.getBalanceChanges(Currency.BTC).test();
    TestSubscriber<Balance> usdChanges = service.getBalanceChanges(Currency.USD).test();

    verify(streamingService, times(1)).subscribeChannel("balance");
    btcChanges.assertValueAt(
        1,
        new Balance(
            Currency.BTC,
            new BigDecimal("2299.01329566"),
            new BigDecimal("2295.01329566"),
            new BigDecimal("4.00000000")));
    usdChanges.assertValueAt(
        1,
        new Balance(
            Currency.USD,
            new BigDecimal("453616.3125"),
            new BigDecimal("453616.3125"),
            new BigDecimal("0.0000")));
  }
}
