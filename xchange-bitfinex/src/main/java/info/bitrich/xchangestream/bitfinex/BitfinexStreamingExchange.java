package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexStreamingExchange extends BitfinexExchange implements StreamingExchange {
    private static final String API_URI = "wss://api.bitfinex.com/ws/2";

    private BitfinexStreamingService streamingService;
    private BitfinexStreamingMarketDataService streamingMarketDataService;

    public BitfinexStreamingExchange() {
    }

    @Override
    protected void initServices() {
        super.initServices();
        streamingService = createStreamingService();
        streamingMarketDataService = new BitfinexStreamingMarketDataService(streamingService);
    }

    private BitfinexStreamingService createStreamingService() {
        BitfinexStreamingService streamingService = new BitfinexStreamingService(API_URI);
        applyStreamingSpecification(getExchangeSpecification(), streamingService);
        return streamingService;
    }

    @Override
    public Completable connect(ProductSubscription... args) {
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

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }

}
