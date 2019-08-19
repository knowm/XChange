package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.lgo.dto.LgoBalanceUpdate;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

import java.util.HashMap;
import java.util.Map;

public class LgoStreamingAccountService implements StreamingAccountService {

    private final LgoStreamingService service;
    private final Map<Currency, Balance> wallet = new HashMap<>();
    private Observable<Wallet> walletObservable = null;

    public LgoStreamingAccountService(LgoStreamingService lgoStreamingService) {
        service = lgoStreamingService;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        return getWallet().map(w -> w.getBalance(currency));
    }

    public Observable<Wallet> getWallet() {
        if (walletObservable == null) {
            createSubscription();
        }
        return walletObservable.share();
    }

    private void createSubscription() {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        walletObservable = service
                .subscribeChannel("balance")
                .map(s -> mapper.readValue(s.toString(), LgoBalanceUpdate.class))
                .map(s -> {
                    if (s.getType().equals("snapshot")) {
                        wallet.clear();
                    }
                    LgoAdapter.adaptBalances(s.getData()).forEach(balance -> wallet.put(balance.getCurrency(), balance));
                    return new Wallet(wallet.values());
                });
    }
}
