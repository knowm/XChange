package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import info.bitrich.xchangestream.binance.dto.OutboundAccountInfoBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.processors.BehaviorProcessor;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class BinanceStreamingAccountService implements StreamingAccountService {

  private final BehaviorProcessor<OutboundAccountInfoBinanceWebsocketTransaction> accountInfoLast =
      BehaviorProcessor.create();
  private final FlowableProcessor<OutboundAccountInfoBinanceWebsocketTransaction> accountInfoPublisher =
      accountInfoLast.toSerialized();

  private volatile Disposable accountInfo;
  private volatile BinanceUserDataStreamingService binanceUserDataStreamingService;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BinanceStreamingAccountService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;
  }

  public Flowable<OutboundAccountInfoBinanceWebsocketTransaction> getRawAccountInfo() {
    checkConnected();
    return accountInfoPublisher;
  }

  public Flowable<Balance> getBalanceChanges() {
    checkConnected();
    return getRawAccountInfo()
        .map(OutboundAccountInfoBinanceWebsocketTransaction::getBalances)
        .flatMap((List<BinanceWebsocketBalance> balances) -> Flowable.fromIterable(balances))
        .map(BinanceWebsocketBalance::toBalance);
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
                  BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.OUTBOUND_ACCOUNT_INFO)
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
  void setUserDataStreamingService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    if (accountInfo != null && !accountInfo.isDisposed()) accountInfo.dispose();
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;
    openSubscriptions();
  }

  private OutboundAccountInfoBinanceWebsocketTransaction accountInfo(JsonNode json) {
    try {
      return mapper.treeToValue(json, OutboundAccountInfoBinanceWebsocketTransaction.class);
    } catch (Exception e) {
      throw new ExchangeException("Unable to parse account info", e);
    }
  }
}
