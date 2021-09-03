package info.bitrich.xchangestream.service.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker13;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import java.lang.reflect.Field;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyFriendlyWebSocketClientHandshaker extends WebSocketClientHandshaker13 {
    private static final Logger LOG =
            LoggerFactory.getLogger(ProxyFriendlyWebSocketClientHandshaker.class);

    public static final String HANDLER_HTTP_CLIENT_CODEC = "http.client.codec";
    private String expectedSubprotocol;

    public ProxyFriendlyWebSocketClientHandshaker(
            URI webSocketURL,
            WebSocketVersion version,
            String subprotocol,
            boolean allowExtensions,
            HttpHeaders customHeaders,
            int maxFramePayloadLength) {
        super(
                webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength);
        this.expectedSubprotocol = subprotocol;
    }

    public ProxyFriendlyWebSocketClientHandshaker(
            URI webSocketURL,
            WebSocketVersion version,
            String subprotocol,
            boolean allowExtensions,
            HttpHeaders customHeaders,
            int maxFramePayloadLength,
            boolean performMasking,
            boolean allowMaskMismatch) {
        super(
                webSocketURL,
                version,
                subprotocol,
                allowExtensions,
                customHeaders,
                maxFramePayloadLength,
                performMasking,
                allowMaskMismatch);
        this.expectedSubprotocol = subprotocol;
    }

    public ProxyFriendlyWebSocketClientHandshaker(
            URI webSocketURL,
            WebSocketVersion version,
            String subprotocol,
            boolean allowExtensions,
            HttpHeaders customHeaders,
            int maxFramePayloadLength,
            boolean performMasking,
            boolean allowMaskMismatch,
            long forceCloseTimeoutMillis) {
        super(
                webSocketURL,
                version,
                subprotocol,
                allowExtensions,
                customHeaders,
                maxFramePayloadLength,
                performMasking,
                allowMaskMismatch,
                forceCloseTimeoutMillis);
        this.expectedSubprotocol = subprotocol;
    }

    @Override
    public ChannelFuture handshake(Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return fixedHandshake(channel, channel.newPromise());
    }

    // fix the ws-encoder placement
    // ref to
    // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker.handshake(io.netty.channel.Channel, io.netty.channel.ChannelPromise)
    public ChannelFuture fixedHandshake(Channel channel, final ChannelPromise promise) {
        ChannelPipeline pipeline = channel.pipeline();
        HttpResponseDecoder decoder = pipeline.get(HttpResponseDecoder.class);
        if (decoder == null) {
            HttpClientCodec codec = pipeline.get(HttpClientCodec.class);
            if (codec == null) {
                promise.setFailure(
                        new IllegalStateException(
                                "ChannelPipeline does not contain " + "a HttpResponseDecoder or HttpClientCodec"));
                return promise;
            }
        }

        FullHttpRequest request = newHandshakeRequest();

        channel
                .writeAndFlush(request)
                .addListener(
                        new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) {
                                if (future.isSuccess()) {
                                    ChannelPipeline p = future.channel().pipeline();
                                    // quick fix here
                                    if (p.get(HANDLER_HTTP_CLIENT_CODEC) != null) {
                                        p.addAfter(HANDLER_HTTP_CLIENT_CODEC, "ws-encoder", newWebSocketEncoder());
                                    } else {
                                        ChannelHandlerContext ctx = p.context(HttpRequestEncoder.class);
                                        if (ctx == null) {
                                            ctx = p.context(HttpClientCodec.class);
                                        }
                                        if (ctx == null) {
                                            promise.setFailure(
                                                    new IllegalStateException(
                                                            "ChannelPipeline does not contain "
                                                                    + "a HttpRequestEncoder or HttpClientCodec"));
                                            return;
                                        }
                                        p.addAfter(ctx.name(), "ws-encoder", newWebSocketEncoder());
                                    }

                                    promise.setSuccess();
                                } else {
                                    promise.setFailure(future.cause());
                                }
                            }
                        });
        return promise;
    }

    // ref to io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker.finishHandshake
    public void fixedFinishHandshake(Channel channel, FullHttpResponse response) {
        verify(response);

        // Verify the subprotocol that we received from the server.
        // This must be one of our expected subprotocols - or null/empty if we didn't want to speak a
        // subprotocol
        String receivedProtocol = response.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
        receivedProtocol = receivedProtocol != null ? receivedProtocol.trim() : null;
        String expectedProtocol = expectedSubprotocol != null ? expectedSubprotocol : "";
        boolean protocolValid = false;

        if (expectedProtocol.isEmpty() && receivedProtocol == null) {
            // No subprotocol required and none received
            protocolValid = true;
            setActualSubprotocol(expectedSubprotocol); // null or "" - we echo what the user requested
        } else if (!expectedProtocol.isEmpty()
                && receivedProtocol != null
                && !receivedProtocol.isEmpty()) {
            // We require a subprotocol and received one -> verify it
            for (String protocol : expectedProtocol.split(",")) {
                if (protocol.trim().equals(receivedProtocol)) {
                    protocolValid = true;
                    setActualSubprotocol(receivedProtocol);
                    break;
                }
            }
        } // else mixed cases - which are all errors

        if (!protocolValid) {
            throw new WebSocketHandshakeException(
                    String.format(
                            "Invalid subprotocol. Actual: %s. Expected one of: %s",
                            receivedProtocol, expectedSubprotocol));
        }

        setHandshakeComplete();

        final ChannelPipeline p = channel.pipeline();
        // Remove decompressor from pipeline if its in use
        HttpContentDecompressor decompressor = p.get(HttpContentDecompressor.class);
        if (decompressor != null) {
            p.remove(decompressor);
        }

        // Remove aggregator if present before
        HttpObjectAggregator aggregator = p.get(HttpObjectAggregator.class);
        if (aggregator != null) {
            p.remove(aggregator);
        }

        ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
        if (ctx == null) {
            final HttpClientCodec codec;
            String codecName;
            if (p.get(HANDLER_HTTP_CLIENT_CODEC) != null) {
                codec = (HttpClientCodec) p.get(HANDLER_HTTP_CLIENT_CODEC);
                codecName = HANDLER_HTTP_CLIENT_CODEC;
            } else {
                ctx = p.context(HttpClientCodec.class);
                if (ctx == null) {
                    throw new IllegalStateException(
                            "ChannelPipeline does not contain " + "a HttpRequestEncoder or HttpClientCodec");
                }
                codec = (HttpClientCodec) ctx.handler();
                codecName = ctx.name();
            }

            // Remove the encoder part of the codec as the user may start writing frames after this method
            // returns.
            codec.removeOutboundHandler();
            p.addAfter(codecName, "ws-decoder", newWebsocketDecoder());

            // Delay the removal of the decoder so the user can setup the pipeline if needed to handle
            // WebSocketFrame messages.
            // See https://github.com/netty/netty/issues/4533
            channel
                    .eventLoop()
                    .execute(
                            new Runnable() {
                                @Override
                                public void run() {
                                    p.remove(codec);
                                }
                            });
        } else {
            if (p.get(HttpRequestEncoder.class) != null) {
                // Remove the encoder part of the codec as the user may start writing frames after this
                // method returns.
                p.remove(HttpRequestEncoder.class);
            }
            final ChannelHandlerContext context = ctx;
            p.addAfter(context.name(), "ws-decoder", newWebsocketDecoder());

            // Delay the removal of the decoder so the user can setup the pipeline if needed to handle
            // WebSocketFrame messages.
            // See https://github.com/netty/netty/issues/4533
            channel
                    .eventLoop()
                    .execute(
                            new Runnable() {
                                @Override
                                public void run() {
                                    p.remove(context.handler());
                                }
                            });
        }
    }

    private void setActualSubprotocol(String actualSubprotocol) {
        //        this.actualSubprotocol = actualSubprotocol;
        // hack to access the parent private field
        try {
            Field privateField = WebSocketClientHandshaker.class.getDeclaredField("actualSubprotocol");
            privateField.setAccessible(true);
            privateField.set(this, actualSubprotocol);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            LOG.error("Cannot access field[actualSubprotocol]", e);
        }
    }

    private void setHandshakeComplete() {
        //        handshakeComplete = true;
        // hack to access the parent private field
        try {
            Field privateField = WebSocketClientHandshaker.class.getDeclaredField("handshakeComplete");
            privateField.setAccessible(true);
            privateField.setBoolean(this, true);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            LOG.error("Cannot access field[handshakeComplete]", e);
        }
    }
}
