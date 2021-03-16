package info.bitrich.xchangestream.core;

import info.bitrich.xchangestream.service.ConnectableService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import io.netty.channel.ChannelHandlerContext;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public interface StreamingExchange extends Exchange {
  String USE_SANDBOX = "Use_Sandbox";
  String ACCEPT_ALL_CERITICATES = "Accept_All_Ceriticates";
  String ENABLE_LOGGING_HANDLER = "Enable_Logging_Handler";
  String SOCKS_PROXY_HOST = "SOCKS_Proxy_Host";
  String SOCKS_PROXY_PORT = "SOCKS_Proxy_Port";
  String AUTO_RECONNECT = "Auto_Reconnect";
  String L3_ORDERBOOK = "L3_Orderbook";

  /**
   * Connects to the WebSocket API of the exchange.
   *
   * @param args Product subscription is used only in certain exchanges where you need to specify
   *     subscriptions during the connect phase.
   * @return {@link Completable} that completes upon successful connection.
   */
  Completable connect(ProductSubscription... args);

  /**
   * Disconnect from the WebSocket API.
   *
   * @return {@link Completable} that completes upon successful disconnect.
   */
  Completable disconnect();

  /**
   * Checks whether connection to the exchange is alive.
   *
   * @return true if connection is open, otherwise false.
   */
  boolean isAlive();

  /**
   * Flowable for reconnection failure event. When this happens, it usually indicates that the
   * server or the network is down.
   *
   * @return Flowable with the exception during reconnection.
   */
  default Flowable<Throwable> reconnectFailure() {
    throw new NotYetImplementedForExchangeException("reconnectFailure");
  }

  /**
   * Flowable for connection success event. When this happens, it usually indicates that the
   * server or the network is down.
   *
   * @return Flowable with the exception during reconnection.
   */
  default Flowable<Object> connectionSuccess() {
    throw new NotYetImplementedForExchangeException("connectionSuccess");
  }

  /**
   * Flowable for disconnection event.
   *
   * @return Flowable with ChannelHandlerContext
   */
  default Flowable<ChannelHandlerContext> disconnectFlowable() {
    throw new NotYetImplementedForExchangeException("disconnectFlowable");
  }

  /**
   * Flowable for connectionState. designed to replaces connectionSuccess reconnectFailure
   * disconnectFlowable
   *
   * @return Flowable
   */
  default Flowable<State> connectionStateFlowable() {
    throw new NotYetImplementedForExchangeException("connectionState");
  }

  /**
   * Flowable for message delay measure. Every time when the client received a message with a
   * timestamp, the delay time is calculated and pushed to subscribers.
   *
   * @return Flowable with the message delay measure.
   */
  default Flowable<Long> messageDelay() {
    throw new NotYetImplementedForExchangeException("messageDelay");
  }

  default void resubscribeChannels() {
    throw new NotYetImplementedForExchangeException("resubscribeChannels");
  }

  default Flowable<Object> connectionIdle() {
    throw new NotYetImplementedForExchangeException("connectionIdle");
  }

  /** Returns service that can be used to access streaming market data. */
  StreamingMarketDataService getStreamingMarketDataService();

  /** Returns service that can be used to access streaming account data. */
  default StreamingAccountService getStreamingAccountService() {
    throw new NotYetImplementedForExchangeException("getStreamingAccountService");
  }

  /** Returns service that can be used to access streaming trade data. */
  default StreamingTradeService getStreamingTradeService() {
    throw new NotYetImplementedForExchangeException("getStreamingTradeService");
  }

  /**
   * Set whether or not to enable compression handler.
   *
   * @param compressedMessages Defaults to false
   */
  void useCompressedMessages(boolean compressedMessages);

  default void applyStreamingSpecification(
      ExchangeSpecification exchangeSpec, NettyStreamingService<?> streamingService) {
    streamingService.setSocksProxyHost(
        (String) exchangeSpec.getExchangeSpecificParametersItem(SOCKS_PROXY_HOST));
    streamingService.setSocksProxyPort(
        (Integer) exchangeSpec.getExchangeSpecificParametersItem(SOCKS_PROXY_PORT));
    streamingService.setBeforeConnectionHandler(
        (Runnable)
            exchangeSpec.getExchangeSpecificParametersItem(
                ConnectableService.BEFORE_CONNECTION_HANDLER));

    Boolean accept_all_ceriticates =
        (Boolean) exchangeSpec.getExchangeSpecificParametersItem(ACCEPT_ALL_CERITICATES);
    if (accept_all_ceriticates != null && accept_all_ceriticates) {
      streamingService.setAcceptAllCertificates(true);
    }

    Boolean enable_logging_handler =
        (Boolean) exchangeSpec.getExchangeSpecificParametersItem(ENABLE_LOGGING_HANDLER);
    if (enable_logging_handler != null && enable_logging_handler) {
      streamingService.setEnableLoggingHandler(true);
    }
    Boolean autoReconnect =
        (Boolean) exchangeSpec.getExchangeSpecificParametersItem(AUTO_RECONNECT);
    if (autoReconnect != null) streamingService.setAutoReconnect(autoReconnect);
  }
}
