package info.bitrich.xchangestream.binance.futures;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.futures.dto.AccountUpdateBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.processors.BehaviorProcessor;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class BinanceFuturesStreamingAccountService implements StreamingAccountService {

  private final BehaviorProcessor<AccountUpdateBinanceWebsocketTransaction> accountInfoLast =
      BehaviorProcessor.create();
  private final FlowableProcessor<AccountUpdateBinanceWebsocketTransaction> accountInfoPublisher =
      accountInfoLast.toSerialized();

  private volatile Disposable accountInfo;
  private volatile BinanceUserDataStreamingService binanceUserDataStreamingService;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BinanceFuturesStreamingAccountService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;
  }

  public Flowable<AccountUpdateBinanceWebsocketTransaction> getRawAccountInfo() {
    checkConnected();
    return accountInfoPublisher;
  }

  public Flowable<Balance> getBalanceChanges() {
    checkConnected();
    return getRawAccountInfo()
        .map(AccountUpdateBinanceWebsocketTransaction::toBalanceList)
        .flatMap(Flowable::fromIterable);
  }

  private void checkConnected() {
    if (binanceUserDataStreamingService == null || !binanceUserDataStreamingService.isSocketOpen())
      throw new ExchangeSecurityException("Not authenticated");
  }

  @Override
  public Flowable<Balance> getBalanceChanges(Currency currency, Object... args) {
    return getBalanceChanges().filter(t -> t.getCurrency().equals(currency));
  }

  /**
   * Registers subsriptions with the streaming service for the given products.
   *
   * <p>As we receive messages as soon as the connection is open, we need to register subscribers to
   * handle these before the first messages arrive.
   */
  public void openSubscriptions() {
    if (binanceUserDataStreamingService != null) {
      accountInfo =
          binanceUserDataStreamingService
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

  /**
   * User data subscriptions may have to persist across multiple socket connections to different
   * URLs and therefore must act in a publisher fashion so that subscribers get an uninterrupted
   * stream.
   */
  public void setUserDataStreamingService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    if (accountInfo != null && !accountInfo.isDisposed()) accountInfo.dispose();
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;
    openSubscriptions();
  }

  private AccountUpdateBinanceWebsocketTransaction accountInfo(JsonNode json) {
    try {
      return mapper.treeToValue(json, AccountUpdateBinanceWebsocketTransaction.class);
    } catch (Exception e) {
      throw new ExchangeException("Unable to parse account info", e);
    }
  }
}
