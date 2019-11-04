package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.JsonNode;
import io.reactivex.Observable;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class LgoStreamingAccountServiceTest {

    @Test
    public void it_handles_balances() throws IOException {
        JsonNode snapshot = TestUtils.getJsonContent("/account/balance-snapshot.json");
        JsonNode update = TestUtils.getJsonContent("/account/balance-update.json");
        LgoStreamingService streamingService = mock(LgoStreamingService.class);
        LgoStreamingAccountService service = new LgoStreamingAccountService(streamingService);
        when(streamingService.subscribeChannel(anyString())).thenReturn(Observable.just(snapshot, update));

        Observable<Wallet> wallet = service.getWallet();

        verify(streamingService).subscribeChannel("balance");
        assertThat(wallet.blockingFirst()).usingRecursiveComparison().isEqualTo(
                buildWallet(new Balance(Currency.BTC, new BigDecimal("2301.01329566"), new BigDecimal("2297.01329566"), new BigDecimal("4.00000000")),
                        new Balance(Currency.USD, new BigDecimal("453616.3125"), new BigDecimal("453616.3125"), new BigDecimal("0.0000")))
        );
        assertThat(wallet.blockingLast()).usingRecursiveComparison().isEqualTo(
                buildWallet(
                        new Balance(Currency.BTC, new BigDecimal("2299.01329566"), new BigDecimal("2295.01329566"), new BigDecimal("4.00000000")),
                        new Balance(Currency.USD, new BigDecimal("453616.3125"), new BigDecimal("453616.3125"), new BigDecimal("0.0000"))
                )
        );
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
        when(streamingService.subscribeChannel(anyString())).thenReturn(Observable.just(snapshot, update));

        Observable<Balance> btcChanges = service.getBalanceChanges(Currency.BTC);
        Observable<Balance> usdChanges = service.getBalanceChanges(Currency.USD);

        verify(streamingService, times(1)).subscribeChannel("balance");
        assertThat(btcChanges.blockingFirst()).usingRecursiveComparison().isEqualTo(
                new Balance(Currency.BTC, new BigDecimal("2301.01329566"), new BigDecimal("2297.01329566"), new BigDecimal("4.00000000"))
        );
        assertThat(btcChanges.blockingLast()).usingRecursiveComparison().isEqualTo(
                new Balance(Currency.BTC, new BigDecimal("2299.01329566"), new BigDecimal("2295.01329566"), new BigDecimal("4.00000000"))
        );
        assertThat(usdChanges.blockingFirst()).usingRecursiveComparison().isEqualTo(
                new Balance(Currency.USD, new BigDecimal("453616.3125"), new BigDecimal("453616.3125"), new BigDecimal("0.0000"))
        );
        assertThat(usdChanges.blockingLast()).usingRecursiveComparison().isEqualTo(
                new Balance(Currency.USD, new BigDecimal("453616.3125"), new BigDecimal("453616.3125"), new BigDecimal("0.0000"))
        );
    }
}