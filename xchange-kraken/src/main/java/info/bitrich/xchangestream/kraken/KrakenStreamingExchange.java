package info.bitrich.xchangestream.kraken;

import com.sun.org.apache.xpath.internal.operations.Bool;
import info.bitrich.xchangestream.core.*;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.service.KrakenAccountServiceRaw;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author makarid
 */
public class KrakenStreamingExchange extends KrakenExchange implements StreamingExchange {

    private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingExchange.class);
    private static final String API_URI = "wss://ws.kraken.com";
    private static final String API_AUTH_URI = "wss://ws-auth.kraken.com";
    private static final String API_BETA_URI = "wss://beta-ws.kraken.com";

    private KrakenStreamingService streamingService, privateStreamingService;
    private KrakenStreamingMarketDataService streamingMarketDataService;
    private KrakenStreamingTradeService streamingTradeService;

    private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

    public KrakenStreamingExchange() {
    }

    private static String pickUri(boolean isPrivate, Boolean useSandbox) {
        if (useSandbox != null && useSandbox)
            return API_BETA_URI;
        else
            return isPrivate ? API_AUTH_URI : API_URI;
    }

    @Override
    protected void initServices() {
        super.initServices();
        Boolean useSanbox = (Boolean)exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX);

        this.streamingService = new KrakenStreamingService(false, pickUri(false,useSanbox));
        this.streamingMarketDataService = new KrakenStreamingMarketDataService(streamingService);

        if (exchangeSpecification.getApiKey() != null) {
            this.privateStreamingService = new KrakenStreamingService(true, pickUri(true,useSanbox));
        }

        KrakenAccountServiceRaw rawKrakenAcctService = (KrakenAccountServiceRaw) getAccountService();

        streamingTradeService = new KrakenStreamingTradeService(privateStreamingService, rawKrakenAcctService);
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        if (privateStreamingService != null)
            return privateStreamingService.connect().andThen(streamingService.connect());

        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        if (privateStreamingService != null)
            return privateStreamingService.disconnect().andThen(streamingService.disconnect());

        return streamingService.disconnect();
    }

    @Override
    public boolean isAlive() {
        return streamingService.isSocketOpen() && (privateStreamingService == null || privateStreamingService.isSocketOpen());
    }
    
    @Override
    public Observable<Object> connectionSuccess() {
        return streamingService.subscribeConnectionSuccess().mergeWith(privateStreamingService.subscribeConnectionSuccess());
    }
    
    @Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure().mergeWith(privateStreamingService.subscribeReconnectFailure());
    }
    
    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
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
    public StreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }
    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        streamingService.useCompressedMessages(compressedMessages);
    }
}
