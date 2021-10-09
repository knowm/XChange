package info.bitrich.xchangestream.binance.futures;

import info.bitrich.xchangestream.binance.BinanceStreamingService;
import info.bitrich.xchangestream.core.ProductSubscription;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;

import java.time.Duration;

public class BinanceFuturesStreamingService extends BinanceStreamingService {

    public BinanceFuturesStreamingService(String baseUri, ProductSubscription productSubscription) {
        super(baseUri, productSubscription);
    }

    public BinanceFuturesStreamingService(String baseUri, ProductSubscription productSubscription, int maxFramePayloadLength, Duration connectionTimeout, Duration retryDuration, int idleTimeoutSeconds) {
        super(baseUri, productSubscription, maxFramePayloadLength, connectionTimeout, retryDuration, idleTimeoutSeconds);
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }
}
