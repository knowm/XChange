package info.bitrich.xchangestream.service.netty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.bitrich.xchangestream.service.exception.NotConnectedException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
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
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.internal.SocketUtils;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public abstract class NettyStreamingService<T> {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private static final Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration DEFAULT_RETRY_DURATION = Duration.ofSeconds(15);

    private class Subscription {
        final ObservableEmitter<T> emitter;
        final String channelName;
        final Object[] args;

        public Subscription(ObservableEmitter<T> emitter, String channelName, Object[] args) {
            this.emitter = emitter;
            this.channelName = channelName;
            this.args = args;
        }
    }

    private final int maxFramePayloadLength;
    private final URI uri;
    private boolean isManualDisconnect = false;
    private boolean connectedSuccessfully = false;
    private Channel webSocketChannel;
    private Duration retryDuration;
    private Duration connectionTimeout;
    private volatile NioEventLoopGroup eventLoopGroup;
    protected Map<String, Subscription> channels = new ConcurrentHashMap<>();
    private boolean compressedMessages = false;
    private final List<ObservableEmitter<Throwable>> reconnFailEmitters = new LinkedList<>();
    private final List<ObservableEmitter<Object>> connectionSuccessEmitters = new LinkedList<>();

    //debugging
    private boolean acceptAllCertificates = false;
    private boolean enableLoggingHandler = false;
    private LogLevel loggingHandlerLevel = LogLevel.DEBUG;
    private String socksProxyHost;
    private Integer socksProxyPort;

    public NettyStreamingService(String apiUrl) {
        this(apiUrl, 65536);
    }

    public NettyStreamingService(String apiUrl, int maxFramePayloadLength) {
        this(apiUrl, maxFramePayloadLength, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_RETRY_DURATION);
    }

    public NettyStreamingService(String apiUrl, int maxFramePayloadLength, Duration connectionTimeout, Duration retryDuration) {
        try {
            this.maxFramePayloadLength = maxFramePayloadLength;
            this.retryDuration = retryDuration;
            this.connectionTimeout = connectionTimeout;
            this.uri = new URI(apiUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Error parsing URI " + apiUrl, e);
        }
    }

    public Completable connect() {
        return Completable.create(completable -> {
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
                        sslContextBuilder = sslContextBuilder.trustManager(InsecureTrustManagerFactory.INSTANCE);
                    }
                    sslCtx = sslContextBuilder.build();
                } else {
                    sslCtx = null;
                }

                final WebSocketClientHandler handler = getWebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(
                        uri, WebSocketVersion.V13, null, true, getCustomHeaders(), maxFramePayloadLength),
                        this::messageHandler);

                Bootstrap b = new Bootstrap();
                eventLoopGroup = new NioEventLoopGroup(2);
                b.group(eventLoopGroup)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, java.lang.Math.toIntExact(connectionTimeout.toMillis()))
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                ChannelPipeline p = ch.pipeline();

                                if (socksProxyHost != null) {
                                    p.addLast(new Socks5ProxyHandler(SocketUtils.socketAddress(socksProxyHost, socksProxyPort)));
                                }
                                if (sslCtx != null) {
                                    p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                                }

                                WebSocketClientExtensionHandler clientExtensionHandler = getWebSocketClientExtensionHandler();
                                List<ChannelHandler> handlers = new ArrayList<>(4);
                                handlers.add(new HttpClientCodec());

                                if (enableLoggingHandler) handlers.add(new LoggingHandler(loggingHandlerLevel));
                                if (compressedMessages) handlers.add(WebSocketClientCompressionHandler.INSTANCE);
                                handlers.add(new HttpObjectAggregator(8192));

                                if (clientExtensionHandler != null) {
                                  handlers.add(clientExtensionHandler);
                                }

                                handlers.add(handler);
                                p.addLast(handlers.toArray(new ChannelHandler[0]));
                            }
                        });

                b.connect(uri.getHost(), port).addListener((ChannelFuture future) -> {
                    webSocketChannel = future.channel();
                    if (future.isSuccess()) {
                        handler.handshakeFuture().addListener(f -> {
                            if (f.isSuccess()) {
                                completable.onComplete();
                            } else {
                                handleError(completable, f.cause());
                            }
                        });
                    } else {
                        handleError(completable, future.cause());
                    }

                });
            } catch (Exception throwable) {
                handleError(completable, throwable);
            }
        }).doOnError(t -> {
            LOG.warn("Problem with connection", t);
            reconnFailEmitters.forEach(emitter -> emitter.onNext(t));
        }).retryWhen(new RetryWithDelay(retryDuration.toMillis()))
          .doOnComplete(() -> {
            connectedSuccessfully = true;
            LOG.warn("Resubscribing channels");
            resubscribeChannels();

            connectionSuccessEmitters.forEach(emitter -> emitter.onNext(new Object()));
        });
    }

    protected void handleError(CompletableEmitter completable, Throwable t) {
        isManualDisconnect = true;
        ChannelFuture disconnect = webSocketChannel.disconnect();
        disconnect.addListener(f -> {
            if(f.isSuccess()) {
                isManualDisconnect = false;
            }
            // shutdown sockets after disconnect for avoiding sockets leak
            eventLoopGroup.shutdownGracefully(2, 30, TimeUnit.SECONDS);
        });
        completable.onError(t);
    }

    protected DefaultHttpHeaders getCustomHeaders() {
        return new DefaultHttpHeaders();
    }

    public Completable disconnect() {
        isManualDisconnect = true;
        connectedSuccessfully = false;
        return Completable.create(completable -> {
            if (webSocketChannel.isOpen()) {
                CloseWebSocketFrame closeFrame = new CloseWebSocketFrame();
                webSocketChannel.writeAndFlush(closeFrame).addListener(future -> {
                    channels = new ConcurrentHashMap<>();
                    eventLoopGroup.shutdownGracefully(2, 30, TimeUnit.SECONDS).addListener(f -> {
                      LOG.info("Disconnected");
                      completable.onComplete();
                    });
                });
            } else {
              LOG.warn("Disconnect called but already disconnected");
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
            WebSocketFrame frame = new TextWebSocketFrame(message);
            webSocketChannel.writeAndFlush(frame);
        }
    }

    public Observable<Throwable> subscribeReconnectFailure() {
        return Observable.create(reconnFailEmitters::add);
    }

    public Observable<Object> subscribeConnectionSuccess() {
        return Observable.create(connectionSuccessEmitters::add);
    }

    public Observable<T> subscribeChannel(String channelName, Object... args) {
        final String channelId = getSubscriptionUniqueId(channelName, args);
        LOG.info("Subscribing to channel {}", channelId);

        return Observable.<T>create(e -> {
            if (webSocketChannel == null || !webSocketChannel.isOpen()) {
                e.onError(new NotConnectedException());
            }

            if (!channels.containsKey(channelId)) {
                Subscription newSubscription = new Subscription(e, channelName, args);
                channels.put(channelId, newSubscription);
                try {
                    sendMessage(getSubscribeMessage(channelName, args));
                } catch (IOException throwable) {
                    e.onError(throwable);
                }
            }
        }).doOnDispose(() -> {
            if (channels.containsKey(channelId)) {
                sendMessage(getUnsubscribeMessage(channelId));
                channels.remove(channelId);
            }
        }).share();
    }

    public void resubscribeChannels() {
        for (String channelId : channels.keySet()) {
            try {
                Subscription subscription = channels.get(channelId);
                sendMessage(getSubscribeMessage(subscription.channelName, subscription.args));
            } catch (IOException e) {
                LOG.error("Failed to reconnect channel: {}", channelId);
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
        handleChannelMessage(channel, message);
    }

    protected void handleError(T message, Throwable t) {
        String channel = getChannel(message);
        handleChannelError(channel, t);
    }


    protected void handleChannelMessage(String channel, T message) {
        NettyStreamingService<T>.Subscription subscription = channels.get(channel);
        if (subscription == null) {
            LOG.debug("Channel has been closed {}.", channel);
            return;
        }
        ObservableEmitter<T> emitter = subscription.emitter;
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
        ObservableEmitter<T> emitter = subscription.emitter;
        if (emitter == null) {
            LOG.debug("No subscriber for channel {}.", channel);
            return;
        }

        emitter.onError(t);
    }

    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return WebSocketClientCompressionHandler.INSTANCE;
    }

    protected WebSocketClientHandler getWebSocketClientHandler(WebSocketClientHandshaker handshaker,
                                                               WebSocketClientHandler.WebSocketMessageHandler handler) {
        return new NettyWebSocketClientHandler(handshaker, handler);
    }

    protected class NettyWebSocketClientHandler extends WebSocketClientHandler {
        protected NettyWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
            super(handshaker, handler);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            if (isManualDisconnect) {
                isManualDisconnect = false;
            } else {
                super.channelInactive(ctx);
                if (connectedSuccessfully) {
                    LOG.info("Reopening websocket because it was closed by the host");
                    eventLoopGroup.shutdownGracefully(2, 30, TimeUnit.SECONDS).addListener(f -> connect().subscribe());
                }
            }
        }
    }

    public boolean isSocketOpen() {
        return webSocketChannel.isOpen();
    }

    public void useCompressedMessages(boolean compressedMessages) { this.compressedMessages = compressedMessages; }

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


}
