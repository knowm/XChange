package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.util.Events;

import io.reactivex.Completable;
import io.reactivex.Observable;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitfinex.v1.service.BitfinexTradeService;

/**
 * Created by Lukas Zaoralek on 7.11.17.
 */
public class BitfinexStreamingExchange extends BitfinexExchange implements StreamingExchange {

    static final String API_URI = "wss://api.bitfinex.com/ws/2";

    private BitfinexStreamingService streamingService;
    private BitfinexStreamingMarketDataService streamingMarketDataService;
    private BitfinexStreamingTradeService streamingTradeService;
    private BitfinexStreamingAccountService streamingAccountService;

    @Override
    protected void initServices() {
        super.initServices();
        Runnable onApiCall = Events.onApiCall(exchangeSpecification);
        this.streamingService = createStreamingService();
        this.streamingMarketDataService = new BitfinexStreamingMarketDataService(streamingService);
        this.streamingTradeService = new BitfinexStreamingTradeService(streamingService, (BitfinexTradeService) tradeService, onApiCall);
        this.streamingAccountService = new BitfinexStreamingAccountService(streamingService, streamingTradeService);
    }

    private BitfinexStreamingService createStreamingService() {
        BitfinexStreamingService streamingService = new BitfinexStreamingService(API_URI, getNonceFactory());
        applyStreamingSpecification(getExchangeSpecification(), streamingService);
        if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey())) {
            streamingService.setApiKey(exchangeSpecification.getApiKey());
            streamingService.setApiSecret(exchangeSpecification.getSecretKey());
        }
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
    public BitfinexStreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public BitfinexStreamingAccountService getStreamingAccountService() {
        return streamingAccountService;
    }

    @Override
    public BitfinexStreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }

    public boolean isAuthenticatedAlive() {
        return streamingService != null && streamingService.isAuthenticated();
    }
}
