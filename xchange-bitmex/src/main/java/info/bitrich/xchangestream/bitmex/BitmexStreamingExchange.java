package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexExchange;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Lukas Zaoralek on 12.11.17.
 */
public class BitmexStreamingExchange extends BitmexExchange implements StreamingExchange {
    private static final String API_URI = "wss://www.bitmex.com/realtime";
    private static final String TESTNET_API_URI = "wss://testnet.bitmex.com/realtime";

    private BitmexStreamingService streamingService;
    private BitmexStreamingMarketDataService streamingMarketDataService;

    public BitmexStreamingExchange() {
    }

    @Override
    protected void initServices() {
        super.initServices();
        streamingService = createStreamingService();
        ExchangeSpecification exchangeSpecification = getExchangeSpecification();
        streamingMarketDataService = new BitmexStreamingMarketDataService(streamingService);
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return streamingService.connect();
    }

    private BitmexStreamingService createStreamingService() {
        ExchangeSpecification exchangeSpec = getExchangeSpecification();
        Boolean useSandbox = (Boolean) exchangeSpec.getExchangeSpecificParametersItem(USE_SANDBOX);
        String uri = useSandbox == null || !useSandbox ? API_URI : TESTNET_API_URI;
        BitmexStreamingService streamingService = new BitmexStreamingService(uri, exchangeSpec.getApiKey(), exchangeSpec.getSecretKey());
        applyStreamingSpecification(exchangeSpec, streamingService);
        return streamingService;
    }

    @Override
    public Completable disconnect() {
        return streamingService.disconnect();
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return null;
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
    public boolean isAlive() {
        return streamingService.isSocketOpen();
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }
}
