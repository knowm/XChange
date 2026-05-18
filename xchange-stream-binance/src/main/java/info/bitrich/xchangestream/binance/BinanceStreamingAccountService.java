package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.account.OutboundAccountPositionBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class BinanceStreamingAccountService implements StreamingAccountService {

  private final BehaviorSubject<OutboundAccountPositionBinanceWebsocketTransaction>
      accountInfoLast = BehaviorSubject.create();
  private final Subject<OutboundAccountPositionBinanceWebsocketTransaction> accountInfoPublisher =
      accountInfoLast.toSerialized();

  private volatile Disposable accountInfo;
  private volatile BinanceUserDataFutureStreamingService binanceUserDataFutureStreamingService;
  private volatile BinanceUserDataSpotStreamingService binanceUserDataSpotStreamingService;
  private boolean isFuture = false;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BinanceStreamingAccountService(
      BinanceUserDataFutureStreamingService binanceUserDataFutureStreamingService, BinanceUserDataSpotStreamingService binanceUserDataSpotStreamingService, boolean isFuture) {
    this.binanceUserDataFutureStreamingService = binanceUserDataFutureStreamingService;
    this.binanceUserDataSpotStreamingService = binanceUserDataSpotStreamingService;
    this.isFuture = isFuture;
  }

  public Observable<OutboundAccountPositionBinanceWebsocketTransaction> getRawAccountInfo() {
    checkConnected();
    return accountInfoPublisher;
  }

  public Observable<Balance> getBalanceChanges() {
    checkConnected();
    return getRawAccountInfo()
        .map(OutboundAccountPositionBinanceWebsocketTransaction::toBalanceList)
        .flatMap(Observable::fromIterable);
  }

  private void checkConnected() {
    if (isFuture) {
      if (binanceUserDataFutureStreamingService == null || !binanceUserDataFutureStreamingService.isSocketOpen()) {
        throw new ExchangeSecurityException("Not authenticated");
      }
    } else if (binanceUserDataSpotStreamingService == null || !binanceUserDataSpotStreamingService.isSocketOpen()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    return getBalanceChanges().filter(t -> t.getCurrency().equals(currency));
  }

  /**
   * Registers subsriptions with the streaming service for the given products.
   *
   * <p>As we receive messages as soon as the connection is open, we need to register subscribers to
   * handle these before the first messages arrive.
   */
  public void openSubscriptions() {
    if (isFuture) {
      if (binanceUserDataFutureStreamingService == null) {
        return;
      }
      accountInfo =
          binanceUserDataFutureStreamingService
              .subscribeChannel(
                  BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.OUTBOUND_ACCOUNT_POSITION)
              .map(this::accountInfo)
              .filter(
                  m ->
                      accountInfoLast.getValue() == null
                          || accountInfoLast.getValue().getEventTime().before(m.getEventTime()))
              .subscribe(accountInfoPublisher::onNext);
    } else if (binanceUserDataSpotStreamingService != null) {
      accountInfo =
          binanceUserDataSpotStreamingService
              .subscribeChannel(
                  BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.OUTBOUND_ACCOUNT_POSITION)
              .map(this::accountInfo)
              .filter(
                  m ->
                      accountInfoLast.getValue() == null
                          || accountInfoLast.getValue().getEventTime().before(m.getEventTime()))
              .subscribe(accountInfoPublisher::onNext);
    }
  }

  /**
   * User data subscriptions may have to persist across multiple socket connections to different URLs and therefore must act in a publisher fashion so that subscribers get an uninterrupted stream.
   */
  void setUserDataFutureStreamingService(
      BinanceUserDataFutureStreamingService binanceUserDataFutureStreamingService) {
    if (accountInfo != null && !accountInfo.isDisposed()) {
      accountInfo.dispose();
    }
    this.binanceUserDataFutureStreamingService = binanceUserDataFutureStreamingService;
    openSubscriptions();
  }

  private OutboundAccountPositionBinanceWebsocketTransaction accountInfo(JsonNode json) {
    try {
      return mapper.treeToValue(json, OutboundAccountPositionBinanceWebsocketTransaction.class);
    } catch (Exception e) {
      throw new ExchangeException("Unable to parse account info", e);
    }
  }
}
