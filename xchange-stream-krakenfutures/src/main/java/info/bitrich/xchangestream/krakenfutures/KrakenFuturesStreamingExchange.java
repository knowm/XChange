package info.bitrich.xchangestream.krakenfutures;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.krakenfutures.KrakenFuturesExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenFuturesStreamingExchange extends KrakenFuturesExchange implements StreamingExchange {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenFuturesStreamingExchange.class);
    private final String API_URI = "wss://futures.kraken.com/ws/v1";
    private final String DEMO_API_URI = "wss://demo-futures.kraken.com/ws/v1";

    private KrakenFuturesStreamingService streamingService;
    private KrakenFuturesStreamingMarketDataService streamingMarketDataService;
    private KrakenFuturesStreamingTradeService streamingTradeService;

    @Override
    protected void initServices() {
        super.initServices();
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        this.streamingService = new KrakenFuturesStreamingService((Boolean.TRUE.equals(
                exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) ? DEMO_API_URI : API_URI, exchangeSpecification);
        this.streamingMarketDataService = new KrakenFuturesStreamingMarketDataService(streamingService);
        this.streamingTradeService = new KrakenFuturesStreamingTradeService(streamingService);
        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        return streamingService.disconnect();
    }

    @Override
    public boolean isAlive() {
        return streamingService.isSocketOpen();
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }
    @Override
    public StreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
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
        LOG.debug("Resubscribing channels");
        streamingService.resubscribeChannels();
    }
}
