package info.bitrich.xchangestream.service.netty;

import info.bitrich.xchangestream.service.ConnectableService;
import info.bitrich.xchangestream.service.exception.NotConnectedException;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.processors.PublishProcessor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NettyStreamingService<T> extends ConnectableService {
  private final Logger LOG = LoggerFactory.getLogger(this.getClass());

  protected static final Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(10);
  protected static final Duration DEFAULT_RETRY_DURATION = Duration.ofSeconds(15);
  protected static final int DEFAULT_IDLE_TIMEOUT = 15;

  protected class Subscription {

    final FlowableEmitter<T> emitter;
    final String channelName;
    final Object[] args;

    public Subscription(FlowableEmitter<T> emitter, String channelName, Object[] args) {
      this.emitter = emitter;
      this.channelName = channelName;
      this.args = args;
    }

    public FlowableEmitter<T> getEmitter() {
      return emitter;
    }
  }

  private final int maxFramePayloadLength;
  protected URI uri;
  private final AtomicBoolean isManualDisconnect = new AtomicBoolean();
  private Channel webSocketChannel;
  private final Duration retryDuration;
  private final Duration connectionTimeout;
  private final int idleTimeoutSeconds;
  private volatile NioEventLoopGroup eventLoopGroup;
  protected final Map<String, Subscription> channels = new ConcurrentHashMap<>();
  private boolean compressedMessages = false;

  private final PublishProcessor<Throwable> reconnFailEmitters = PublishProcessor.create();
  private final PublishProcessor<Object> connectionSuccessEmitters = PublishProcessor.create();
  private final PublishProcessor<Object> disconnectEmitters = PublishProcessor.create();
  private final PublishProcessor<Object> subjectIdle = PublishProcessor.create();

  private final ConnectionStateModel connectionStateModel = new ConnectionStateModel();

  // debugging
  private boolean acceptAllCertificates = false;
  private boolean enableLoggingHandler = false;
  private boolean autoReconnect = true;
  private LogLevel loggingHandlerLevel = LogLevel.DEBUG;
  private String socksProxyHost;
  private Integer socksProxyPort;

  public NettyStreamingService(String apiUrl) {
    this(apiUrl, 65536);
  }

  public NettyStreamingService(String apiUrl, int maxFramePayloadLength) {
    this(apiUrl, maxFramePayloadLength, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_RETRY_DURATION);
  }

  public NettyStreamingService(
      String apiUrl,
      int maxFramePayloadLength,
      Duration connectionTimeout,
      Duration retryDuration) {
    this(apiUrl, maxFramePayloadLength, connectionTimeout, retryDuration, DEFAULT_IDLE_TIMEOUT);
  }

  public NettyStreamingService(
      String apiUrl,
      int maxFramePayloadLength,
      Duration connectionTimeout,
      Duration retryDuration,
      int idleTimeoutSeconds) {
    this.maxFramePayloadLength = maxFramePayloadLength;
    this.retryDuration = retryDuration;
    this.connectionTimeout = connectionTimeout;
    this.idleTimeoutSeconds = idleTimeoutSeconds;
    try {
      this.uri = new URI(apiUrl);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Error parsing URI " + apiUrl, e);
    }
  }

  @Override
  protected Completable openConnection() {
    return Completable.create(
            completable -> {
              try {
                LOG.info("Connecting to {}", uri.toString());

                String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();

                String host = uri.getHost();
                if (host == null) {
                  throw new IllegalArgumentException("Host cannot be null.");
                }

                final int port;
                if (uri.getPort() == -1) {
                  if ("ws".equalsIgnoreCase(scheme)) {
                    port = 80;
                  } else if ("wss".equalsIgnoreCase(scheme)) {
                    port = 443;
                  } else {
                    port = -1;
                  }
                } else {
                  port = uri.getPort();
                }

                if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
                  throw new IllegalArgumentException("Only WS(S) is supported.");
                }

                final boolean ssl = "wss".equalsIgnoreCase(scheme);
                final SslContext sslCtx;
                if (ssl) {
                  SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();
                  if (acceptAllCertificates) {
                    sslContextBuilder.trustManager(InsecureTrustManagerFactory.INSTANCE);
                  }
                  sslCtx = sslContextBuilder.build();
                } else {
                  sslCtx = null;
                }

                final WebSocketClientHandler handler =
                    getWebSocketClientHandler(
                        WebSocketClientHandshakerFactory.newHandshaker(
                            uri,
                            WebSocketVersion.V13,
                            null,
                            true,
                            getCustomHeaders(),
                            maxFramePayloadLength),
                        this::messageHandler);

                if (eventLoopGroup == null || eventLoopGroup.isShutdown()) {
                  eventLoopGroup = new NioEventLoopGroup(2);
                }

                new Bootstrap()
                    .group(eventLoopGroup)
                    .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        java.lang.Math.toIntExact(connectionTimeout.toMillis()))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(
                        new ChannelInitializer<SocketChannel>() {
                          @Override
                          protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            if (socksProxyHost != null) {
                              p.addLast(
                                  new Socks5ProxyHandler(
                                      SocketUtils.socketAddress(socksProxyHost, socksProxyPort)));
                            }
                            if (sslCtx != null) {
                              p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                            }
                            p.addLast(new HttpClientCodec());
                            if (enableLoggingHandler)
                              p.addLast(new LoggingHandler(loggingHandlerLevel));
                            if (compressedMessages)
                              p.addLast(WebSocketClientCompressionHandler.INSTANCE);
                            p.addLast(new HttpObjectAggregator(8192));
                            if (idleTimeoutSeconds > 0)
                              p.addLast(new IdleStateHandler(idleTimeoutSeconds, 0, 0));
                            WebSocketClientExtensionHandler clientExtensionHandler =
                                getWebSocketClientExtensionHandler();
                            if (clientExtensionHandler != null) {
                              p.addLast(clientExtensionHandler);
                            }
                            p.addLast(handler);
                          }
                        })
                    .connect(uri.getHost(), port)
                    .addListener(
                        (ChannelFuture channelFuture) -> {
                          webSocketChannel = channelFuture.channel();
                          if (channelFuture.isSuccess()) {
                            handler
                                .handshakeFuture()
                                .addListener(
                                    handshakeFuture -> {
                                      if (handshakeFuture.isSuccess()) {
                                        connectionStateModel.setState(State.OPEN);
                                        completable.onComplete();
                                      } else {
                                        webSocketChannel
                                            .disconnect()
                                            .addListener(
                                                x -> completable.onError(handshakeFuture.cause()));
                                      }
                                    });
                          } else {
                            connectionStateModel.setState(State.CLOSED);
                            completable.onError(channelFuture.cause());
                            scheduleReconnect();
                          }
                        });
              } catch (Exception throwable) {
                connectionStateModel.setState(State.CLOSED);
                completable.onError(throwable);
                scheduleReconnect();
              }
            })
        .doOnError(
            t -> {
              if (t instanceof WebSocketHandshakeException) {
                LOG.warn("Problem with connection: {} - {}", t.getClass(), t.getMessage());
              } else {
                LOG.warn("Problem with connection", t);
              }
              connectionStateModel.setState(State.CLOSED);
              reconnFailEmitters.onNext(t);
            })
        .doOnComplete(
            () -> {
              resubscribeChannels();

              connectionStateModel.setState(State.OPEN);
              connectionSuccessEmitters.onNext(new Object());
            });
  }

  private void scheduleReconnect() {
    if (autoReconnect) {
      LOG.info("Scheduling reconnection");

      webSocketChannel
          .eventLoop()
          .schedule(
              () ->
                  connect()
                      .subscribe(
                          () -> LOG.info("Reconnection complete"),
                          e -> LOG.error("Reconnection failed: {}", e.getMessage())),
              retryDuration.toMillis(),
              TimeUnit.MILLISECONDS);
    }
  }

  protected DefaultHttpHeaders getCustomHeaders() {
    return new DefaultHttpHeaders();
  }

  public Completable disconnect() {
    isManualDisconnect.set(true);
    return Completable.create(
        completable -> {
          if (webSocketChannel != null && webSocketChannel.isOpen()) {
            CloseWebSocketFrame closeFrame = new CloseWebSocketFrame();
            webSocketChannel
                .writeAndFlush(closeFrame)
                .addListener(
                    future -> {
                      channels.clear();
                      eventLoopGroup
                          .shutdownGracefully(2, idleTimeoutSeconds, TimeUnit.SECONDS)
                          .addListener(
                              f -> {
                                LOG.info("Disconnected");
                                connectionStateModel.setState(State.CLOSED);
                                disconnectEmitters.onNext(new Object());
                                completable.onComplete();
                              });
                    });
          } else {
            LOG.warn("Disconnect called but already disconnected");
            connectionStateModel.setState(State.CLOSED);
            completable.onComplete();
          }
        });
  }

  protected abstract String getChannelNameFromMessage(T message) throws IOException;

  public abstract String getSubscribeMessage(String channelName, Object... args) throws IOException;

  public abstract String getUnsubscribeMessage(String channelName) throws IOException;

  public String getSubscriptionUniqueId(String channelName, Object... args) {
    return channelName;
  }

  /**
   * Handler that receives incoming messages.
   *
   * @param message Content of the message from the server.
   */
  public abstract void messageHandler(String message);

  public void sendMessage(String message) {
    LOG.debug("Sending message: {}", message);

    if (webSocketChannel == null || !webSocketChannel.isOpen()) {
      LOG.warn("WebSocket is not open! Call connect first.");
      return;
    }

    if (!webSocketChannel.isWritable()) {
      LOG.warn("Cannot send data to WebSocket as it is not writable.");
      return;
    }
    if (message != null) {
      webSocketChannel.writeAndFlush(new TextWebSocketFrame(message));
    }
  }

  public Flowable<Throwable> subscribeReconnectFailure() {
    return reconnFailEmitters.publish(1).refCount();
  }

  public Flowable<Object> subscribeConnectionSuccess() {
    return connectionSuccessEmitters.publish(1).refCount();
  }

  public Flowable<Object> subscribeDisconnect() {
    return disconnectEmitters.publish(1).refCount();
  }

  public Flowable<State> subscribeConnectionState() {
    return connectionStateModel.stateFlowable();
  }

  public Flowable<T> subscribeChannel(String channelName, Object... args) {
    final String channelId = getSubscriptionUniqueId(channelName, args);
    LOG.info("Subscribing to channel {}", channelId);

    return Flowable.<T>create(
            e -> {
              if (webSocketChannel == null || !webSocketChannel.isOpen()) {
                e.onError(new NotConnectedException());
              }
              channels.computeIfAbsent(
                  channelId,
                  cid -> {
                    Subscription newSubscription = new Subscription(e, channelName, args);
                    try {
                      sendMessage(getSubscribeMessage(channelName, args));
                    } catch (
                        Exception
                            throwable) { // if getSubscribeMessage throws this, it is because it
                      // needs to report
                      e.onError(throwable); // a problem creating the message
                    }
                    return newSubscription;
                  });
            }, BackpressureStrategy.LATEST)
    	.doOnCancel(
            () -> {
              if (channels.remove(channelId) != null) {
                try {
                  sendMessage(getUnsubscribeMessage(channelId));
                } catch (IOException e) {
                  LOG.debug("Failed to unsubscribe channel: {} {}", channelId, e.toString());
                } catch (Exception e) {
                  LOG.warn("Failed to unsubscribe channel: {}", channelId, e);
                }
              }
            })
    	.publish(1).refCount(); // share uses a buffer of 128. We need buffer of 1 to prevent delivering delayed events.
  }

  public void resubscribeChannels() {
    for (Entry<String, Subscription> entry : channels.entrySet()) {
      try {
        Subscription subscription = entry.getValue();
        sendMessage(getSubscribeMessage(subscription.channelName, subscription.args));
      } catch (IOException e) {
        LOG.error("Failed to reconnect channel: {}", entry.getKey());
      }
    }
  }

  protected String getChannel(T message) {
    String channel;
    try {
      channel = getChannelNameFromMessage(message);
    } catch (IOException e) {
      LOG.error("Cannot parse channel from message: {}", message);
      return "";
    }
    return channel;
  }

  protected void handleMessage(T message) {
    String channel = getChannel(message);
    if (!StringUtil.isNullOrEmpty(channel)) handleChannelMessage(channel, message);
  }

  protected void handleError(T message, Throwable t) {
    String channel = getChannel(message);
    if (!StringUtil.isNullOrEmpty(channel)) handleChannelError(channel, t);
    else LOG.error("handleError cannot parse channel from message: {}", message);
  }

  protected void handleIdle(ChannelHandlerContext ctx) {
    ctx.writeAndFlush(new PingWebSocketFrame());
  }

  private void onIdle(ChannelHandlerContext ctx) {
    subjectIdle.onNext(1);
    handleIdle(ctx);
  }

  /**
   * Flowable which fires if the websocket is deemed idle, only fired if <code>
   * idleTimeoutSeconds != 0</code>.
   */
  public Flowable<Object> subscribeIdle() {
    return subjectIdle.publish(1).refCount();
  }

  protected void handleChannelMessage(String channel, T message) {
    if (channel == null) {
      LOG.debug("Channel provided is null");
      return;
    }
    NettyStreamingService<T>.Subscription subscription = channels.get(channel);
    if (subscription == null) {
      LOG.debug("Channel has been closed {}.", channel);
      return;
    }
    FlowableEmitter<T> emitter = channels.get(channel).emitter;
    if (emitter == null) {
      LOG.debug("No subscriber for channel {}.", channel);
      return;
    }
    emitter.onNext(message);
  }

  protected void handleChannelError(String channel, Throwable t) {
    NettyStreamingService<T>.Subscription subscription = channels.get(channel);
    if (subscription == null) {
      LOG.debug("Channel {} has been closed.", channel);
      return;
    }
    FlowableEmitter<T> emitter = channels.get(channel) == null ? null : channels.get(channel).emitter;
    if (emitter == null) {
      LOG.debug("No subscriber for channel {}.", channel);
      return;
    }

    emitter.onError(t);
  }

  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionHandler.INSTANCE;
  }

  protected WebSocketClientHandler getWebSocketClientHandler(
      WebSocketClientHandshaker handshaker,
      WebSocketClientHandler.WebSocketMessageHandler handler) {
    return new NettyWebSocketClientHandler(handshaker, handler);
  }

  protected class NettyWebSocketClientHandler extends WebSocketClientHandler {

    protected NettyWebSocketClientHandler(
        WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
      super(handshaker, handler);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
      if (isManualDisconnect.compareAndSet(true, false)) {
        // Don't attempt to reconnect
      } else {
        super.channelInactive(ctx);
        disconnectEmitters.onNext(new Object());
        LOG.info("Reopening Websocket Client because it was closed! {}", ctx.channel());
        scheduleReconnect();
      }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      if (!(evt instanceof IdleStateEvent)) {
        return;
      }
      IdleStateEvent e = (IdleStateEvent) evt;
      if (e.state().equals(IdleState.READER_IDLE)) {
        onIdle(ctx);
      }
    }
  }

  public boolean isSocketOpen() {
    return webSocketChannel != null && webSocketChannel.isOpen();
  }

  public void useCompressedMessages(boolean compressedMessages) {
    this.compressedMessages = compressedMessages;
  }

  public void setAcceptAllCertificates(boolean acceptAllCertificates) {
    this.acceptAllCertificates = acceptAllCertificates;
  }

  public void setEnableLoggingHandler(boolean enableLoggingHandler) {
    this.enableLoggingHandler = enableLoggingHandler;
  }

  public void setLoggingHandlerLevel(LogLevel loggingHandlerLevel) {
    this.loggingHandlerLevel = loggingHandlerLevel;
  }

  public void setSocksProxyHost(String socksProxyHost) {
    this.socksProxyHost = socksProxyHost;
  }

  public void setSocksProxyPort(Integer socksProxyPort) {
    this.socksProxyPort = socksProxyPort;
  }

  public void setAutoReconnect(boolean autoReconnect) {
    this.autoReconnect = autoReconnect;
  }
}
