package info.bitrich.xchangestream.coinmate.v2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebsocketBalance;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

public class CoinmateStreamingAccountService implements StreamingAccountService {

  private final CoinmateStreamingService coinmateStreamingService;
  private final Set<Wallet.WalletFeature> walletFeatures =
      new HashSet<>(Arrays.asList(Wallet.WalletFeature.TRADING, Wallet.WalletFeature.FUNDING));

  public CoinmateStreamingAccountService(CoinmateStreamingService coinmateStreamingService) {
    this.coinmateStreamingService = coinmateStreamingService;
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {

    return getCoinmateBalances()
        .map(balanceMap -> balanceMap.get(currency.toString()))
        .map(
            (balance) ->
                new Balance.Builder()
                    .currency(currency)
                    .total(balance.getBalance())
                    .available(balance.getBalance().subtract(balance.getReserved()))
                    .frozen(balance.getReserved())
                    .build());
  }

  public Observable<Wallet> getWalletChanges(Object... args) {

    return getCoinmateBalances()
        .map(
            (balanceMap) -> {
              List<Balance> balances = new ArrayList<>();
              balanceMap.forEach(
                  (s, coinmateWebsocketBalance) ->
                      balances.add(
                          new Balance.Builder()
                              .currency(new Currency(s))
                              .total(coinmateWebsocketBalance.getBalance())
                              .available(
                                  coinmateWebsocketBalance
                                      .getBalance()
                                      .subtract(coinmateWebsocketBalance.getReserved()))
                              .frozen(coinmateWebsocketBalance.getReserved())
                              .build()));
              return balances;
            })
        .map(
            (balances) ->
                Wallet.Builder.from(balances).features(walletFeatures).id("spot").build());
  }

  private Observable<Map<String, CoinmateWebsocketBalance>> getCoinmateBalances() {
    String channelName = "private-user_balances-" + coinmateStreamingService.getUserId();

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper()
            .readerFor(new TypeReference<Map<String, CoinmateWebsocketBalance>>() {});

    return coinmateStreamingService
        .subscribeChannel(channelName, true)
        .map((message) -> reader.readValue(message.get("payload").get("balances")));
  }
}
