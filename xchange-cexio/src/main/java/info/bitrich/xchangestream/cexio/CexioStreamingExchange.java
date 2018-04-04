package info.bitrich.xchangestream.cexio;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.cexio.CexIOExchange;

public class CexioStreamingExchange extends CexIOExchange implements StreamingExchange {

    private static final String API_URI = "wss://ws.cex.io/ws/";

    private final CexioStreamingMarketDataService streamingMarketDataService;
    private final CexioStreamingRawService streamingOrderDataService;

    public CexioStreamingExchange() {
        this.streamingOrderDataService = new CexioStreamingRawService(API_URI);
        this.streamingMarketDataService = new CexioStreamingMarketDataService();
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return streamingOrderDataService.connect();
    }

    @Override
    public Completable disconnect() {
        return streamingOrderDataService.disconnect();
    }

    @Override
    public boolean isAlive() {
        return streamingOrderDataService.isSocketOpen();
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    public void setCredentials(String apiKey, String apiSecret) {
        streamingOrderDataService.setApiKey(apiKey);
        streamingOrderDataService.setApiSecret(apiSecret);
    }

    public CexioStreamingRawService getStreamingRawService() {
        return streamingOrderDataService;
    }

}
