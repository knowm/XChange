package info.bitrich.xchangestream.binancefuture;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.binance.BinanceStreamingAccountService;
import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesAccountUpdateRaw;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesAccountUpdateTransaction;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesPosition;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class BinanceFuturesStreamingAccountService extends BinanceStreamingAccountService {

  private final BehaviorSubject<BinanceFuturesAccountUpdateTransaction> accountInfoLast =
      BehaviorSubject.create();
  private final Subject<BinanceFuturesAccountUpdateTransaction> accountInfoPublisher =
      accountInfoLast.toSerialized();

  public BinanceFuturesStreamingAccountService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    super(binanceUserDataStreamingService);
  }


  public Observable<BinanceFuturesAccountUpdateTransaction> getRawFuturesAccountInfo() {
    checkConnected();
    return accountInfoPublisher;
  }

  public Observable<BinanceFuturesAccountUpdateRaw> getAccountUpdates() {
    return getRawFuturesAccountInfo().map(BinanceFuturesAccountUpdateRaw::new);
  }

  private void checkConnected() {
    if (binanceUserDataStreamingService == null
        || !binanceUserDataStreamingService.isSocketOpen()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
  }

  @Override
  public Observable<Balance> getBalanceChanges() {
    return getRawFuturesAccountInfo()
        .map(
            it -> it.toBalanceList()
                .stream()
                .map(b -> new Balance(b.getCurrency(), b.getWalletBalance()))
                .collect(Collectors.toList())
        )
        .flatMap(Observable::fromIterable);
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    return getBalanceChanges().filter(t -> t.getCurrency().equals(currency));
  }

  public Observable<BinanceFuturesPosition> getPositionChanges() {
    return getRawFuturesAccountInfo()
        .map(BinanceFuturesAccountUpdateTransaction::toPositionList)
        .flatMap(Observable::fromIterable);
  }

  public Observable<BinanceFuturesPosition> getPositionChanges(FuturesContract contract) {
    return getPositionChanges().filter(t -> t.getFuturesContract().equals(contract));
  }

  @Override
  public void openSubscriptions() {
    if (binanceUserDataStreamingService != null) {
      accountInfo = binanceUserDataStreamingService
          .subscribeChannel(
              BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.ACCOUNT_UPDATE)
          .map(this::accountInfo)
          .filter(
              m ->
                  accountInfoLast.getValue() == null
                      || accountInfoLast.getValue().getEventTime().before(m.getEventTime()))
          .subscribe(accountInfoPublisher::onNext);
    }
  }


  private BinanceFuturesAccountUpdateTransaction accountInfo(JsonNode json) {
    try {
      return mapper.treeToValue(json, BinanceFuturesAccountUpdateTransaction.class);
    } catch (Exception e) {
      throw new ExchangeException("Unable to parse account info", e);
    }
  }
}
