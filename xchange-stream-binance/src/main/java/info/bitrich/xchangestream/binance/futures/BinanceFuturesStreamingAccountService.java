package info.bitrich.xchangestream.binance.futures;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.futures.dto.AccountConfigUpdateBinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.futures.dto.AccountUpdateBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.processors.BehaviorProcessor;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.instrument.Instrument;

public class BinanceFuturesStreamingAccountService implements StreamingAccountService {

  private final BehaviorProcessor<AccountUpdateBinanceWebsocketTransaction> accountInfoLast =
      BehaviorProcessor.create();
  private final FlowableProcessor<AccountUpdateBinanceWebsocketTransaction> accountInfoPublisher =
      accountInfoLast.toSerialized();

  private final BehaviorProcessor<AccountConfigUpdateBinanceWebsocketTransaction> accountConfigLast =
          BehaviorProcessor.create();
  private final FlowableProcessor<AccountConfigUpdateBinanceWebsocketTransaction> accountConfigPublisher =
          accountConfigLast.toSerialized();

  private volatile Disposable accountInfo;
  private volatile Disposable accountConfig;
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

  public Flowable<AccountConfigUpdateBinanceWebsocketTransaction> getRawAccountConfig() {
    checkConnected();
    return accountConfigPublisher;
  }

  public Flowable<Balance> getBalanceChanges() {
    checkConnected();
    return getRawAccountInfo()
        .map(AccountUpdateBinanceWebsocketTransaction::toBalanceList)
        .flatMap(Flowable::fromIterable);
  }

  public Flowable<OpenPosition> getPositionChanges() {
    checkConnected();
    return getRawAccountInfo()
            .map(AccountUpdateBinanceWebsocketTransaction::toPositionList)
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

  @Override
  public Flowable<OpenPosition> getPositionChanges(Instrument instrument, Object... args) {
    return getPositionChanges().filter(t -> t.getInstrument().equals(instrument));
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
              .subscribeChannel(BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.ACCOUNT_UPDATE)
              .map(this::accountInfo)
              .filter(
                  m ->
                      accountInfoLast.getValue() == null
                          || accountInfoLast.getValue().getEventTime().before(m.getEventTime()))
              .subscribe(accountInfoPublisher::onNext);

      accountConfig = binanceUserDataStreamingService
              .subscribeChannel(BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.ACCOUNT_CONFIG_UPDATE)
              .map(this::accountConfigUpdate)
              .filter(
                  m ->
                      accountConfigLast.getValue() == null
                          || accountConfigLast.getValue().getEventTime().before(m.getEventTime()))
              .subscribe(accountConfigPublisher::onNext);
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
    if (accountInfo != null && !accountConfig.isDisposed()) accountConfig.dispose();
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

  private AccountConfigUpdateBinanceWebsocketTransaction accountConfigUpdate(JsonNode json) {
    try {
      return mapper.treeToValue(json, AccountConfigUpdateBinanceWebsocketTransaction.class);
    } catch (Exception e) {
      throw new ExchangeException("Unable to parse account config update", e);
    }
  }
}
