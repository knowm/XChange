package org.knowm.xchange.stream.coinmate;

import com.fasterxml.jackson.core.type.TypeReference;
import org.knowm.xchange.stream.coinmate.dto.CoinmateWebsocketBalance;
import org.knowm.xchange.stream.core.StreamingAccountService;
import org.knowm.xchange.stream.service.netty.StreamingObjectMapperHelper;
import org.knowm.xchange.stream.service.pusher.PusherStreamingService;
import io.reactivex.Observable;
import java.util.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

public class CoinmateStreamingAccountService implements StreamingAccountService {

  private final PusherStreamingService service;
  private final String userId;
  private final Set<Wallet.WalletFeature> walletFeatures =
      new HashSet<>(Arrays.asList(Wallet.WalletFeature.TRADING, Wallet.WalletFeature.FUNDING));

  public CoinmateStreamingAccountService(PusherStreamingService service, String userId) {
    this.service = service;
    this.userId = userId;
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {

    return getCoinmateBalances()
        .map(balanceMap -> balanceMap.get(currency.toString()))
        .map(
            (balance) -> {
              return new Balance.Builder()
                  .currency(currency)
                  .total(balance.getBalance())
                  .available(balance.getBalance().subtract(balance.getReserved()))
                  .frozen(balance.getReserved())
                  .build();
            });
  }

  public Observable<Wallet> getWalletChanges(Object... args) {

    return getCoinmateBalances()
        .map(
            (balanceMap) -> {
              List<Balance> balances = new ArrayList<>();
              balanceMap.forEach(
                  (s, coinmateWebsocketBalance) -> {
                    balances.add(
                        new Balance.Builder()
                            .currency(new Currency(s))
                            .total(coinmateWebsocketBalance.getBalance())
                            .available(
                                coinmateWebsocketBalance
                                    .getBalance()
                                    .subtract(coinmateWebsocketBalance.getReserved()))
                            .frozen(coinmateWebsocketBalance.getReserved())
                            .build());
                  });
              return balances;
            })
        .map(
            (balances) -> {
              return Wallet.Builder.from(balances).features(walletFeatures).id("spot").build();
            });
  }

  private Observable<Map<String, CoinmateWebsocketBalance>> getCoinmateBalances() {
    String channelName = "private-user_balances-" + userId;

    return service
        .subscribeChannel(channelName, "user_balances")
        .map(
            (message) -> {
              Map<String, CoinmateWebsocketBalance> balanceMap =
                  StreamingObjectMapperHelper.getObjectMapper()
                      .readValue(
                          message, new TypeReference<Map<String, CoinmateWebsocketBalance>>() {});

              return balanceMap;
            });
  }
}
