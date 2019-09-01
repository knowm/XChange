package info.bitrich.xchange.coinmate;

import com.fasterxml.jackson.core.type.TypeReference;
import info.bitrich.xchange.coinmate.dto.CoinmateWebsocketBalance;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoinmateStreamingAccountService implements StreamingAccountService {

    private final PusherStreamingService service;
    private final String userId;

    public CoinmateStreamingAccountService(PusherStreamingService service, String userId) {
        this.service = service;
        this.userId = userId;
    }

    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        String channelName = "private-user_balances-" + this.userId;

        return this.service.subscribePrivateChannel(channelName, "user_balances")
                .map((message) -> {
                    Map<String, CoinmateWebsocketBalance> balanceMap =
                    StreamingObjectMapperHelper.getObjectMapper().readValue(message, new TypeReference<Map<String, CoinmateWebsocketBalance>>() {});
            return balanceMap;
        }).map((balanceMap) -> {
            CoinmateWebsocketBalance currencyBalance = balanceMap.get(currency.toString());
            return new Balance(
                        currency,
                        currencyBalance.getBalance(),
                        currencyBalance.getBalance().subtract(currencyBalance.getReserved()),
                        currencyBalance.getReserved()
                    );
        });
    }

    public Observable<Wallet> getWalletChanges(Object... args) {
        String channelName = "private-user_balances-" + this.userId;

        return service.subscribePrivateChannel(channelName, "user_balances").map((message) -> {
            Map<String, CoinmateWebsocketBalance> balanceMap =
                    StreamingObjectMapperHelper.getObjectMapper().readValue(message, new TypeReference<Map<String, CoinmateWebsocketBalance>>() {});
            return balanceMap;
        }).map((balanceMap) -> {
            List<Balance> balances = new ArrayList<>();
            balanceMap.forEach((s, coinmateWebsocketBalance) -> {
                balances.add(
                        new Balance(
                                new Currency(s),
                                coinmateWebsocketBalance.getBalance(),
                                coinmateWebsocketBalance.getBalance().subtract(coinmateWebsocketBalance.getReserved()),
                                coinmateWebsocketBalance.getReserved())
                );
            });
            return balances;
        }).map((balances) -> {
            return new Wallet("spot", balances);
        });
    }
}
