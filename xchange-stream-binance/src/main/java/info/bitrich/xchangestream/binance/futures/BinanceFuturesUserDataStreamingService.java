package info.bitrich.xchangestream.binance.futures;

import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;

public class BinanceFuturesUserDataStreamingService extends BinanceUserDataStreamingService {

    public static BinanceUserDataStreamingService create(String apiUri, String listenKey) {
        return new BinanceFuturesUserDataStreamingService(apiUri + "/ws/" + listenKey);
    }

    protected BinanceFuturesUserDataStreamingService(String url) {
        super(url);
    }

    @Override
    protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
        return null;
    }
}
