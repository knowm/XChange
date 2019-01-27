package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;

import io.reactivex.Completable;
import io.reactivex.Observable;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;

import java.util.ArrayList;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexStreamingExchange extends BitfinexExchange implements StreamingExchange {

    static final String API_URI = "wss://api.bitfinex.com/ws/2";

    private BitfinexStreamingService streamingService;
    private BitfinexStreamingMarketDataService streamingMarketDataService;
    private BitfinexStreamingRawService streamingAuthenticatedDataService;

    @Override
    protected void initServices() {
        super.initServices();
        this.streamingService = createStreamingService();
        this.streamingAuthenticatedDataService = createAuthenticatedStreamingService();
        this.streamingMarketDataService = new BitfinexStreamingMarketDataService(streamingService, streamingAuthenticatedDataService);
    }

    private BitfinexStreamingRawService createAuthenticatedStreamingService() {
        if (StringUtils.isEmpty(exchangeSpecification.getApiKey()))
            return null;
        BitfinexStreamingRawService result = new BitfinexStreamingRawService(API_URI);
        result.setApiKey(exchangeSpecification.getApiKey());
        result.setApiSecret(exchangeSpecification.getSecretKey());
        return result;
    }

    private BitfinexStreamingService createStreamingService() {
        BitfinexStreamingService streamingService = new BitfinexStreamingService(API_URI);
        applyStreamingSpecification(getExchangeSpecification(), streamingService);
        return streamingService;
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        if (streamingAuthenticatedDataService == null) {
            return streamingService.connect();
        } else {
            ArrayList<Completable> result = new ArrayList<>();
            result.add(streamingService.connect());
            result.add(streamingAuthenticatedDataService.connect());
            return Completable.concat(result);
        }
    }

    @Override
    public Completable disconnect() {
        if (streamingAuthenticatedDataService == null) {
            return streamingService.disconnect();
        } else {
            ArrayList<Completable> result = new ArrayList<>();
            result.add(streamingService.disconnect());
            result.add(streamingAuthenticatedDataService.disconnect());
            return Completable.concat(result);
        }
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
    public BitfinexStreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }

    public boolean isAuthenticatedAlive() {
        return streamingAuthenticatedDataService != null && streamingAuthenticatedDataService.isSocketOpen();
    }
}
