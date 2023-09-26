package info.bitrich.xchangestream.bybit;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.bybit.BybitExchange;

public class BybitStreamingExchange extends BybitExchange implements StreamingExchange {

  private enum MarketType {
    SPOT,
    LINEAR,
    INVERSE,
    OPTION
  }
  private BybitStreamingService streamingService;

  private BybitStreamingTradeService streamingTradeService;

  @Override
  public Completable connect(ProductSubscription... args) {

    if(exchangeSpecification.getApiKey() != null){
      streamingService = new BybitStreamingService(getBybitURI(useSandbox(exchangeSpecification), true, ""), exchangeSpecification);
      streamingTradeService = new BybitStreamingTradeService(streamingService);

    } else {
      streamingService = new BybitStreamingService(getBybitURI(useSandbox(exchangeSpecification), false, MarketType.SPOT.toString().toLowerCase()), exchangeSpecification);
    }

    return streamingService.connect();
  }

  private String getBybitURI(boolean isSandBox, boolean isAuthenticated, String marketType){
    return "wss://stream"+ (isSandBox ? "-testnet" : "") + ".bybit.com/v5/" + ((isAuthenticated) ? "private" : "public/" + marketType);
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public Completable disconnect() {
    streamingService.pingPongDisconnectIfConnected();
    return streamingService.disconnect();
  }

  @Override
  public Observable<Object> connectionSuccess() {
    return streamingService.subscribeConnectionSuccess();
  }

  @Override
  public Observable<Object> disconnectObservable() {
    return streamingService.subscribeDisconnect();
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public Observable<ConnectionStateModel.State> connectionStateObservable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public void resubscribeChannels() {
    streamingService.resubscribeChannels();
  }
}
