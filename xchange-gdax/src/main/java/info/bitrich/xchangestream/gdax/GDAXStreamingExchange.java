package info.bitrich.xchangestream.gdax;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.gdax.dto.account.GDAXWebsocketAuthData;
import org.knowm.xchange.gdax.service.GDAXAccountServiceRaw;

/**
 * GDAX Streaming Exchange. Connects to live WebSocket feed.
 */
public class GDAXStreamingExchange extends GDAXExchange implements StreamingExchange {
    private static final String API_URI = "wss://ws-feed.gdax.com";

    private GDAXStreamingService streamingService;
    private GDAXStreamingMarketDataService streamingMarketDataService;

    public GDAXStreamingExchange() { }

    @Override
    protected void initServices() {
        super.initServices();
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        if (args == null || args.length == 0)
            throw new UnsupportedOperationException("The ProductSubscription must be defined!");
        ExchangeSpecification exchangeSpec = getExchangeSpecification();
        this.streamingService = new GDAXStreamingService(API_URI, () -> authData(exchangeSpec));
        this.streamingMarketDataService = new GDAXStreamingMarketDataService(this.streamingService);
        streamingService.subscribeMultipleCurrencyPairs(args);

        return streamingService.connect();
    }

    private GDAXWebsocketAuthData authData(ExchangeSpecification exchangeSpec) {
        GDAXWebsocketAuthData authData = null;
        if ( exchangeSpec.getApiKey() != null ) {
            try {
                GDAXAccountServiceRaw rawAccountService = (GDAXAccountServiceRaw) getAccountService();
                authData = rawAccountService.getWebsocketAuthData();
            }
            catch (Exception e) {
                logger.warn("Failed attempting to acquire Websocket AuthData needed for private data on" +
                            " websocket.  Will only receive public information via API", e);
            }
        }
        return authData;
    }

    @Override
    public Completable disconnect() {
        GDAXStreamingService service = this.streamingService;
        streamingService = null;
        streamingMarketDataService = null;
        return service.disconnect();
    }

    @Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure();
    }

    @Override
    public Observable<Object> connectionSuccess() {
        return streamingService.subscribeConnectionSuccess();
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = super.getDefaultExchangeSpecification();
        spec.setShouldLoadRemoteMetaData(false);

        return spec;
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    /**
     * Enables the user to listen on channel inactive events and react appropriately.
     *
     * @param channelInactiveHandler a WebSocketMessageHandler instance.
     */
    public void setChannelInactiveHandler(WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
        streamingService.setChannelInactiveHandler(channelInactiveHandler);
    }

    @Override
    public boolean isAlive() {
        return streamingService != null && streamingService.isSocketOpen();
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }
}
