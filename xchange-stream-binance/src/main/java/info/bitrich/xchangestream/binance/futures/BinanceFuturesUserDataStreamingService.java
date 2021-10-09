package info.bitrich.xchangestream.binance.futures;

import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;

public class BinanceFuturesUserDataStreamingService extends BinanceUserDataStreamingService {

    private static final String USER_API_BASE_URI = "wss://fstream.binance.com/ws/";

    public static BinanceUserDataStreamingService create(String listenKey) {
        return new BinanceFuturesUserDataStreamingService(USER_API_BASE_URI + listenKey);
    }

    protected BinanceFuturesUserDataStreamingService(String url) {
        super(url);
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }
}
